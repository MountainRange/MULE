package io.github.mountainrange.mule.enums;

import io.github.mountainrange.mule.Config;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Describes the kind of terrain found on a given tile. Terrain types affect resource production.
 */
public enum TerrainType {
	RIVER("pictures/", Config.getInstance().texturePack, "/river"),
	PLAIN("pictures/", Config.getInstance().texturePack, "/plain"),
	MOUNTAIN1("pictures/", Config.getInstance().texturePack, "/mountain1"),
	MOUNTAIN2("pictures/", Config.getInstance().texturePack, "/mountain2"),
	MOUNTAIN3("pictures/", Config.getInstance().texturePack, "/mountain3"),
	LAKE("pictures/", Config.getInstance().texturePack, "/lake"),
	TOWN("pictures/", Config.getInstance().texturePack, "/town"),
	NULL(null, null, null);

	private static final int TILE_SIZE = 40;

	private final Rectangle rect;
	private final String dir;
	private final String fn;

	private String pack;

	TerrainType(Color c) {
		this.dir = "pictures/";
		this.pack = "";
		this.fn = "ViPaint";

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
		LAKE.pack = pack;
		TOWN.pack = pack;
	}
}
