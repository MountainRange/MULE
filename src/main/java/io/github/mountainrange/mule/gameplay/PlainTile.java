package io.github.mountainrange.mule.gameplay.javafx;

import io.github.mountainrange.mule.enums.MuleType;
import io.github.mountainrange.mule.enums.TerrainType;
import io.github.mountainrange.mule.gameplay.Player;
import io.github.mountainrange.mule.gameplay.TileInterface;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

/**
 * Self-contained class for information about a given tile.
 */
public class PlainTile implements TileInterface {
	private Player owner;
	private Rectangle ownerRect;
	private Rectangle muleRect;
	private TerrainType terrain;
	private MuleType mule;

	/**
	 * Construct a tile with the given terrain with no mule installed and no owner.
	 * @param terrain type of terrain on the tile
	 * @deprecated Use VisualTile or copy this class into a different view.
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

	public boolean hasOwner() {
		return owner != null;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public void setMuleDraw(MuleType mule) {
		this.mule = mule;
	}

	public TerrainType getTerrainType() {
		return terrain;
	}

	public MuleType getMuleType() {
		return mule;
	}

	public void setMuleType(MuleType mule) {
		this.mule = mule;
	}

	public TerrainType getTerrain() {
		return terrain;
	}

	public void setTerrain(TerrainType terrain) {
		this.terrain = terrain;
	}

	public MuleType getMule() {
		return mule;
	}

	public void setMule(MuleType mule) {
		this.mule = mule;
	}

	/**
	 * Check if a Tile has the same data as another tile, that is, if it has the same owner, terrain, and mule
	 * installed.
	 * @param other tile to compare to
	 * @return whether other is equal to this tile
	 */
	public boolean deepEquals(Object other) {
		if (other == null || !(other instanceof PlainTile)) {
			return false;
		}
		if (this == other) {
			return true;
		}

		PlainTile o = (PlainTile) other;
		return Objects.equals(owner, o.owner) && terrain == o.terrain && mule == o.mule;
	}

	@Override
	public int hashCode() {
		int hash = owner != null ? owner.hashCode() : 0;
		hash = (hash << 8) ^ terrain.ordinal();
		hash = (hash << 8) ^ mule.ordinal();
		return hash;
	}
}
