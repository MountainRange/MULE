package io.github.mountainrange.mule.gameplay;

import io.github.mountainrange.mule.enums.MapSize;
import io.github.mountainrange.mule.enums.MapType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Tests for WorldMap and Player ownership of tiles.
 */
public class WorldMapTest {

	@Rule
	public Timeout timeout = Timeout.seconds(10);

    private PlainGrid grid;
	private WorldMap<PlainTile> map;

	@Before
	public void setup() {
		grid = new PlainGrid(9, 5, MapType.CLASSIC, MapSize.ALPS);
		map = new WorldMap<>(grid, MapType.CLASSIC);
	}

	@Test
	public void testOwnershipNew() {
		Player p = new Player(0);

		assertEquals("Player should start with zero tiles", 0, map.countLandOwnedBy(p));
		assertEquals("Player should start with zero tiles", Collections.emptySet(), map.landOwnedBy(p));
	}

	@Test
	public void testOwnershipUnowned() {
		assertNull("Unowned tile should have no owner set", grid.get(0,0).getOwner());
	}

	@Test
	public void testOwnershipSingle() {
		// Instantiate a dummy player object
		Player p = new Player(0);
		// Get an arbitrary tile from the grid
		PlainTile tile = grid.get(0, 0);

		// Ensure that the tile is sold successfully
		assertTrue("Empty tile should be sold successfully", map.sellTile(p, tile));
		// Ensure that the owner is set properly
		assertSame("Tile that was sold should have its owner set to the player given", p, tile.getOwner());
	}

	@Test
	public void testSellingOwnedTileFails() {
		Player p0 = new Player(0);
		Player p1 = new Player(1);

		PlainTile tile = grid.get(0, 0);

		assertTrue("Empty tile should be sold successfully", map.sellTile(p0, tile));
		assertSame("Tile that was sold should have its owner set to the player given", p0, tile.getOwner());

		assertFalse("Already owned tile should not be sold", map.sellTile(p1, tile));
		assertSame("Already owned tile should not have its owner changed after selling fails", p0, tile.getOwner());
	}

	@Test
	public void testSellingAll() {
		Player p0 = new Player(0);
		Player p1 = new Player(1);
		for (int col = 0; col < grid.getCols(); col++) {
			for (int row = 0; row < grid.getRows(); row++) {
				if (col != 4 && row != 2) {
					// If the tile is not the town, attempt to sell it
					PlainTile tile = grid.get(col, row);
					assertTrue("Empty tile should be sold successfully", map.sellTile(p0, tile));
					assertSame("Tile that was sold should have its owner set to the player given", p0, tile.getOwner());
				}
			}
		}

		for (int col = 0; col < grid.getCols(); col++) {
			for (int row = 0; row < grid.getRows(); row++) {
				if (col != 4 && row != 2) {
					PlainTile tile = grid.get(col, row);
					assertFalse("Already owned tile should not be sold", map.sellTile(p1, tile));
					assertSame("Already owned tile should not have its owner changed after selling fails", p0,
							tile.getOwner());
				}
			}
		}
	}

}
