package io.github.mountainrange.mule.enums;

import io.github.mountainrange.mule.MULE;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Describes the kind of terrain found on a given tile. Terrain types affect resource production.
 */
public enum TerrainType {
	RIVER(Color.BLUE),
	PLAIN(Color.GREEN),
	MOUNTAIN1(Color.ROSYBROWN),
	MOUNTAIN2(Color.SILVER),
	MOUNTAIN3(Color.BLANCHEDALMOND);

	private final int tileSize = 40;

	private final Rectangle rect;

	TerrainType(Color c) {
		double vSize = MULE.VSIZE;
		double hSize = MULE.HSIZE;

		rect = new Rectangle(tileSize, tileSize);
		rect.setFill(c);

		rect.setX(0);
		rect.setX(1);
	}

	public Rectangle getRect() {
		return rect;
	}
}
