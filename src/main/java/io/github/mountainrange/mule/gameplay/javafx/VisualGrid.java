package io.github.mountainrange.mule.gameplay.javafx;

import io.github.mountainrange.mule.enums.MapSize;
import io.github.mountainrange.mule.enums.MapType;

import io.github.mountainrange.mule.gameplay.Grid;
import io.github.mountainrange.mule.gameplay.Tile;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

/**
 * A class to represent a scalable Grid in javafx.
 * Needs a pane to draw the grid to.
 *
 * @author Jay Kamat
 * @version 1.0
 *
 */
public class VisualGrid<T extends Group & Tile> extends Grid<T> {
	private Pane upperPane;
	private Rectangle selectionRect;

	public static final double THICKNESS = 1;
	public static final Color COLOR = Color.BLACK;
	public static final Rectangle SELECTION_TEMPLATE;

	private Text overlayText;
	private Text overlayHeadline;

	// TODO make the player not a rectangle
	public static final Rectangle PLAYER_TEMPLATE;
	private Rectangle player;

	public static final double TEXT_DISPLAY_RATIO = 3.0 / 4.0;
	public static final double HEADLINE_DISPLAY_RATIO = 1.0 / 4.0;
	public static final double TEXT_BUFFER = 20;

	static {
		SELECTION_TEMPLATE = new Rectangle(1, 1, Color.TRANSPARENT);
		SELECTION_TEMPLATE.setFill(Color.TRANSPARENT);
		SELECTION_TEMPLATE.setStroke(Color.BLACK);
		SELECTION_TEMPLATE.setStrokeWidth(0.05);

		PLAYER_TEMPLATE = new Rectangle(0.125, 0.125, 0.5, 0.5);
		PLAYER_TEMPLATE.setFill(Color.YELLOW);
		PLAYER_TEMPLATE.setStroke(Color.ORANGE);
		PLAYER_TEMPLATE.setStrokeWidth(0.05);
	}

	public VisualGrid(int cols, int rows, MapType mapType, MapSize mapSize, Pane upperPane) {
		super(cols, rows, mapType, mapSize);
		this.upperPane = upperPane;

		// Loads existing grid in, and other stuff.
		resetGrid();
	}

