package io.github.mountainrange.mule.gameplay;

import io.github.mountainrange.mule.enums.MuleType;
import io.github.mountainrange.mule.enums.TerrainType;

import java.io.Serializable;
import java.util.Objects;

/**
 * An alternate Tile implementation, which does not use JavaFX.
 *
 * This tile does not actually draw anything to the screen, it is only
 * a sample implementation of Tile.
 *
 * This is useful for Junit Tests.
 */
public class PlainTile implements Tile, Serializable {
	private Player owner;
	private TerrainType terrain;
	private MuleType mule;

	/**
	 * Construct a tile with the given terrain with no mule installed and no owner.
	 * @param terrain type of terrain on the tile
s	 */
	public PlainTile(TerrainType terrain) {
		this(Objects.requireNonNull(terrain), MuleType.EMPTY, null);
	}

	public PlainTile(TerrainType terrain, MuleType mule, Player owner) {
		this.terrain = terrain;
		this.mule = mule;
		this.owner = owner;

		if (terrain == TerrainType.TOWN) {
			this.owner = new Player(-1, "GAME", null, null);
		}
	}

	/**
	 * Copy constructor from another type of Tile.
	 * @param toCopy
     */
	public PlainTile(Tile toCopy) {
		this(toCopy.getTerrain(), toCopy.getMule(), toCopy.getOwner());
	}

	@Override
	public Player getOwner() {
		return owner;
	}

	@Override
	public void setOwner(Player owner) {
		this.owner = owner;
	}

	@Override
	public void setMuleDraw(MuleType mule) {
		this.mule = mule;
	}

	@Override
	public TerrainType getTerrain() {
		return terrain;
	}

	@Override
	public void setTerrain(TerrainType terrain) {
		this.terrain = terrain;
	}

	@Override
	public MuleType getMule() {
		return mule;
	}

	@Override
	public void setMule(MuleType mule) {
		this.mule = mule;
	}

	@Override
	public int hashCode() {
		int hash = owner != null ? owner.hashCode() : 0;
		hash = (hash << 8) ^ terrain.ordinal();
		hash = (hash << 8) ^ mule.ordinal();
		return hash;
	}
}
