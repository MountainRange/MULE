package io.github.mountainrange.mule.enums;

/**
 * List of map types (for the map generator).
 */
public enum MapType {
	CLASSIC("classic"),
	RANDOM("random"),
	EXPERIMENTAL("experimental"),
	EMPTY("empty");

	public final TerrainType[][] map;


	MapType(String name) {
		// Abbreviations
		final TerrainType T99 = null;
		final TerrainType T00 = TerrainType.PLAIN;
		final TerrainType T01 = TerrainType.RIVER;
		final TerrainType T02 = TerrainType.MOUNTAIN1;
		final TerrainType T03 = TerrainType.MOUNTAIN2;
		final TerrainType T04 = TerrainType.MOUNTAIN3;
		final TerrainType T05 = TerrainType.TOWN;

		if (name.equalsIgnoreCase("empty")) {
			this.map = new TerrainType[][]{
				{T99, T99, T99, T99, T99, T99, T99, T99, T99},
				{T99, T99, T99, T99, T99, T99, T99, T99, T99},
				{T99, T99, T99, T99, T99, T99, T99, T99, T99},
				{T99, T99, T99, T99, T99, T99, T99, T99, T99},
				{T99, T99, T99, T99, T99, T99, T99, T99, T99}
			};
		} else {
			this.map = new TerrainType[][]{
				{T00, T00, T02, T00, T01, T00, T04, T00, T00},
				{T00, T02, T00, T00, T01, T00, T00, T00, T04},
				{T04, T00, T00, T00, T05, T00, T00, T00, T02},
				{T00, T03, T00, T00, T01, T00, T03, T00, T00},
				{T00, T00, T03, T00, T01, T00, T00, T00, T03}
			};
		}
	}
}
