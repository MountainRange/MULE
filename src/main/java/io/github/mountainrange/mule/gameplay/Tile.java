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

	/**
	 * Set the type of MULE on this Tile, and also draw the MULE if this is a visual tile.
	 * @param mule type of MULE to set
     */
	void setMuleDraw(MuleType mule);

	/**
	 * Get the type of terrain on this tile.
	 * @return type of terrain on this tile
     */
	TerrainType getTerrain();

	/**
	 * Set the terrain associated with this tile.
	 * @param terrain type of terrain to set
     */
	void setTerrain(TerrainType terrain);

	/**
	 * Get the type of MULE on the current tile.
	 * @return MULE on this tile
	 */
	MuleType getMule();

	/**
	 * Set the type of MULE on this tile (without necessarily drawing it).
	 * @param mule type of MULE to set
     */
	void setMule(MuleType mule);

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
