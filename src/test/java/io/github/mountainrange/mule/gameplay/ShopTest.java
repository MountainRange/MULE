package io.github.mountainrange.mule.gameplay;

// Junit Imports
import io.github.mountainrange.mule.enums.Difficulty;
import org.junit.*;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;
import java.util.*;


/**
 * A Class to Test the Shop
 */
public class ShopTest {

	@Rule
	public Timeout timeout = new Timeout(200);

	private Shop hillShop;

	@Before
	public void setup() {
		hillShop = new Shop(Difficulty.HILL);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFoodUsageNegativeTurn() {
		Shop.foodUsage(-1);
	}

	@Test
	public void testFoodUsage() {
		// Copied from food usage table: http://bringerp.free.fr/RE/Mule/reverseEngineering.php5#PlayerFoodRequirement
		assertEquals(3, Shop.foodUsage(0));
		assertEquals(3, Shop.foodUsage(1));
		assertEquals(3, Shop.foodUsage(2));
		assertEquals(3, Shop.foodUsage(3));
		assertEquals(4, Shop.foodUsage(4));
		assertEquals(4, Shop.foodUsage(5));
		assertEquals(4, Shop.foodUsage(6));
		assertEquals(4, Shop.foodUsage(7));
		assertEquals(5, Shop.foodUsage(8));
		assertEquals(5, Shop.foodUsage(9));
		assertEquals(5, Shop.foodUsage(10));
		assertEquals(5, Shop.foodUsage(11));
	}

}
