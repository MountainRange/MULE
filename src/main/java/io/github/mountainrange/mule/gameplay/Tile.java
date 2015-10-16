package io.github.mountainrange.mule.gameplay;

import io.github.mountainrange.mule.enums.MuleType;
import io.github.mountainrange.mule.enums.TerrainType;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

/**
 * An interface which all tiles will implement.
 * Contains common tile functions which tiles of any kind must implement.
 */
public interface Tile {

	/**
	 * Returns if this tile has been claimed by a player yet.
	 * @return true if this tile has an owner, false otherwise
	 */
	default boolean hasOwner() {
		return getOwner() != null;
	}

	/**
	 * Returns this tile's owner
	 * @return the tile's owner, or null if no owner.
	 */
	Player getOwner();

	/**
	 * Sets this tile's owner.
	 * This will overwrite any existing owner of this tile
	 *
	 * @param owner The owner to set to
	 */
	void setOwner(Player owner);

	void setMuleDraw(MuleType mule);
	TerrainType getTerrain();
	void setTerrain(TerrainType terrain);
	MuleType getMule();
	void setMule(MuleType mule);
	void produce();

	/**
	 * Check if a Tile has the same data as another tile, that is, if it has the same owner, terrain, and mule
	 * installed.
	 * @param other tile to compare to
	 * @return whether other is equal to this tile
	 */
	default boolean deepEquals(Object other) {
		if (other == null || !(other instanceof PlainTile)) {
			return false;
		}
		if (this == other) {
			return true;
		}

		PlainTile o = (PlainTile) other;
		return Objects.equals(getOwner(), o.getOwner()) && getTerrain() == o.getTerrain() && getMule() == o.getMule();
	}
}
