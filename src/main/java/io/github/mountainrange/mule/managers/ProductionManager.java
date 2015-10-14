package io.github.mountainrange.mule.managers;

import io.github.mountainrange.mule.enums.ResourceType;
import io.github.mountainrange.mule.enums.TerrainType;
import io.github.mountainrange.mule.gameplay.WorldMap;

import java.util.EnumMap;

/**
 * Responsible for computing production, spoilage, etc.
 */
public class ProductionManager {

	private static final EnumMap<TerrainType, EnumMap<ResourceType, Integer>> BASE_PRODUCTION;
	private static final EnumMap<ResourceType, Double> SPOILAGE_RATIOS;

	private WorldMap map;

	public ProductionManager(WorldMap map) {
		this.map = map;
	}

	/**
	 * Get the percentage of a player's stock of a given resource that <em>doesn't</em> spoil each turn.
	 * @param resource resource to get the spoilage ratio of
	 * @return spoilage ratio of the given resource
	 */
	public static double spoilageRatioOf(ResourceType resource) {
		return SPOILAGE_RATIOS.get(resource);
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
		// Hard-coded spoilage ratios from turn to turn
		SPOILAGE_RATIOS = new EnumMap<>(ResourceType.class);

		SPOILAGE_RATIOS.put(ResourceType.FOOD, 0.5);
		SPOILAGE_RATIOS.put(ResourceType.ENERGY, 0.75);
		SPOILAGE_RATIOS.put(ResourceType.SMITHORE, 0.0);
		SPOILAGE_RATIOS.put(ResourceType.CRYSTITE, 0.0);

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
