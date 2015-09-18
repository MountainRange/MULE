package io.github.mountainrange.mule;

import java.util.EnumMap;

/**
 * Hold information about current prices and inventories.
 */
public class Shop {

	private static final EnumMap<DifficultyType, EnumMap<ResourceType, Integer>> initialStocks;
	private static final EnumMap<DifficultyType, EnumMap<ResourceType, Integer>> initialPrices;

	private EnumMap<ResourceType, Integer> stocks;
	private EnumMap<ResourceType, Integer> prices;

	public Shop(DifficultyType difficulty) {
		// Initialize stocks and prices to their starting values
		stocks = new EnumMap<>(initialStocks.get(difficulty));
		prices = new EnumMap<>(initialPrices.get(difficulty));
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

	static {
		// Hard-coded initial shop prices and stocks at the beginning of the game
		initialStocks = new EnumMap<>(DifficultyType.class);

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

		initialStocks.put(DifficultyType.HILL, beginnerStocks);
		initialStocks.put(DifficultyType.MESA, otherStocks);
		initialStocks.put(DifficultyType.PLATEAU, otherStocks);
		initialStocks.put(DifficultyType.MOUNTAIN, otherStocks);

		initialPrices = new EnumMap<>(DifficultyType.class);

		EnumMap<ResourceType, Integer> beginnerPrices = new EnumMap<>(ResourceType.class);
		beginnerPrices.put(ResourceType.FOOD, 30);
		beginnerPrices.put(ResourceType.ENERGY, 25);
		beginnerPrices.put(ResourceType.SMITHORE, 50);
		beginnerPrices.put(ResourceType.CRYSTITE, 100);
		beginnerPrices.put(ResourceType.MULE, 100);

		initialPrices.put(DifficultyType.HILL, beginnerPrices);
		initialPrices.put(DifficultyType.MESA, beginnerPrices);
		initialPrices.put(DifficultyType.PLATEAU, beginnerPrices);
		initialPrices.put(DifficultyType.MOUNTAIN, beginnerPrices);
	}

}
