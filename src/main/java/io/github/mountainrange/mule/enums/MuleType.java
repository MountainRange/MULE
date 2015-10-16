package io.github.mountainrange.mule.enums;

import javafx.scene.paint.Color;

/**
 * Describes the type of MULE installed on a given plot.
 */
public enum MuleType {
	EMPTY(Color.BLACK),
	FOOD_MULE(Color.GREEN),
	ENERGY_MULE(Color.YELLOW),
	SMITHORE_MULE(Color.PURPLE),
	CRYSTITE_MULE(Color.SILVER);

	public final Color color;

	MuleType(Color color) {
		this.color = color;
	}
}
