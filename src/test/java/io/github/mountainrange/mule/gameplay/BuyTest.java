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
	public Timeout timeout = Timeout.seconds(10);

	private Shop store;
	private Player shopInspector;

	@Before
	public void setup() {
		// store should have 16 food and energy, 0 smithore and crystite
		store = new Shop(Difficulty.HILL);
		shopInspector = new Player(0, "Bob", Race.DOME, java.awt.Color.RED);
	}

	@Test
	public void testBuyNoMoney() {
		// Create a player with no money and attempt to buy all resources unsuccessfully
		shopInspector.setMoney(0);

		for (ResourceType resource : ResourceType.values()) {
			assertFalse(store.buy(shopInspector, resource));
		}
	}

	@Test
	public void testBuyAll() {
		for (ResourceType resource : ResourceType.values()) {
			int startingQuantity = shopInspector.stockOf(resource);

			// Give the player enough money to buy all of this resource
			shopInspector.changeMoney(store.priceOf(resource) * startingQuantity);

			for (int i = 0; i < startingQuantity; i++) {
				// Buy all the resource from the shop possible
				assertTrue(store.buy(shopInspector, resource));
			}

			// Player should have no money left
			assertEquals(0, shopInspector.getMoney());
			// Player should be unable to buy this resource now
			assertFalse(store.buy(shopInspector, resource));
			// Player should have the same amount of the resource as was originally in the shop
			assertEquals(startingQuantity, shopInspector.stockOf(resource));
		}
	}

}
