package io.github.mountainrange.mule.gameplay;

import io.github.mountainrange.mule.enums.Difficulty;
import io.github.mountainrange.mule.enums.MuleType;
import io.github.mountainrange.mule.enums.ResourceType;

import java.util.EnumMap;

/**
 * Hold information about current prices and inventories.
 */
public class Shop {

	private static final EnumMap<Difficulty, EnumMap<ResourceType, Integer>> initialStocks;
	private static final EnumMap<Difficulty, EnumMap<ResourceType, Integer>> initialPrices;

	private static final EnumMap<MuleType, Integer> outfitPrices;

	private EnumMap<ResourceType, Integer> stocks;
	private EnumMap<ResourceType, Integer> prices;

	public Shop(Difficulty difficulty) {
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

	/**
	 * Gets the price of outfitting a MULE of the given type.
	 * @param mule type of MULE to outfit
	 * @return the price of outfitting the given MuleType
	 */
	public static int outfitPriceOf(MuleType mule) {
		return outfitPrices.get(mule);
	}

	static {
		// Hard-coded initial shop prices and stocks at the beginning of the game
		initialStocks = new EnumMap<>(Difficulty.class);

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

		initialStocks.put(Difficulty.HILL, beginnerStocks);
		initialStocks.put(Difficulty.MESA, otherStocks);
		initialStocks.put(Difficulty.PLATEAU, otherStocks);
		initialStocks.put(Difficulty.MOUNTAIN, otherStocks);

		initialPrices = new EnumMap<>(Difficulty.class);

		EnumMap<ResourceType, Integer> beginnerPrices = new EnumMap<>(ResourceType.class);
		beginnerPrices.put(ResourceType.FOOD, 30);
		beginnerPrices.put(ResourceType.ENERGY, 25);
		beginnerPrices.put(ResourceType.SMITHORE, 50);
		beginnerPrices.put(ResourceType.CRYSTITE, 100);
		beginnerPrices.put(ResourceType.MULE, 100);

		initialPrices.put(Difficulty.HILL, beginnerPrices);
		initialPrices.put(Difficulty.MESA, beginnerPrices);
		initialPrices.put(Difficulty.PLATEAU, beginnerPrices);
		initialPrices.put(Difficulty.MOUNTAIN, beginnerPrices);

		// Hard-coded MULE-outfitting prices
		outfitPrices = new EnumMap<>(MuleType.class);

		outfitPrices.put(MuleType.FOOD_MULE, 25);
		outfitPrices.put(MuleType.ENERGY_MULE, 50);
		outfitPrices.put(MuleType.SMITHORE_MULE, 75);
		outfitPrices.put(MuleType.CRYSTITE_MULE, 100);
	}

}
