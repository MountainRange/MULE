package io.github.mountainrange.mule.gameplay;

/**
 * Container for changes in the quantity of a particular resource due to usage, spoilage, or production.
 */
public class ProductionResult {

	/** The number of units of a resource that were consumed. */
	public final int usage;
	/** The number of units of a resource that spoiled. */
	public final int spoilage;
	/** The number of units of a resource that were produced. */
	public final int production;
	/** The number of units of a resource that are theoretically required on the next round. */
	public final int requirement;

	public ProductionResult(int usage, int spoilage, int production, int requirement) {
		this.usage = usage;
		this.spoilage = spoilage;
		this.production = production;
		this.requirement = requirement;
	}

	/**
	 * Compute the net change in quantity due to this production result.
	 * @return net change in quantity
	 */
	public int delta() {
		return production - usage - spoilage;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof ProductionResult)) {
			return false;
		}
		if (other == this) {
			return true;
		}
		ProductionResult o = (ProductionResult) other;
		return usage == o.usage && spoilage == o.spoilage && production == o.production
				&& requirement == o.requirement;
	}

	@Override
	public int hashCode() {
		int hash = usage;
		hash = (hash << 8) ^ spoilage;
		hash = (hash << 8) ^ production;
		hash = (hash << 8) ^ requirement;
		return hash;
	}

}
