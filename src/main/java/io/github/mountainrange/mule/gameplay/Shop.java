package io.github.mountainrange.mule.gameplay;

import io.github.mountainrange.mule.enums.Difficulty;
import io.github.mountainrange.mule.enums.MuleType;
import io.github.mountainrange.mule.enums.ResourceType;

import java.util.EnumMap;

/**
 * Hold information about current prices and inventories.
 */
public class Shop {

	private static final EnumMap<Difficulty, EnumMap<ResourceType, Integer>> INITIAL_STOCKS;
	private static final EnumMap<Difficulty, EnumMap<ResourceType, Integer>> INITIAL_PRICES;

	private static final EnumMap<MuleType, Integer> OUTFIT_PRICES;
	private static final EnumMap<ResourceType, Double> SPOILAGE_RATIOS;

	private EnumMap<ResourceType, Integer> stocks;
	private EnumMap<ResourceType, Integer> prices;

	public Shop(Difficulty difficulty) {
		// Initialize stocks and prices to their starting values
		stocks = new EnumMap<>(INITIAL_STOCKS.get(difficulty));
		prices = new EnumMap<>(INITIAL_PRICES.get(difficulty));
	}

	/**
	 * Get the current stock in this shop of the given resource.
	 * @param resource resource to check the stock of
	 * @return quantity of the given resource
	 */
	public int stockOf(ResourceType resource) {
		return stocks.get(resource);
	}

	/**
	 * Get the current stock in this shop of the given resource.
	 * @param resource resource to check the price of
	 * @return price of the given resource
	 */
	public int priceOf(ResourceType resource) {
		return prices.get(resource);
	}

	/**
	 * Gets the price of outfitting a MULE of the given type.
	 * @param mule type of MULE to outfit
	 * @return the price of outfitting the given MuleType
	 */
	public static int outfitPriceOf(MuleType mule) {
		return OUTFIT_PRICES.get(mule);
	}

	/**
	 * Get the percentage of a player's stock that <em>doesn't</em> spoil each turn.
	 * @param resource resource to get the spoilage ratio of
	 * @return the spoilage ratio of the given resource
	 */
	public static double spoilageRatioOf(ResourceType resource) {
		return SPOILAGE_RATIOS.get(resource);
	}
	/**
	 * Get the amount of food required for each player on the given turn. Food usage starts at 3 on turn 0 and increases
	 * by 1 every 3 turns.
	 * @param turn turn to calculate food usage
	 * @return food usage on the given turn
	 */
	public static int foodUsage(int turn) {
		return turn / 4 + 3;
	}

	static {
		// Hard-coded initial shop prices and stocks at the beginning of the game
		INITIAL_STOCKS = new EnumMap<>(Difficulty.class);

		EnumMap<ResourceType, Integer> beginnerStocks = new EnumMap<>(ResourceType.class);
		beginnerStocks.put(ResourceType.FOOD, 16);
		beginnerStocks.put(ResourceType.ENERGY, 16);
		beginnerStocks.put(ResourceType.SMITHORE, 0);
		beginnerStocks.put(ResourceType.CRYSTITE, 0);
		beginnerStocks.put(ResourceType.MULE, 25);

		EnumMap<ResourceType, Integer> otherStocks = new EnumMap<>(ResourceType.class);
		otherStocks.put(ResourceType.FOOD, 8);
		otherStocks.put(ResourceType.ENERGY, 8);
		otherStocks.put(ResourceType.SMITHORE, 8);
		otherStocks.put(ResourceType.CRYSTITE, 0);
		otherStocks.put(ResourceType.MULE, 14);

		INITIAL_STOCKS.put(Difficulty.HILL, beginnerStocks);
		INITIAL_STOCKS.put(Difficulty.MESA, otherStocks);
		INITIAL_STOCKS.put(Difficulty.PLATEAU, otherStocks);
		INITIAL_STOCKS.put(Difficulty.MOUNTAIN, otherStocks);

		INITIAL_PRICES = new EnumMap<>(Difficulty.class);

		EnumMap<ResourceType, Integer> beginnerPrices = new EnumMap<>(ResourceType.class);
		beginnerPrices.put(ResourceType.FOOD, 30);
		beginnerPrices.put(ResourceType.ENERGY, 25);
		beginnerPrices.put(ResourceType.SMITHORE, 50);
		beginnerPrices.put(ResourceType.CRYSTITE, 100);
		beginnerPrices.put(ResourceType.MULE, 100);

		INITIAL_PRICES.put(Difficulty.HILL, beginnerPrices);
		INITIAL_PRICES.put(Difficulty.MESA, beginnerPrices);
		INITIAL_PRICES.put(Difficulty.PLATEAU, beginnerPrices);
		INITIAL_PRICES.put(Difficulty.MOUNTAIN, beginnerPrices);

		// Hard-coded MULE-outfitting prices
		OUTFIT_PRICES = new EnumMap<>(MuleType.class);

		OUTFIT_PRICES.put(MuleType.FOOD_MULE, 25);
		OUTFIT_PRICES.put(MuleType.ENERGY_MULE, 50);
		OUTFIT_PRICES.put(MuleType.SMITHORE_MULE, 75);
		OUTFIT_PRICES.put(MuleType.CRYSTITE_MULE, 100);

		// Hard-coded spoilage ratios from turn to turn
		SPOILAGE_RATIOS = new EnumMap<>(ResourceType.class);

		SPOILAGE_RATIOS.put(ResourceType.FOOD, 0.5);
		SPOILAGE_RATIOS.put(ResourceType.ENERGY, 0.75);
		SPOILAGE_RATIOS.put(ResourceType.SMITHORE, 0.0);
		SPOILAGE_RATIOS.put(ResourceType.CRYSTITE, 0.0);
	}

}
