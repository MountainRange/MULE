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
	 * @return True if this tile has an owner, false otherwise
	 */
	boolean hasOwner();

	/**
	 * Returns this tile's owner
	 * @return The tile's owner, or null if no owner.
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
	TerrainType getTerrainType();
	MuleType getMuleType();
	void setMuleType(MuleType mule);
	TerrainType getTerrain();
	void setTerrain(TerrainType terrain);
	MuleType getMule();
	void setMule(MuleType mule);
	boolean deepEquals(Object other);
}
