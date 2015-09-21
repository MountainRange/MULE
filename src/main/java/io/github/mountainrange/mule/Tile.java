package io.github.mountainrange.mule;

import io.github.mountainrange.mule.enums.MuleType;
import io.github.mountainrange.mule.enums.TerrainType;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

/**
 * Self-contained class for information about a given tile.
 */
public class Tile extends ImageView {

	private TerrainType terrain;
	private MuleType mule;
	private Player owner;

	/**
	 * Construct a tile with the given terrain with no mule installed and no owner.
	 * @param terrain type of terrain on the tile
	 */
	public Tile(TerrainType terrain) {
		this(Objects.requireNonNull(terrain), MuleType.EMPTY, null);
	}

	public Tile(TerrainType terrain, MuleType mule, Player owner) {
		super(terrain.getPic());
		this.terrain = terrain;
		setFitWidth(1);
		setFitHeight(1);
		this.mule = mule;
		this.owner = owner;
	}

}
