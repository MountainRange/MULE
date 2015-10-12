package io.github.mountainrange.mule.enums;

/**
 * List of map types (for the map generator).
 */
public enum MapType {
	CLASSIC("classic"),
	RANDOM("random"),
	EXPERIMENTAL("experimental"),
	EMPTY("empty");

	public final int[][] map;

	MapType(String name) {
		if (name.equalsIgnoreCase("empty")) {
			this.map = new int[][]{
				{-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1},
				{-1,-1,-1,-1,-1,-1,-1,-1,-1}
			};
		} else {
			this.map = new int[][]{
				{0,0,2,0,1,0,4,0,0},
				{0,2,0,0,1,0,0,0,4},
				{4,0,0,0,5,0,0,0,2},
				{0,3,0,0,1,0,3,0,0},
				{0,0,3,0,1,0,0,0,3}
			};
		}
	}
}
