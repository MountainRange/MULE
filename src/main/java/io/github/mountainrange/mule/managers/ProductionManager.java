package io.github.mountainrange.mule.managers;

import io.github.mountainrange.mule.enums.ResourceType;
import io.github.mountainrange.mule.enums.TerrainType;
import io.github.mountainrange.mule.gameplay.Player;
import io.github.mountainrange.mule.gameplay.ProductionResult;
import io.github.mountainrange.mule.gameplay.WorldMap;

import java.util.EnumMap;
import java.util.Map;

/**
 * Responsible for computing production, spoilage, etc.
 */
public class ProductionManager {

	private static final EnumMap<TerrainType, EnumMap<ResourceType, Integer>> BASE_PRODUCTION;

	public static Map<Player, EnumMap<ResourceType, ProductionResult>> calculateProduction(WorldMap map, int round) {
		return null;
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
	 * Get the amount of food required for each player on the given turn. Food usage starts at 3 on turn 0 and increases
	 * by 1 every 4 turns.
	 * @param turn turn to calculate food usage
	 * @return food usage on the given turn
	 */
	public static int foodUsage(int turn) {
		if (turn < 0) {
			throw new IllegalArgumentException(String.format("Can't get food usage for turn %d: negative turn", turn));
		}
		return turn / 4 + 3;
	}

	/**
	 * Bound the given quantity by the given min and max. If quantity is less than min, return min instead. If quantity
	 * is greater than max, return max instead. Otherwise, return quantity.
	 * @param quantity quantity to bound
	 * @param min lower bound
	 * @param max upper bound
	 * @return quantity bounded by min and max
	 */
	protected int bound(int quantity, int min, int max) {
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
	}

}
