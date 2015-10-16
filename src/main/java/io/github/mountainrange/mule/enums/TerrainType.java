package io.github.mountainrange.mule.enums;

import io.github.mountainrange.mule.Config;
import io.github.mountainrange.mule.MULE;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Describes the kind of terrain found on a given tile. Terrain types affect resource production.
 */
public enum TerrainType {
	RIVER("pictures/", Config.DEFAULT_PACK, "/river.png", new int[] {4, 2, 0, 0}),
	PLAIN("pictures/", Config.DEFAULT_PACK, "/plain.png", new int[] {2, 3, 1, 0}),
	MOUNTAIN1("pictures/", Config.DEFAULT_PACK, "/mountain1.png", new int[] {1, 1, 2, 0}),
	MOUNTAIN2("pictures/", Config.DEFAULT_PACK, "/mountain2.png", new int[] {1, 1, 3, 0}),
	MOUNTAIN3("pictures/", Config.DEFAULT_PACK, "/mountain3.png", new int[] {1, 1, 4, 0}),
	TOWN("pictures/", Config.DEFAULT_PACK, "/town.png", null),
	NULL(null, null, null);

	private final int tileSize = 40;

	private final Rectangle rect;
	private String dir;
	private String pack;
	private String fn;
	private int[] production;

	TerrainType(Color c) {
		this.dir = "pictures/";
		this.pack = "";
		this.fn = "ViPaint.png";

		double vSize = MULE.VSIZE;
		double hSize = MULE.HSIZE;

		rect = new Rectangle(tileSize, tileSize);
		rect.setFill(c);

		rect.setX(0);
		rect.setX(1);
	}

	TerrainType(String dir, String fn, int[] production) {
		this(dir, "", fn, production);
	}

	TerrainType(String dir, String pack, String fn, int[] production) {
		this.dir = dir;
		this.pack = pack;
		this.fn = fn;

		double vSize = MULE.VSIZE;
		double hSize = MULE.HSIZE;

		rect = new Rectangle(tileSize, tileSize);
		//rect.setFill(c);

		rect.setX(0);
		rect.setX(1);

		this.production = production;
	}

	public Rectangle getRect() {
		return rect;
	}

	public String getPath() {
		return dir + pack + fn;
	}

	public static void changePack(String pack) {
		RIVER.pack = pack;
		PLAIN.pack = pack;
		MOUNTAIN1.pack = pack;
		MOUNTAIN2.pack = pack;
		MOUNTAIN3.pack = pack;
		TOWN.pack = pack;
	}

	public int getProduction(ResourceType type) {
		if (type != null) {
			return production[type.ordinal()];
		}
		return 0;
	}
}
