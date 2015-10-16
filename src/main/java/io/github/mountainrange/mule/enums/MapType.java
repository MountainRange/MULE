package io.github.mountainrange.mule.enums;

import java.util.Arrays;
import java.util.Random;

/**
 * List of map types (for the map generator).
 */
public enum MapType {
	CLASSIC, RANDOM_MED, EXPERIMENTAL, EMPTY;

	private TerrainType[][] map;
	private static final Random r = new Random();

	private static final TerrainType[][] EMPTY_MAP;
	private static final TerrainType[][] DEFAULT_MAP;

	// Abbreviations
	private static final TerrainType T99 = null;
	private static final TerrainType T00 = TerrainType.PLAIN;
	private static final TerrainType T01 = TerrainType.RIVER;
	private static final TerrainType T02 = TerrainType.MOUNTAIN1;
	private static final TerrainType T03 = TerrainType.MOUNTAIN2;
	private static final TerrainType T04 = TerrainType.MOUNTAIN3;
	private static final TerrainType T05 = TerrainType.TOWN;

	static {
		EMPTY_MAP = new TerrainType[][]{
				{T99, T99, T99, T99, T99, T99, T99, T99, T99},
				{T99, T99, T99, T99, T99, T99, T99, T99, T99},
				{T99, T99, T99, T99, T99, T99, T99, T99, T99},
				{T99, T99, T99, T99, T99, T99, T99, T99, T99},
				{T99, T99, T99, T99, T99, T99, T99, T99, T99}
		};

		DEFAULT_MAP = new TerrainType[][]{
				{T00, T00, T02, T00, T01, T00, T04, T00, T00},
				{T00, T02, T00, T00, T01, T00, T00, T00, T04},
				{T04, T00, T00, T00, T05, T00, T00, T00, T02},
				{T00, T03, T00, T00, T01, T00, T03, T00, T00},
				{T00, T00, T03, T00, T01, T00, T00, T00, T03}
		};
	}

	/**
	 * The first iteration of the random map generator
	 * @param cols Cols to generate
	 * @param rows Rows to generate
	 * @return The randomly generated map.
	 */
	private static TerrainType[][] randomMapGeneratorAlpha(int cols, int rows) {
		int riverPosition = (int) (Math.random() * cols);
		TerrainType[][] tileGrid = new TerrainType[rows][cols];
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				double r = Math.random();
				if (x == (cols/2) && y == (rows/2)) {
					tileGrid[y][x] = T05;
				} else if (x == riverPosition) {
					tileGrid[y][x] = T01;
				} else if (r < .1) {
					tileGrid[y][x] = T02;
				} else if (r < .2) {
					tileGrid[y][x] = T03;
				} else if (r < .3) {
					tileGrid[y][x] = T04;
				} else {
					tileGrid[y][x] = T00;
				}
			}
		}
		return tileGrid;
	}

	private static TerrainType[][] randomMapGeneratorBeta(int cols, int rows, Integer riverLength, Integer numRivers) {

		// Defaults
		if (riverLength == null) {
			riverLength = 5;
		}
		if (numRivers == null) {
			numRivers = 2;
		}

		// First generate a map w/o a river.
		TerrainType[][] tileGrid = new TerrainType[rows][cols];

		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				double r = Math.random();
				if (x == (cols/2) && y == (rows/2)) {
					tileGrid[y][x] = T05;
				} else if (r < .1) {
					tileGrid[y][x] = T02;
				} else if (r < .2) {
					tileGrid[y][x] = T03;
				} else if (r < .3) {
					tileGrid[y][x] = T04;
				} else {
					tileGrid[y][x] = T00;
				}
			}
		}

		for (int j = 0; j < numRivers; j++) {
			int riverX, riverY;
			// Now we generate rivers on top of it!

			// 1. Find a point on the edge.
			if (r.nextBoolean()) {
				// Top/bottom
				riverX = r.nextInt(cols);
				riverY = r.nextBoolean() ? 0 : rows - 1;

			} else {
				// Left/Right
				riverX = r.nextBoolean() ? 0 : cols - 1;
				riverY = r.nextInt(rows);
			}

			// System.out.println(riverX + " " + riverY);
			// Make this tile a river!
			tileGrid[riverY][riverX] = T01;

			int lastDir = -1; // Prevent movement back and forth. Crossing is Ok though.

			// 2. Generate a river!
			for (int i = 0; i < riverLength; i++) {
				int direction = r.nextInt(4); // 0 = Up, 1 = Right, 2 = down, 3 = left
				if (direction == ((lastDir + 2) % 4)) {
					i--;
					continue;
				}

				if (direction == 0) {
					if (riverY - 1 == rows / 2 || riverY - 1 < 0) {
						i--;
						continue;
					}
					tileGrid[--riverY][riverX] = T01;
				} else if (direction == 1) {
					if (riverX + 1 >= cols || riverX + 1 == cols / 2) {
						i--;
						continue;
					}
					tileGrid[riverY][++riverX] = T01;
				} else if (direction == 2) {
					if (riverY + 1 == rows / 2 || riverY + 1 >= rows) {
						i--;
						continue;
					}
					tileGrid[++riverY][riverX] = T01;
				} else {
					if (riverX - 1 < 0 || riverX - 1 == cols / 2) {
						i--;
						continue;
					}
					tileGrid[riverY][--riverX] = T01;
				}

				lastDir = direction;
				// System.out.println(riverX + " " + riverY);
			}
		}

		return tileGrid;
	}

	/**
	 * Returns the terrain map for this enum.
	 * @return The map for this enum.
	 */
	public TerrainType[][] getMap() {
		if (map == null) {
			if (this.name().equalsIgnoreCase("empty")) {
				map =  EMPTY_MAP;
			} else if (this.name().contains("random".toUpperCase())) {
				map = MapType.randomMapGeneratorBeta(9, 5, null, null);
			} else {
				map = DEFAULT_MAP;
			}
		}
		return map;
	}

	public void resetMap() {
		map = null;
	}
}
