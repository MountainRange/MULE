package io.github.mountainrange.mule.enums;

import javafx.scene.paint.Color;

/**
 * Describes the type of MULE installed on a given plot.
 */
public enum MuleType {
	EMPTY(null, Color.BLACK),
	FOOD_MULE(ResourceType.FOOD, Color.GREEN),
	ENERGY_MULE(ResourceType.ENERGY, Color.YELLOW),
	SMITHORE_MULE(ResourceType.SMITHORE, Color.PURPLE),
	CRYSTITE_MULE(ResourceType.CRYSTITE, Color.SILVER);

	private ResourceType resourceProduced;
	private Color color;

	MuleType(ResourceType resourceProduced, Color color) {
		this.resourceProduced = resourceProduced;
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
}
