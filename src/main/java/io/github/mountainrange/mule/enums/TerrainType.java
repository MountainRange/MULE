package io.github.mountainrange.mule.enums;

import io.github.mountainrange.mule.MULE;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Describes the kind of terrain found on a given tile. Terrain types affect resource production.
 */
public enum TerrainType {
	RIVER("pictures/river.png"),
	PLAIN("pictures/plain.png"),
	MOUNTAIN1("pictures/mountain1.png"),
	MOUNTAIN2("pictures/mountain2.png"),
	MOUNTAIN3("pictures/mountain3.png"),
	TOWN("pictures/town.png");


	private final int tileSize = 40;

	private final Rectangle rect;
	private final String pic;

	TerrainType(Color c) {
		this.pic = "pictures/ViPaint.png";

		double vSize = MULE.VSIZE;
		double hSize = MULE.HSIZE;

		rect = new Rectangle(tileSize, tileSize);
		rect.setFill(c);

		rect.setX(0);
		rect.setX(1);
	}

	TerrainType(String pic) {
		this.pic = pic;

		double vSize = MULE.VSIZE;
		double hSize = MULE.HSIZE;

		rect = new Rectangle(tileSize, tileSize);
		//rect.setFill(c);

		rect.setX(0);
		rect.setX(1);
	}

	public Rectangle getRect() {
		return rect;
	}

	public String getPic() {
		return pic;
	}
}
