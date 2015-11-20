package io.github.mountainrange.mule.gameplay;

import io.github.mountainrange.mule.enums.MapSize;
import io.github.mountainrange.mule.enums.MapType;
import io.github.mountainrange.mule.enums.MuleType;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

		for (MuleType muleType : MuleType.values()) {
			String msg = String.format("Player should have zero mules of type %s", muleType);
			assertEquals(msg, 0, map.countTilesWithMule(p, muleType));
			assertEquals(msg, Collections.emptySet(), map.tilesWithMule(p, muleType));
		}
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

		assertEquals("Player should have one tile with an empty MULE", 1, map.countTilesWithMule(p, MuleType.EMPTY));

		Set<Tile> singleTileSet = new HashSet<>();
		singleTileSet.add(tile);
		assertEquals("Player should have one tile with an empty MULE", singleTileSet, map.tilesWithMule(p,
				MuleType.EMPTY));

		for (MuleType muleType : MuleType.values()) {
			if (muleType != MuleType.EMPTY) {
				// Check that all other MuleTypes have no tiles associated with them
				String msg = String.format("Player should have zero mules of type %s", muleType);
				assertEquals(msg, 0, map.countTilesWithMule(p, muleType));
				assertEquals(msg, Collections.emptySet(), map.tilesWithMule(p, muleType));
			}
		}
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

		assertEquals("Other player should have zero tiles", 0, map.countLandOwnedBy(p1));
		assertEquals("Other player should have zero tiles", Collections.emptySet(), map.landOwnedBy(p1));

		for (MuleType muleType : MuleType.values()) {
			// Ensure that both players have no tiles associated with MULEs of other types
			if (muleType != MuleType.EMPTY) {
				String msg0 = String.format("Player should have zero mules of type %s", muleType);
				assertEquals(msg0, 0, map.countTilesWithMule(p0, muleType));
				assertEquals(msg0, Collections.emptySet(), map.tilesWithMule(p0, muleType));
			}

			String msg1 = String.format("Other player should have zero mules of type %s", muleType);
			assertEquals(msg1, 0, map.countTilesWithMule(p1, muleType));
			assertEquals(msg1, Collections.emptySet(), map.tilesWithMule(p1, muleType));
		}
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
