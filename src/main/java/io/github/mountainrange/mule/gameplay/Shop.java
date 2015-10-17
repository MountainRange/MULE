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

	private static final EnumMap<Difficulty, Integer> INITIAL_MULE_STOCK;
	private static final EnumMap<Difficulty, Integer> INITIAL_MULE_PRICE;

	private static final EnumMap<MuleType, Integer> OUTFIT_PRICES;

	private EnumMap<ResourceType, Integer> stocks;
	private EnumMap<ResourceType, Integer> prices;
	private int muleStock;
	private int mulePrice;

	public Shop(Difficulty difficulty) {
		// Initialize stocks and prices to their starting values
		stocks = new EnumMap<>(INITIAL_STOCKS.get(difficulty));
		prices = new EnumMap<>(INITIAL_PRICES.get(difficulty));

		muleStock = INITIAL_MULE_STOCK.get(difficulty);
		mulePrice = INITIAL_MULE_PRICE.get(difficulty);
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
	 * Get the number of MULEs available in the shop.
	 * @return number of MULEs for sale in the shop
	 */
	public int muleStock() {
		return muleStock;
	}

	/**
	 * Get the price of MULEs in the shop
	 * @return price of MULEs in the shop
	 */
	public int mulePrice() {
		return mulePrice;
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
	 * Randomly generate an amount of money earned gambling on the given round with the given time left. The value is
	 * the base gambling profit plus a random percentage of the amount of time left. Also see
	 * <a href="http://bringerp.free.fr/RE/Mule/reverseEngineering.php5#GamblingAtThePub">Gambling at the Pub</a>.
	 * @param round round to compute gambling profit on
	 * @param timeLeft time left in the given turn
	 * @return amount of money earned gambling
	 */
	public static int gamblingProfit(int round, int timeLeft) {
		if (round < 0 || timeLeft < 0) {
			String msg = String.format("Can't get gambling profit for round %d with %d time left: negative values",
					round, timeLeft);
			throw new IllegalArgumentException(msg);
		}
		return Math.max(0, Math.min(250, (baseGamblingProfit(round) + ((int) (Math.random() * timeLeft)))));
	}

	/**
	 * Get the base amount of money earned gambling on the given round. It is 50 from rounds 0-2, and increases by 50
	 * every 4 rounds afterwards. Also see
	 * <a href="http://bringerp.free.fr/RE/Mule/reverseEngineering.php5#GamblingAtThePub">Gambling at the Pub</a>.
	 * @param round round to check gambling profit
	 * @return minimum amount of money earned gambling on the given round
	 */
	private static int baseGamblingProfit(int round) {
		if (round < 0) {
			String msg = String.format("Can't get base gambling profit for round %d: negative round", round);
			throw new IllegalArgumentException(msg);
		}
		return ((round + 1) / 4 + 1) * 50;
	}

	/**
	 * Buy a single unit of the given resource from the given player to this shop. Buying fails if the shop doesn't have
	 * any of the given resource, or the player doesn't have enough money.
	 * @param player player who is buying
	 * @param resource resource being bought
	 * @return whether the resource was successfully sold
	 */
	public boolean buy(Player player, ResourceType resource) {
		if (stockOf(resource) <= 0) {
			System.out.printf("Shop is out of %s\n", resource.toString());
			return false;
		}
		if (player.getMoney() < priceOf(resource)) {
			System.out.println("Player does not have enough money");
			return false;
		}

		addResource(resource, -1);
		player.addMoney(-priceOf(resource));
		player.changeStockOf(resource, 1);
		return true;
	}

	/**
	 * Sell a single unit of the given resource to the given player from this shop. Selling fails if the given player
	 * doesn't have any of the given resource.
	 * @param player player who is selling
	 * @param resource resource being sold
	 * @return whether the resource was successfully sold
	 */
	public boolean sell(Player player, ResourceType resource) {
		if (player.stockOf(resource) <= 0) {
			System.out.printf("Player is out of %s\n", resource.toString());
			return false;
		}

		addResource(resource, 1);
		player.addMoney(priceOf(resource));
		player.changeStockOf(resource, -1);
		return true;
	}

	/**
	 * Sell a MULE of the given type to the given player. Selling fails if the player is already carrying a MULE, or
	 * there are no mules in the shop.
	 * @param player player to buy MULE for
	 * @return whether a MULE was successfully bought
	 */
	public boolean sellMule(Player player, MuleType muleType) {
		if (muleStock <= 0 || player.hasMule() || player.getMoney() < outfitPriceOf(muleType)) {
			return false;
		}

		muleStock--;
		player.addMoney(-outfitPriceOf(muleType));
		player.setMule(muleType);
		return true;
	}

	private void addResource(ResourceType resource, int quantity) {
		int stock = stocks.get(resource);
		stocks.put(resource, stock + quantity);
	}

	static {
		// Hard-coded shop stocks at the beginning of the game
		INITIAL_STOCKS = new EnumMap<>(Difficulty.class);

		EnumMap<ResourceType, Integer> beginnerStocks = new EnumMap<>(ResourceType.class);
		beginnerStocks.put(ResourceType.FOOD, 16);
		beginnerStocks.put(ResourceType.ENERGY, 16);
		beginnerStocks.put(ResourceType.SMITHORE, 0);
		beginnerStocks.put(ResourceType.CRYSTITE, 0);

		EnumMap<ResourceType, Integer> otherStocks = new EnumMap<>(ResourceType.class);
		otherStocks.put(ResourceType.FOOD, 8);
		otherStocks.put(ResourceType.ENERGY, 8);
		otherStocks.put(ResourceType.SMITHORE, 8);
		otherStocks.put(ResourceType.CRYSTITE, 0);

		INITIAL_STOCKS.put(Difficulty.HILL, beginnerStocks);
		INITIAL_STOCKS.put(Difficulty.MESA, otherStocks);
		INITIAL_STOCKS.put(Difficulty.PLATEAU, otherStocks);
		INITIAL_STOCKS.put(Difficulty.MOUNTAIN, otherStocks);

		// Hard-coded shop prices at the beginning of the game
		INITIAL_PRICES = new EnumMap<>(Difficulty.class);

		EnumMap<ResourceType, Integer> startPrices = new EnumMap<>(ResourceType.class);
		startPrices.put(ResourceType.FOOD, 30);
		startPrices.put(ResourceType.ENERGY, 25);
		startPrices.put(ResourceType.SMITHORE, 50);
		startPrices.put(ResourceType.CRYSTITE, 100);

		// Initial prices are the same on every difficulty
		INITIAL_PRICES.put(Difficulty.HILL, startPrices);
		INITIAL_PRICES.put(Difficulty.MESA, startPrices);
		INITIAL_PRICES.put(Difficulty.PLATEAU, startPrices);
		INITIAL_PRICES.put(Difficulty.MOUNTAIN, startPrices);

		// Hard-coded initial MULE stocks and prices
		INITIAL_MULE_STOCK = new EnumMap<>(Difficulty.class);

		INITIAL_MULE_STOCK.put(Difficulty.HILL, 25);
		INITIAL_MULE_STOCK.put(Difficulty.MESA, 14);
		INITIAL_MULE_STOCK.put(Difficulty.PLATEAU, 14);
		INITIAL_MULE_STOCK.put(Difficulty.MOUNTAIN, 14);

		INITIAL_MULE_PRICE = new EnumMap<>(Difficulty.class);

		INITIAL_MULE_PRICE.put(Difficulty.HILL, 100);
		INITIAL_MULE_PRICE.put(Difficulty.MESA, 100);
		INITIAL_MULE_PRICE.put(Difficulty.PLATEAU, 100);
		INITIAL_MULE_PRICE.put(Difficulty.MOUNTAIN, 100);

		// Hard-coded MULE-outfitting prices
		OUTFIT_PRICES = new EnumMap<>(MuleType.class);

		OUTFIT_PRICES.put(MuleType.FOOD_MULE, 25);
		OUTFIT_PRICES.put(MuleType.ENERGY_MULE, 50);
		OUTFIT_PRICES.put(MuleType.SMITHORE_MULE, 75);
		OUTFIT_PRICES.put(MuleType.CRYSTITE_MULE, 100);

	}

}
