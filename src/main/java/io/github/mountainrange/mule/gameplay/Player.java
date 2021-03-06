package io.github.mountainrange.mule.gameplay;

import io.github.mountainrange.mule.enums.MuleType;
import io.github.mountainrange.mule.enums.Race;
import io.github.mountainrange.mule.enums.ResourceType;

import java.util.EnumMap;

import javafx.scene.paint.Color;

/**
 * Container that holds information about a particular player.
 */
public class Player {

	public static final Color[] DEFAULT_COLORS = { Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE };

	private Race race;
	private Color color;

	private final int id;
	private String name;

	private int money;

	private EnumMap<ResourceType, Integer> stocks;

	private MuleType currentMuleType;

	/**
	 * Create a new Player with the given id and set their name, race, and color to some default based on id.
	 * @param id the id of the player to create
	 */
	public Player(int id) {
		this.id = id;
		name = "Player " + (id + 1);
		race = Race.values()[id % Race.values().length];
		color = DEFAULT_COLORS[id % DEFAULT_COLORS.length];
		money = race.getStartingMoney();
		currentMuleType = null;

		stocks = new EnumMap<>(ResourceType.class);
		for (ResourceType resource : ResourceType.values()) {
			stocks.put(resource, 0);
		}
	}

	/**
	 * Create a player with the given properties.
	 * @param id id of the player
	 * @param name name of the player
	 * @param race race of the player
	 * @param color color of the player
	 */
	public Player(int id, String name, Race race, Color color) {
		this.id = id;
		this.name = name;
		this.race = race;
		this.color = color;

		stocks = new EnumMap<>(ResourceType.class);
		for (ResourceType resource : ResourceType.values()) {
			stocks.put(resource, 0);
		}
	}

	/**
	 * Get the amount of the given resource the player owns.
	 * @param resource the resource to check the stock of
	 * @return the stock of the given resource
	 */
	public int stockOf(ResourceType resource) {
		return stocks.get(resource);
	}

	/**
	 * Change the player's resources by the given amount. Stocks will always remain greater than or equal to zero.
	 * @param resource resource to change
	 * @param delta amount to add/subtract from the player's stocks
	 */
	public void changeStockOf(ResourceType resource, int delta) {
		stocks.put(resource, Math.max(0, stocks.get(resource) + delta));
	}

	/**
	 * Get the id of this player (mainly used to generate a hashcode).
	 * @return id of this player
     */
	public int getId() {
		return id;
	}

	/**
	 * Get the name of this player, as given in the game config screen.
	 * @return name of this player
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of this player to something else.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the amount of money this player has
	 * @return money owned by this player
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * Set the amount of money the player owns to the given amount.
	 */
	public void setMoney(int money) {
		this.money = money;
	}

	/**
	 * Change the amount of money this player has by the given quantity (can be negative).
	 * @param money quantity to change by
     */
	public void changeMoney(int money) {
		this.money += money;
	}

	/**
	 * Get the race of this player.
	 * @return race of this player
     */
	public Race getRace() {
		return race;
	}

	/**
	 * Set the race of this player to the given race.
	 */
	public void setRace(Race race) {
		this.race = race;
		money = race.getStartingMoney();
	}

	/**
	 * Get the color assigned to this player.
	 * @return color associated with this player
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Set the color assigned to this player.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Whether this player is carrying a MULE with them or not.
	 * @return whether this player has a MULE
	 */
	public boolean hasMule() {
		return currentMuleType != null;
	}

	/**
	 * Get the type of MULE this player is carrying, or null if they are not carrying a MULE.
	 * @return the type of MULE this player is carrying
	 */
	public MuleType getMule() {
		return currentMuleType;
	}

	/**
	 * Set the type of MULE this player is carrying.
	 * @param muleType type of MULE this player should carry
	 */
	public void setMule(MuleType muleType) {
		currentMuleType = muleType;
	}

	public boolean equals(Object other) {
		if (!(other instanceof Player)) {
			return false;
		}

		Player o = (Player) other;
		return name.equals(o.name) && id == o.id;
	}

	public int hashCode() {
		return id;
	}

}
