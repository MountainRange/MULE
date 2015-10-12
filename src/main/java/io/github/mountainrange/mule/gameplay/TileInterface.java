package io.github.mountainrange.mule.gameplay;

import io.github.mountainrange.mule.enums.MuleType;
import io.github.mountainrange.mule.enums.TerrainType;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

/**
 * Self-contained class for information about a given tile.
 */
public interface TileInterface {
	boolean hasOwner();
	Player getOwner();
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
