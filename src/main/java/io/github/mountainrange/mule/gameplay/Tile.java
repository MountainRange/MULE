package io.github.mountainrange.mule.gameplay;

import io.github.mountainrange.mule.enums.MuleType;
import io.github.mountainrange.mule.enums.TerrainType;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

/**
 * Self-contained class for information about a given tile.
 */
public class Tile extends Group {

	private ImageView image;

	private Player owner;
	private TerrainType terrain;
	private MuleType mule;
	/**
	 * Construct a tile with the given terrain with no mule installed and no owner.
	 * @param terrain type of terrain on the tile
	 */
	public Tile(TerrainType terrain) {
		this(Objects.requireNonNull(terrain), MuleType.EMPTY, null);
	}

	public Tile(TerrainType terrain, MuleType mule, Player owner) {
		this.image = new ImageView(terrain.getPath());
		image.setFitWidth(1);
		image.setFitHeight(1);
		this.getChildren().add(image);
		this.maxWidth(1);
		this.maxHeight(1);

		this.terrain = terrain;
		this.mule = mule;
		this.owner = owner;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public TerrainType getTerrain() {
		return terrain;
	}

	public void setTerrain(TerrainType terrain) {
		this.terrain = terrain;
	}

	public MuleType getMule() {
		return mule;
	}

	public void setMule(MuleType mule) {
		this.mule = mule;
	}

}
