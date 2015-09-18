package io.github.mountainrange.mule;

import java.util.EnumMap;
import java.util.Objects;

import javafx.scene.paint.Color;

/**
 * Container that holds information about a particular player.
 */
public class Player {

	public static final String[] DEFAULT_NAME = {"Player 1", "Player 2", "Player 3", "Player 4"};
	public static final Config.Race[] DEFAULT_RACE = { Config.Race.BONZOID, Config.Race.FLAPPER,
			Config.Race.HUMAN, Config.Race.BUZZAITE };
	public static final Color[] DEFAULT_COLOR = { Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE };

	private Config.Race race;
	private Color color = Color.BLACK;

	private final int id;
	private String name;

	private int money;

	private EnumMap<ResourceType, Integer> stocks;

	public Player(String name, int id, Config.Race race, Color color) {
		this.name = Objects.requireNonNull(name);
		this.id = id;
		this.race = race;
		this.color = color;
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

	public Config.Race getRace() {
		return race;
	}

	public void setRace(Config.Race race) {
		this.race = race;
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
