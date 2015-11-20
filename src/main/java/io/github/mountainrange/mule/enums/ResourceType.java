package io.github.mountainrange.mule.enums;

import io.github.mountainrange.mule.managers.ProductionManager;

/**
 * Describe every resource in the game.
 */
public enum ResourceType {
	FOOD, ENERGY, SMITHORE, CRYSTITE;

	/**
	 * Get the corresponding type of mule that produces this resource. Equivalent to {@link
	 * ProductionManager#muleProducedBy(ResourceType)}.
	 * @return MULE that produces the given resource
	 */
	public MuleType muleProducedBy() {
		return ProductionManager.muleProducedBy(this);
	}
}
