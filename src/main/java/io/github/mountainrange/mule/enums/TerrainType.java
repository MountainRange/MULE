package io.github.mountainrange.mule.enums;

import io.github.mountainrange.mule.Config;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Describes the kind of terrain found on a given tile. Terrain types affect resource production.
 */
public enum TerrainType {
	RIVER("pictures/", Config.DEFAULT_PACK, "/river.png"),
	PLAIN("pictures/", Config.DEFAULT_PACK, "/plain.png"),
	MOUNTAIN1("pictures/", Config.DEFAULT_PACK, "/mountain1.png"),
	MOUNTAIN2("pictures/", Config.DEFAULT_PACK, "/mountain2.png"),
	MOUNTAIN3("pictures/", Config.DEFAULT_PACK, "/mountain3.png"),
	TOWN("pictures/", Config.DEFAULT_PACK, "/town.png"),
	NULL(null, null, null);

	private static final int TILE_SIZE = 40;

	private final Rectangle rect;
	private final String dir;
	private final String fn;

	private String pack;

	TerrainType(Color c) {
		this.dir = "pictures/";
		this.pack = "";
		this.fn = "ViPaint.png";

		rect = new Rectangle(TILE_SIZE, TILE_SIZE);
		rect.setFill(c);

		rect.setX(0);
		rect.setX(1);
	}

	TerrainType(String dir, String fn) {
		this(dir, "", fn);
	}

	TerrainType(String dir, String pack, String fn) {
		this.dir = dir;
		this.pack = pack;
		this.fn = fn;

		rect = new Rectangle(TILE_SIZE, TILE_SIZE);
		//rect.setFill(c);

		rect.setX(0);
		rect.setX(1);
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
}
