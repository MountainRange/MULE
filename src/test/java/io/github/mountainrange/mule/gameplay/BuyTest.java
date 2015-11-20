package io.github.mountainrange.mule.gameplay;

import io.github.mountainrange.mule.enums.Difficulty;
import io.github.mountainrange.mule.enums.Race;
import io.github.mountainrange.mule.enums.ResourceType;
import org.junit.*;
import org.junit.rules.Timeout;
import javafx.scene.paint.Color;

import static org.junit.Assert.*;


/**
 * A Class to Test the buy method in the shop.
 */
public class BuyTest {

	@Rule
	public Timeout timeout = new Timeout(2000);

	private Shop store;
	private Player shopInspector;

	@Before
	public void setup() {
		// store should have 16 food and energy, 0 smithore and crystite
		store = new Shop(Difficulty.HILL);
	}

//	@Test(expected = IllegalArgumentException.class)
//	public void testGamblingProfitNegativeValues() {
//		Shop.gamblingProfit(-1, -1);
//	}

	@Test
	public void testBuy() {
		shopInspector = new Player(0, "Bob", Race.DOME, Color.RED);
		shopInspector.setMoney(0);
		assertFalse(store.buy(shopInspector, ResourceType.FOOD));
		assertFalse(store.buy(shopInspector, ResourceType.ENERGY));
		assertFalse(store.buy(shopInspector, ResourceType.SMITHORE));
		assertFalse(store.buy(shopInspector, ResourceType.CRYSTITE));
		shopInspector.changeMoney(store.priceOf(ResourceType.ENERGY) * 16);
		for (int i = 0; i < 16; i++) {
			assertTrue(store.buy(shopInspector, ResourceType.ENERGY));
		}
		assertTrue(shopInspector.getMoney() == 0);
		assertFalse(store.buy(shopInspector, ResourceType.ENERGY));
		assertEquals(16, shopInspector.stockOf(ResourceType.ENERGY));
	}

}
