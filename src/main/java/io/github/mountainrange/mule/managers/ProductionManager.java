package io.github.mountainrange.mule.managers;

import io.github.mountainrange.mule.enums.MuleType;
import io.github.mountainrange.mule.enums.ResourceType;
import io.github.mountainrange.mule.enums.TerrainType;
import io.github.mountainrange.mule.gameplay.Player;
import io.github.mountainrange.mule.gameplay.ProductionResult;
import io.github.mountainrange.mule.gameplay.Tile;
import io.github.mountainrange.mule.gameplay.WorldMap;

import java.util.*;

/**
 * Responsible for computing production, spoilage, etc.
 */
public final class ProductionManager {

	/** Base production of each resource on each terrain type. */
	private static final EnumMap<TerrainType, EnumMap<ResourceType, Integer>> BASE_PRODUCTION;
	/** Map from resource to the type of MULE that produces it. */
	private static final EnumMap<ResourceType, MuleType> MULE_PRODUCED_BY;
	/** Map from MULE to resource it produces. */
	private static final EnumMap<MuleType, ResourceType> PRODUCES_RESOURCE;

	/**
	 * Calculate production for the given players on the given map on the given round number.
	 * @param map map to calculate production on
	 * @param playerList list of players to calculate production for
	 * @param round round number (affects food requirement)
	 * @return map from players to production results for each resource
	 */
	public static Map<Player, EnumMap<ResourceType, ProductionResult>> calculateProduction(WorldMap map, List<Player>
			playerList, int round) {
		if (round < 0) {
			String msg = String.format("Can't calculate production for round %d: negative round", round);
			throw new IllegalArgumentException(msg);
		}

		Map<Player, EnumMap<ResourceType, ProductionResult>> productionResult = new HashMap<>();

		for (Player player : playerList) {
			EnumMap<ResourceType, ProductionResult> playerProduction = new EnumMap<>(ResourceType.class);
			for (ResourceType resource : ResourceType.values()) {
				int usage = usageOf(resource, player, map, round);
				int requirement = usageOf(resource, player, map, round + 1);
				int spoilage = spoilageOf(resource, player.stockOf(resource), requirement);

				int production = 0;
				Set<Tile> producingTiles = map.tilesWithMule(player, muleProducedBy(resource));
				for (Tile tile : producingTiles) {
					production += baseProductionOf(tile.getTerrain(), resource);
				}

				// TODO: Economies of scale and learning curve bonuses
				ProductionResult resourceProduction = new ProductionResult(usage, spoilage, production, requirement);
				playerProduction.put(resource, resourceProduction);
			}
			productionResult.put(player, playerProduction);
		}

		return productionResult;
	}

	/**
	 * Calculate the usage of the given resource for the given player on the given map on the given round.
	 * @param resource resource to calculate usage of
	 * @param player player to calculate usage of
	 * @param map map to calculate usage of
	 * @param round round number to calculate usage of
	 * @return number of units of the given resource used
	 */
	private static int usageOf(ResourceType resource, Player player, WorldMap map, int round) {
		if (resource == ResourceType.FOOD) {
			// Food usage starts at 3 and increases every 4 rounds
			return round / 4 + 3;
		} else if (resource == ResourceType.ENERGY) {
			// Energy used depends on number of food, smithore, and crystite MULEs installed
			return map.countTilesWithMule(player, MuleType.FOOD_MULE) + map.countTilesWithMule(player,
					MuleType.SMITHORE_MULE) + map.countTilesWithMule(player, MuleType.CRYSTITE_MULE);
		}

		// No smithore or crystite is used for upkeep
		return 0;
	}

	/**
	 * Return the quantity of the given resource that spoils.
	 * @param resource resource to calculate spoilage of
	 * @param quantity amount of resource before spoilage
	 * @return how many units of the given resource spoil
	 */
	private static int spoilageOf(ResourceType resource, int quantity, int requirement) {
		if (resource == ResourceType.FOOD) {
			if (quantity > requirement + 1) {
				// Half the food spoils if there is more than requirement + 1 food
				return quantity / 2;
			} else {
				// No food spoils if there is less than requirement + 1 food
				return 0;
			}
		} else if (resource == ResourceType.ENERGY) {
			// One quarter of energy spoils
			return quantity / 4;
		}

		// Smithore and crystite only spoil if there are more than 50 units
		return Math.max(quantity - 50, 0);
	}

