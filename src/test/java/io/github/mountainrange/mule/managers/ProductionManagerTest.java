package io.github.mountainrange.mule.managers;

import static org.junit.Assert.*;

import io.github.mountainrange.mule.enums.MuleType;
import io.github.mountainrange.mule.enums.ResourceType;
import io.github.mountainrange.mule.managers.ProductionManager;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/**
 * Tests for methods in ProductionManager.
 */
public class ProductionManagerTest {

	@Rule
	public Timeout timeout = Timeout.seconds(10);

	@Test
	public void testEmptyMuleProducesNull() {
		assertNull(ProductionManager.resourceProduced(MuleType.EMPTY));
	}

	@Test
	public void testMuleResourceConsistency() {
		for (ResourceType resource : ResourceType.values()) {
			// Ensure that resourceProduced and muleProducedBy are consistent with each other.
			assertEquals(resource, ProductionManager.resourceProduced(ProductionManager.muleProducedBy(resource)));
		}

		for (MuleType muleType : MuleType.values()) {
			if (muleType != MuleType.EMPTY) {
				// Ensure that resourceProduced and muleProducedBy are consistent with each other.
				assertEquals(muleType, ProductionManager.muleProducedBy(ProductionManager.resourceProduced(muleType)));
			}
		}
	}

}
