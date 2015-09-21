package io.github.mountainrange.mule;

import io.github.mountainrange.mule.enums.MapSize;
import io.github.mountainrange.mule.enums.MapType;
import io.github.mountainrange.mule.enums.TerrainType;

/**
 * Holds information about the entire map.
 */
public class WorldMap {

	Tile[][] tiles;
	int[][] classicMap = {
			{0,0,2,0,1,0,4,0,0},
			{0,2,0,0,1,0,0,0,4},
			{4,0,0,0,5,0,0,0,2},
			{0,3,0,0,1,0,3,0,0},
			{0,0,3,0,1,0,0,0,3}
	};

	public WorldMap(MapType m, MapSize s) {
		int[][] map;
		if (m == MapType.CLASSIC) {
			map = classicMap;
		} else {
			map = classicMap;
		}
		tiles = new Tile[5][9];
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				if (map[i][j] == 0) {
					tiles[i][j] = new Tile(TerrainType.PLAIN);
				} else if (map[i][j] == 1) {
					tiles[i][j] = new Tile(TerrainType.RIVER);
				} else if (map[i][j] == 2) {
					tiles[i][j] = new Tile(TerrainType.MOUNTAIN1);
				} else if (map[i][j] == 3) {
					tiles[i][j] = new Tile(TerrainType.MOUNTAIN2);
				} else if (map[i][j] == 4) {
					tiles[i][j] = new Tile(TerrainType.MOUNTAIN3);
				} else if (map[i][j] == 5) {
					tiles[i][j] = new Tile(TerrainType.TOWN);
				}
			}
		}
	}

	public Tile[][] getTiles() {
		return tiles;
	}

}
