package io.github.mountainrange.mule.gameplay;

import io.github.mountainrange.mule.enums.MapSize;
import io.github.mountainrange.mule.enums.MapType;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * Tests for WorldMap and Player ownership of tiles.
 */
public class WorldMapTest {

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

}
