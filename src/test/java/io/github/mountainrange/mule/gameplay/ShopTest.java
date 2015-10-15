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
	public Timeout timeout = new Timeout(2000);

	private Shop hillShop;

	@Before
	public void setup() {
		hillShop = new Shop(Difficulty.HILL);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGamblingProfitNegativeValues() {
		Shop.gamblingProfit(-1, -1);
	}

	@Test
	public void testBaseGamblingProfit() {
		// Copied from gambling table: http://bringerp.free.fr/RE/Mule/reverseEngineering.php5#GamblingAtThePub
		assertEquals(50, Shop.gamblingProfit(0, 0));
		assertEquals(50, Shop.gamblingProfit(1, 0));
		assertEquals(50, Shop.gamblingProfit(2, 0));
		assertEquals(100, Shop.gamblingProfit(3, 0));
		assertEquals(100, Shop.gamblingProfit(4, 0));
		assertEquals(100, Shop.gamblingProfit(5, 0));
		assertEquals(100, Shop.gamblingProfit(6, 0));
		assertEquals(150, Shop.gamblingProfit(7, 0));
		assertEquals(150, Shop.gamblingProfit(8, 0));
		assertEquals(150, Shop.gamblingProfit(9, 0));
		assertEquals(150, Shop.gamblingProfit(10, 0));
		assertEquals(200, Shop.gamblingProfit(11, 0));
	}

}
