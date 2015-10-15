package io.github.mountainrange.mule.enums;

/**
 * List of map types (for the map generator).
 */
public enum MapType {
	CLASSIC, RANDOM_MED, EXPERIMENTAL, EMPTY;

	private TerrainType[][] map;

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

	MapType() {
	}


	private static TerrainType[][] generateRandomMap(int cols, int rows) {
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

	/**
	 * Returns the terrain map for this enum.
	 * @return The map for this enum.
	 */
	public TerrainType[][] getMap() {
		if (map == null) {
			if (this.name().equalsIgnoreCase("empty")) {
				map =  EMPTY_MAP;
			} else if (this.name().contains("random".toUpperCase())) {
				map = MapType.generateRandomMap(9, 5);
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
