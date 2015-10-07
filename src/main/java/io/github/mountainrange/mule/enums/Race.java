package io.github.mountainrange.mule.enums;

/**
 * Describes the race of a player.
 */
public enum Race {
	FOLD(1600), FAULT_BLOCK(600), DOME(1000), VOLCANIC(1000), PLATEAU(1000);

	private int startingMoney;

	Race(int startingMoney) {
		this.startingMoney = startingMoney;
	}

	/**
	 * The amount of money that a player of this race starts with.
	 * @return the starting money of this race
	 */
	public int getStartingMoney() {
		return startingMoney;
	}
}
