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
	 * @return price of outfitting the given MuleType
	 */
	public static int outfitPriceOf(MuleType mule) {
		return OUTFIT_PRICES.get(mule);
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
	 * Get the base amount of money earned gambling on the given round. It is 50 from rounds 0-2, and increases by 50
	 * every 4 rounds afterwards. Also see
	 * <a href="http://bringerp.free.fr/RE/Mule/reverseEngineering.php5#GamblingAtThePub">Gambling at the Pub</a>.
	 * @param round round to check gambling profit
	 * @return minimum amount of money earned gambling on the given round
	 */
	public static int baseGamblingProfit(int round) {
		if (round < 0) {
			String msg = String.format("Can't get base gambling profit for round %d: negative round", round);
			throw new IllegalArgumentException(msg);
		}
		return ((round + 1) / 4 + 1) * 50;
	}

	/**
	 * Handles players buying stuff
	 * @param player player who is buying
	 * @param resource resource being bought
	 */
	public void buy(Player player, ResourceType resource) {
		if (stockOf(resource) > 0) {
			if (player.getMoney() >= priceOf(resource)) {
				addResource(resource, -1);
				player.addMoney(priceOf(resource) * -1);
				player.addStock(resource, 1);
				System.out.println(player.getMoney());
			} else {
				System.out.println("Player does not have enough money");
			}
		} else {
			System.out.println("Shop is out of " + resource.toString());
		}
	}

	/**
	 * Handles players selling stuff
	 * @param player player who is selling
	 * @param resource resource being sold
	 */
	public void sell(Player player, ResourceType resource) {
		if (player.stockOf(resource) > 0) {
			addResource(resource, 1);
			player.addMoney(priceOf(resource));
			player.addStock(resource, -1);
			System.out.println(player.getMoney());
		} else {
			System.out.println("You are out of " + resource.toString());
		}
	}

	private void addResource(ResourceType resource, int quantity) {
		int stock = stocks.get(resource);
		stocks.put(resource, stock + quantity);
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

		EnumMap<ResourceType, Integer> startPrices = new EnumMap<>(ResourceType.class);
		startPrices.put(ResourceType.FOOD, 30);
		startPrices.put(ResourceType.ENERGY, 25);
		startPrices.put(ResourceType.SMITHORE, 50);
		startPrices.put(ResourceType.CRYSTITE, 100);
		startPrices.put(ResourceType.MULE, 100);

		INITIAL_PRICES.put(Difficulty.HILL, startPrices);
		INITIAL_PRICES.put(Difficulty.MESA, startPrices);
		INITIAL_PRICES.put(Difficulty.PLATEAU, startPrices);
		INITIAL_PRICES.put(Difficulty.MOUNTAIN, startPrices);

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
