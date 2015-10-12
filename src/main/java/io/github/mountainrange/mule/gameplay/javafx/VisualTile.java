package io.github.mountainrange.mule.gameplay.javafx;

import io.github.mountainrange.mule.enums.MuleType;
import io.github.mountainrange.mule.enums.TerrainType;
import io.github.mountainrange.mule.gameplay.Player;
import io.github.mountainrange.mule.gameplay.TileInterface;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

/**
 * Self-contained class for information about a given tile.
 */
public class VisualTile extends Group implements TileInterface {
	private ImageView image;

	private Player owner;
	private Rectangle ownerRect;
	private Rectangle muleRect;
	private TerrainType terrain;
	private MuleType mule;

	/**
	 * Construct a tile with the given terrain with no mule installed and no owner.
	 * @param terrain type of terrain on the tile
	 */
	public VisualTile(TerrainType terrain) {
		this(Objects.requireNonNull(terrain), MuleType.EMPTY, null);
	}

	public VisualTile(TerrainType terrain, MuleType mule, Player owner) {
		// If we are not supposed to draw something, don't draw it.
		if (terrain != TerrainType.NULL) {
			this.image = new ImageView(terrain.getPath());
			image.setFitWidth(1);
			image.setFitHeight(1);
			this.getChildren().add(image);

			this.maxWidth(1);
			this.maxHeight(1);
		}

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

		if (terrain != TerrainType.NULL) {
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
	}

	public void setMuleDraw(MuleType mule) {
		this.mule = mule;
		if (this.terrain != TerrainType.NULL) {
			if (muleRect != null) {
				this.getChildren().remove(muleRect);
			}
			muleRect = new Rectangle(0.25, 0.25, 0.5, 0.5);
			muleRect.setFill(Color.TRANSPARENT);
			muleRect.setStroke(mule.getColor());
			muleRect.setStrokeWidth(0.05);

			getChildren().add(muleRect);
		}
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
		if (other == null || !(other instanceof VisualTile)) {
			return false;
		}
		if (this == other) {
			return true;
		}

		VisualTile o = (VisualTile) other;
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
