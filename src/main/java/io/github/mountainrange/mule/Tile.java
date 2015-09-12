package io.github.mountainrange.mule;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.css.Rect;

/**
 * Created by white on 9/12/2015.
 */
public enum Tile {
	RIVER(Color.BLUE),
	PLAIN(Color.GREEN),
	MOUNTAIN1(Color.ROSYBROWN),
	MOUNTAIN2(Color.SILVER),
	MOUNTAIN3(Color.BLANCHEDALMOND);

	private final int tileSize = 40;

	private final Rectangle rect;

	Tile(Color c) {
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
