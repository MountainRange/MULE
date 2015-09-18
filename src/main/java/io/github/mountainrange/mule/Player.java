package io.github.mountainrange.mule;

import java.util.EnumMap;
import java.util.Objects;

import io.github.mountainrange.mule.enums.Race;
import io.github.mountainrange.mule.enums.ResourceType;
import javafx.scene.paint.Color;

/**
 * Container that holds information about a particular player.
 */
public class Player {

	public static final String[] DEFAULT_NAME = {"Player 1", "Player 2", "Player 3", "Player 4"};
	public static final Race[] DEFAULT_RACE = { Race.FOLD, Race.FAULT_BLOCK,
			Race.DOME, Race.VOLCANIC, Race.PLATEAU };
	public static final Color[] DEFAULT_COLOR = { Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE };

	private Race race;
	private Color color = Color.BLACK;

	private final int id;
	private String name;

	private int money;

	private EnumMap<ResourceType, Integer> stocks;

	public Player(String name, int id, Race race, Color color) {
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

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
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
