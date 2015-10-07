package io.github.mountainrange.mule.gameplay;

import io.github.mountainrange.mule.enums.MuleType;
import io.github.mountainrange.mule.enums.TerrainType;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

/**
 * Self-contained class for information about a given tile.
 */
public class Tile extends Group {
	private ImageView image;

	private Player owner;
	private Rectangle ownerRect;
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

		if (terrain == TerrainType.TOWN) {
			this.owner = new Player(-1, "GAME", null, null);
		}
	}

	public boolean hasOwner() {
		return owner != null;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
		if (ownerRect != null) {
			this.getChildren().remove(ownerRect);
		}
		ownerRect = new Rectangle(0.05, 0.05, 1, 1);
		ownerRect.setFill(Color.TRANSPARENT);
		ownerRect.setStroke(owner.getColor());
		ownerRect.setStrokeWidth(0.05);

		ownerRect.setHeight(1 - ownerRect.getStrokeWidth() * 2);
		ownerRect.setWidth(1 - ownerRect.getStrokeWidth() * 2);

		getChildren().add(ownerRect);
	}

	public TerrainType getTerrainType() {
		return terrain;
	}

	public MuleType getMuleType() {
		return mule;
	}

	public void setMuleType(MuleType mule) {
		this.mule = mule;
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

	/**
	 * Check if a Tile has the same data as another tile, that is, if it has the same owner, terrain, and mule
	 * installed.
	 * @param other tile to compare to
	 * @return whether other is equal to this tile
	 */
	public boolean deepEquals(Object other) {
		if (other == null || !(other instanceof Tile)) {
			return false;
		}
		if (this == other) {
			return true;
		}

		Tile o = (Tile) other;
		return Objects.equals(owner, o.owner) && terrain == o.terrain && mule == o.mule;
	}

	@Override
	public int hashCode() {
		int hash = owner != null ? owner.hashCode() : 0;
		hash = (hash << 8) ^ terrain.ordinal();
		hash = (hash << 8) ^ mule.ordinal();
		return hash;
	}
}
