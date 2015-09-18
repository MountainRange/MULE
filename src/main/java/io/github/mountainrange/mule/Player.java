package io.github.mountainrange.mule;

import java.util.EnumMap;
import java.util.Objects;

/**
 * Container that holds information about a particular player.
 */
public class Player {

	private final int id;
	private String name;

	private int money;

	private EnumMap<ResourceType, Integer> stocks;

	public Player(String name, int id) {
		this.name = Objects.requireNonNull(name);
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
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
