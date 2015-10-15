package io.github.mountainrange.mule.enums;

/**
 * Describe every resource in the game.
 */
public enum ResourceType {
	FOOD(MuleType.FOOD_MULE),
	ENERGY(MuleType.ENERGY_MULE),
	SMITHORE(MuleType.SMITHORE_MULE),
	CRYSTITE(MuleType.CRYSTITE_MULE);

	/** The corresponding MuleType that produces this resource. */
	public final MuleType producedBy;

	ResourceType(MuleType producedBy) {
		this.producedBy = producedBy;
	}
}
