package io.github.mountainrange.mule.gameplay;

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

	public void addStock(ResourceType resource, int quantity) {
		stocks.put(resource, stocks.get(resource) + quantity);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public void addMoney(int money) {
		this.money += money;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
		money = race.getStartingMoney();
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean equals(Object other) {
		if (other == null || !(other instanceof Player)) {
			return false;
		}

		Player o = (Player) other;
		return name.equals(o.name) && id == o.id;
	}

	public int hashCode() {
		return id;
	}

}
