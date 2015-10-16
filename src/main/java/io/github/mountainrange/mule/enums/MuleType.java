package io.github.mountainrange.mule.enums;

import javafx.scene.paint.Color;

/**
 * Describes the type of MULE installed on a given plot.
 */
public enum MuleType {
	EMPTY(Color.BLACK, null),
	FOOD_MULE(Color.GREEN, ResourceType.FOOD),
	ENERGY_MULE(Color.YELLOW, ResourceType.ENERGY),
	SMITHORE_MULE(Color.PURPLE, ResourceType.SMITHORE),
	CRYSTITE_MULE(Color.SILVER, ResourceType.CRYSTITE);

	private Color color;
	private ResourceType type;

	MuleType(Color color, ResourceType type) {
		this.color = color;
		this.type = type;
	}

	public Color getColor() {
		return color;
	}

	public ResourceType getResourceType() {
		return type;
	}
}