	/**
	 * Get the base quantity of the given resource produced on the given terrain.
	 * @param terrain terrain to get base production
	 * @param resource resource to get base production
	 * @return base production of the given resource on the given terrain
	 */
	private static int baseProductionOf(TerrainType terrain, ResourceType resource) {
		return BASE_PRODUCTION.get(terrain).get(resource);
	}

	/**
	 * Get the corresponding type of mule that produces this resource.
	 * @param resource resource to get MuleType for
	 * @return MuleType for the given resource
	 */
	public static MuleType muleProducedBy(ResourceType resource) {
		return MULE_PRODUCED_BY.get(resource);
	}

	/**
	 * Get the corresponding type of resource that this type of MULE produces.
	 * @param mule MuleType to get type of resource for
	 * @return ResourceType for the given MULE
	 */
	public static ResourceType producesResource(MuleType mule) {
		return PRODUCES_RESOURCE.get(mule);
	}

	/**
	 * Bound the given quantity by the given min and max. If quantity is less than min, return min instead. If quantity
	 * is greater than max, return max instead. Otherwise, return quantity.
	 * @param quantity quantity to bound
	 * @param min lower bound
	 * @param max upper bound
	 * @return quantity bounded by min and max
	 */
	protected static int bound(int quantity, int min, int max) {
		if (min > max) {
			String msg = String.format("Can't bound %d by %d and %d: min > max", quantity, min, max);
			throw new IllegalArgumentException(msg);
		}
		return Math.min(min, Math.max(quantity, max));
	}

	static {
		// Hard-coded base production quantities for each terrain type and resource
		BASE_PRODUCTION = new EnumMap<>(TerrainType.class);

		EnumMap<ResourceType, Integer> plainProduction = new EnumMap<>(ResourceType.class);
		plainProduction.put(ResourceType.FOOD, 2);
		plainProduction.put(ResourceType.ENERGY, 3);
		plainProduction.put(ResourceType.SMITHORE, 1);
		plainProduction.put(ResourceType.CRYSTITE, 0);

		EnumMap<ResourceType, Integer> riverProduction = new EnumMap<>(ResourceType.class);
		riverProduction.put(ResourceType.FOOD, 4);
		riverProduction.put(ResourceType.ENERGY, 2);
		riverProduction.put(ResourceType.SMITHORE, 0);
		riverProduction.put(ResourceType.CRYSTITE, 0);

		EnumMap<ResourceType, Integer> mountain1Production = new EnumMap<>(ResourceType.class);
		mountain1Production.put(ResourceType.FOOD, 1);
		mountain1Production.put(ResourceType.ENERGY, 1);
		mountain1Production.put(ResourceType.CRYSTITE, 0);

		EnumMap<ResourceType, Integer> mountain2Production = new EnumMap<>(mountain1Production);
		EnumMap<ResourceType, Integer> mountain3Production = new EnumMap<>(mountain1Production);

		mountain1Production.put(ResourceType.SMITHORE, 2);
		mountain2Production.put(ResourceType.SMITHORE, 3);
		mountain3Production.put(ResourceType.SMITHORE, 4);

		BASE_PRODUCTION.put(TerrainType.PLAIN, plainProduction);
		BASE_PRODUCTION.put(TerrainType.RIVER, riverProduction);
		BASE_PRODUCTION.put(TerrainType.MOUNTAIN1, mountain1Production);
		BASE_PRODUCTION.put(TerrainType.MOUNTAIN2, mountain2Production);
		BASE_PRODUCTION.put(TerrainType.MOUNTAIN3, mountain3Production);

		MULE_PRODUCED_BY = new EnumMap<>(ResourceType.class);
		MULE_PRODUCED_BY.put(ResourceType.FOOD, MuleType.FOOD_MULE);
		MULE_PRODUCED_BY.put(ResourceType.ENERGY, MuleType.ENERGY_MULE);
		MULE_PRODUCED_BY.put(ResourceType.SMITHORE, MuleType.SMITHORE_MULE);
		MULE_PRODUCED_BY.put(ResourceType.CRYSTITE, MuleType.CRYSTITE_MULE);

		PRODUCES_RESOURCE = new EnumMap<>(MuleType.class);
		PRODUCES_RESOURCE.put(MuleType.FOOD_MULE, ResourceType.FOOD);
		PRODUCES_RESOURCE.put(MuleType.ENERGY_MULE, ResourceType.ENERGY);
		PRODUCES_RESOURCE.put(MuleType.SMITHORE_MULE, ResourceType.SMITHORE);
		PRODUCES_RESOURCE.put(MuleType.CRYSTITE_MULE, ResourceType.CRYSTITE);
	}

	// This is a utility class; disallow instantiation
	private ProductionManager() {}

}