	/**
	 * Resets the grid to the state of the superclass
	 */
	private void resetGrid() {
		// Remove everything from upper pane
		upperPane.getChildren().clear();

		for (int i = 1; i < this.cols; i++) {
			Rectangle toAdd = new Rectangle();
			toAdd.setFill(VisualGrid.COLOR);
			toAdd.setWidth(VisualGrid.THICKNESS);
			toAdd.setHeight(1);
			toAdd.layoutXProperty().bind(upperPane.widthProperty().divide(cols)
										 .multiply(i).subtract(VisualGrid.THICKNESS / 2.0));
			toAdd.scaleYProperty().bind(upperPane.heightProperty().multiply(2));
			upperPane.getChildren().add(toAdd);
		}

		for (int i = 1; i < this.cols; i++) {
			Rectangle toAdd = new Rectangle();
			toAdd.setFill(VisualGrid.COLOR);
			toAdd.setHeight(VisualGrid.THICKNESS);
			toAdd.setWidth(1);
			toAdd.layoutYProperty().bind(upperPane.heightProperty().divide(rows)
										 .multiply(i).subtract(VisualGrid.THICKNESS / 2.0));
			toAdd.scaleXProperty().bind(upperPane.widthProperty().multiply(2));
			upperPane.getChildren().add(toAdd);
		}

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j ++) {
				if (grid[i][j] != null) {
					this.add(grid[i][j], i, j);
				}
			}
		}
	}

	/**
	 * Binds the specified node to the coord
	 * @param toBind Node to bind
	 * @param column Column to bind
	 * @param row Row to bind
	 */
	private void bindToGrid(Node toBind, double column, double row) {
		toBind.layoutXProperty().bind(upperPane.widthProperty().divide(cols).divide(2.0).multiply(1 + column * 2.0));
		toBind.layoutYProperty().bind(upperPane.heightProperty().divide(rows).divide(2.0).multiply(1 + row * 2.0));

		toBind.scaleXProperty().bind(upperPane.widthProperty().divide(this.cols));
		toBind.scaleYProperty().bind(upperPane.heightProperty().divide(this.rows));
	}

	/**
	 * Adds a node to this grid.
	 *
	 * Will overwrite any existing element in the grid.
	 */
	public void add(T toAdd, int column, int row) {
		if (!(toAdd instanceof VisualTile)) {
			throw new IllegalArgumentException("You cannot use non-visual tiles with VisualGrid!");
		}

		super.add(toAdd, column, row);

		toAdd.maxHeight(1);
		toAdd.maxWidth(1);

		this.bindToGrid(toAdd, column, row);
		upperPane.getChildren().add(toAdd);
	}

	/**
	 * Removes a node from a selected row/column
	 */
	public T remove(int column, int row) {
		T toReturn = super.remove(column, row);
		if (toReturn != null) {
			upperPane.getChildren().remove(toReturn);
		}
		return toReturn;
	}

	/**
	 * Removes the current selection
	 */
	public void removeSelection() {
		super.removeSelection();
		if (selectionRect != null) {
			upperPane.getChildren().remove(selectionRect);
			selectionRect = null;
		}
	}

	@Override
	public void addToTile(Object toAdd, int column, int row) {
		if (!(toAdd instanceof Node && toAdd instanceof Tile)) {
			throw new IllegalArgumentException("You cannot add non-visual elements to a VisualGrid Tile");
		}

		((T)grid[column][row]).getChildren().add((T) toAdd);
	}

	/**
	 * Selects a certain row/column to outline with a selection marker.
	 *
	 * Will overwrite previous calls to select.
	 */
	public void select(int column, int row) {
		super.select(column, row);

		if (selectionRect != null) {
			this.upperPane.getChildren().remove(selectionRect);
		}
		selectionRect = VisualGrid.SELECTION_TEMPLATE;

		selectionRect.setHeight(1 - selectionRect.getStrokeWidth() * 2);
		selectionRect.setWidth(1 - selectionRect.getStrokeWidth() * 2);

		this.bindToGrid(selectionRect, selection.getX(), selection.getY());

		upperPane.getChildren().add(selectionRect);
	}

	@Override
	public void move(int columnFrom, int rowFrom, int columnTo, int rowTo, T[][] fromGrid, T[][] toGrid) {
		// Find the node to animate (not moved yet)
		T toAnimate = grid[columnFrom][rowFrom];

		super.move(columnFrom, rowFrom, columnTo, rowTo, fromGrid, toGrid);

		if (!(columnFrom == columnTo && rowFrom == rowTo)) {
			// toAnimate.layoutXProperty().unbind();
			// toAnimate.layoutYProperty().unbind();
			// toAnimate.layoutXProperty().bind(upperPane.widthProperty().multiply(0)); // effectively zero out the bind
			// toAnimate.layoutYProperty().bind(upperPane.widthProperty().multiply(0)); // effectively zero out the bind
			bindToGrid(toAnimate, columnTo, rowTo);

			Path path = new Path();

			MoveTo start = new MoveTo(1, 1);
			LineTo end = new LineTo(1, 1);


			start.xProperty().bind(upperPane.widthProperty().divide(cols).multiply(columnFrom - columnTo));
			start.yProperty().bind(upperPane.heightProperty().divide(rows).multiply(rowFrom - rowTo));

			end.xProperty().bind(upperPane.widthProperty().multiply(0));
			end.yProperty().bind(upperPane.heightProperty().multiply(0));

			path.getElements().add(start);
			path.getElements().add(end);
			path.setStroke(Color.BLACK);
			path.setStrokeWidth(2);

			// upperPane.getChildren().add(path); // For debug purposes

			PathTransition animation = new PathTransition();
			animation.setDuration(Duration.seconds(1.0));
			animation.setPath(path);
			animation.setNode(toAnimate);
			animation.setAutoReverse(false);
			animation.setInterpolator(Interpolator.LINEAR);
			animation.play();
		} else {
			// TODO add log here instead.
			// System.out.println(columnFrom + " " + rowTo);
		}
	}

	@Override
	public boolean isInside(Point2D toCheck, int column, int row) {
		T tile = grid[column][row];
		Rectangle r = new Rectangle(tile.getLayoutX() - tile.getScaleX() / 2,
				tile.getLayoutY() - tile.getScaleY() / 2,
				tile.getScaleX(), tile.getScaleY());
		return r.contains(toCheck);
	}

	/**
	 * Moves the player to a specified point.
	 * Creates a player if not already present.
	 *
	 * @param newX New player x point
	 * @param newY New player y point
	 */
	public void movePlayer(double newX, double newY) {
		super.movePlayer(newX, newY);


		if (player != null) {
			this.upperPane.getChildren().remove(player);
		}

		player = VisualGrid.PLAYER_TEMPLATE;
		this.bindToGrid(player, playerPosition.getX(), playerPosition.getY());

		upperPane.getChildren().add(player);
	}

	/**
	 * Removes the player from this grid.
	 */
	public void removePlayer() {
		super.removePlayer();

		if (this.player != null) {
			this.upperPane.getChildren().remove(this.player);
			this.player = null;
		}
	}

	@Override
	public void printText(String toPrint) {
		if (overlayText != null) {
			clearText();
		}

		overlayText = new Text(0, 0, toPrint);
		overlayText.setFont(Font.font("monospaced", FontWeight.BOLD, FontPosture.REGULAR, 25));
		overlayText.yProperty().bind(
			this.upperPane.heightProperty().multiply(TEXT_DISPLAY_RATIO));

		overlayText.xProperty().bind(this.upperPane.widthProperty().multiply(0).add(TEXT_BUFFER));
		overlayText.wrappingWidthProperty().bind(this.upperPane.widthProperty().subtract(TEXT_BUFFER * 2));
		overlayText.setTextAlignment(TextAlignment.CENTER);

		this.upperPane.getChildren().add(overlayText);
	}

	@Override
	public void clearText() {
		if (overlayText != null) {
			this.upperPane.getChildren().remove(overlayText);
			overlayText = null;
		}
	}

	@Override
	public void printHeadline(String toPrint) {
		if (overlayHeadline != null) {
			clearText();
		}

		overlayHeadline = new Text(0, 0, toPrint);
		overlayHeadline.setFont(Font.font("monospaced", FontWeight.BOLD, FontPosture.REGULAR, 25));
		overlayHeadline.yProperty().bind(
			this.upperPane.heightProperty().multiply(HEADLINE_DISPLAY_RATIO));

		overlayHeadline.xProperty().bind(this.upperPane.widthProperty().multiply(0).add(TEXT_BUFFER));
		overlayHeadline.wrappingWidthProperty().bind(this.upperPane.widthProperty().subtract(TEXT_BUFFER * 2));
		overlayHeadline.setTextAlignment(TextAlignment.CENTER);

		this.upperPane.getChildren().add(overlayHeadline);
	}

	@Override
	public void clearHeadline() {
		if (overlayHeadline != null) {
			this.upperPane.getChildren().remove(overlayHeadline);
			overlayHeadline = null;
		}
	}


	@Override
	public void clear() {
		// Remove all visual items.
		upperPane.getChildren().clear();
		super.clear();
	}
}
