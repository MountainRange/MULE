package io.github.mountainrange.mule;

import java.util.Objects;

/**
 * Self-contained class for information about a given tile.
 */
public class Tile {

	private TerrainType terrain;
	private MuleType mule;
	private Player owner;

	/**
	 * Construct a tile with the given terrain with no mule installed and no owner.
	 * @param terrain type of terrain on the tile
	 */
	public Tile(TerrainType terrain) {
		this(Objects.requireNonNull(terrain), MuleType.EMPTY, null);
	}

	public Tile(TerrainType terrain, MuleType mule, Player owner) {
		this.terrain = terrain;
		this.mule = mule;
		this.owner = owner;
	}

}
