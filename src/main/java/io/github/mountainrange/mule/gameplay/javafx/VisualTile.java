package io.github.mountainrange.mule.gameplay.javafx;

import io.github.mountainrange.mule.enums.MuleType;
import io.github.mountainrange.mule.enums.TerrainType;
import io.github.mountainrange.mule.gameplay.Player;
import io.github.mountainrange.mule.gameplay.Tile;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

/**
 * A tile which is drawn onto the screen.
 *
 * This tile is for use with the VisualGrid class, and JavaFX in general.
 * As such, it extends the group object, so other JavaFX objects can be added to it as children.
 */
public class VisualTile extends Group implements Tile {

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

	/**
	 * Construct a tile with the given terrain, MULE, and owner
	 * @param terrain type of terrain on the tile
	 * @param mule type of MULE on the tile
	 * @param owner player that owns this tile
	 */
	public VisualTile(TerrainType terrain, MuleType mule, Player owner) {
		// If we are not supposed to draw something, don't draw it.
		if (terrain != TerrainType.NULL) {
			ImageView image = new ImageView(terrain.getPath());
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

	@Override
	public Player getOwner() {
		return owner;
	}

	@Override
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

	@Override
	public void setMuleDraw(MuleType mule) {
		this.mule = mule;
		if (this.terrain != TerrainType.NULL) {
			if (muleRect != null) {
				this.getChildren().remove(muleRect);
			}
			muleRect = new Rectangle(0.25, 0.25, 0.5, 0.5);
			muleRect.setFill(Color.TRANSPARENT);
			muleRect.setStroke(mule.displayColor);
			muleRect.setStrokeWidth(0.05);

			getChildren().add(muleRect);
		}
	}

	@Override
	public TerrainType getTerrain() {
		return terrain;
	}

	@Override
	public void setTerrain(TerrainType terrain) {
		this.terrain = terrain;
	}

	@Override
	public MuleType getMule() {
		return mule;
	}

	@Override
	public void setMule(MuleType mule) {
		this.mule = mule;
	}

	@Override
	public int hashCode() {
		int hash = owner != null ? owner.hashCode() : 0;
		hash = (hash << 8) ^ terrain.ordinal();
		hash = (hash << 8) ^ mule.ordinal();
		return hash;
	}
}
