package io.github.mountainrange.mule.gameplay;

/**
 * Container for changes in the quantity of a particular resource due to usage, spoilage, or production.
 */
public class ProductionResult {

	/** The number of units of a resource that were consumed. */
	public final int usageDelta;
	/** The number of units of a resource that spoiled. */
	public final int spoilageDelta;
	/** The number of units of a resource that were produced. */
	public final int productionDelta;
	/** The number of units of a resource that are required on the next round. */
	public final int requirement;

	public ProductionResult(int usageDelta, int spoilageDelta, int productionDelta, int requirement) {
		this.usageDelta = usageDelta;
		this.spoilageDelta = spoilageDelta;
		this.productionDelta = productionDelta;
		this.requirement = requirement;
	}

}
