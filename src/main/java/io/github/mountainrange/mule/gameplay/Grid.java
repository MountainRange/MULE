package io.github.mountainrange.mule.gameplay;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;

import java.lang.IllegalArgumentException;
import java.util.HashSet;

/**
 * A class to represent a scalable Grid in javafx.
 * Needs a pane to draw the grid to.
 *
 * @author Jay Kamat
 * @version 1.0
 *
 */
public class Grid {
	private int rows, cols;
	private Pane upperPane;
	private Node[][] tiles;
	private Rectangle selection;

	public static final double THICKNESS=0;
	public static final Color COLOR=Color.BLACK;
	public static final Rectangle SELECTION_TEMPLATE;

	static {
		SELECTION_TEMPLATE = new Rectangle(1, 1, Color.TRANSPARENT);
		SELECTION_TEMPLATE.setFill(Color.TRANSPARENT);
		SELECTION_TEMPLATE.setStroke(Color.BLACK);
		SELECTION_TEMPLATE.setStrokeWidth(3);
	}

	public Grid(int cols, int rows, Pane upperPane) {
		super();

		this.rows = rows;
		this.cols = cols;
		this.upperPane = upperPane;

		if (this.rows < 2 || this.cols < 2) {
			throw new IllegalArgumentException("Grid can only be constructed with more than 2 rows and columns");
		}

		this.tiles = new Node[cols][rows];

		generateGrid();
	}

	private void generateGrid() {
		for (int i = 1; i < this.cols; i++) {
			Rectangle toAdd = new Rectangle();
			toAdd.setFill(this.COLOR);
			toAdd.setWidth(this.THICKNESS);
			toAdd.setHeight(1);
			toAdd.layoutXProperty().bind(upperPane.widthProperty().divide(cols)
										 .multiply(i).subtract(this.THICKNESS / 2.0));
			toAdd.scaleYProperty().bind(upperPane.heightProperty().multiply(2));
			upperPane.getChildren().add(toAdd);
		}

		for (int i = 1; i < this.cols; i++) {
			Rectangle toAdd = new Rectangle();
			toAdd.setFill(this.COLOR);
			toAdd.setHeight(this.THICKNESS);
			toAdd.setWidth(1);
			toAdd.layoutYProperty().bind(upperPane.heightProperty().divide(rows)
										 .multiply(i).subtract(this.THICKNESS / 2.0));
			toAdd.scaleXProperty().bind(upperPane.widthProperty().multiply(2));
			upperPane.getChildren().add(toAdd);
		}

	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public Node get(int column, int row) {
		if (column < 0 || row < 0 || column >= tiles.length || column >= tiles[0].length) {
			throw new IllegalArgumentException("Invalid row or column!");
		}
		return tiles[column][row];
	}

	/**
	 * Adds a node to this grid.
	 *
	 * Will overwrite any exising element in the grid.
	 */
	public void add(Node toAdd, int column, int row) {
		if (column < 0 || row < 0 || column >= tiles.length || column >= tiles[0].length) {
			throw new IllegalArgumentException("Invalid row or column!");
		}

		tiles[column][row] = toAdd;
		toAdd.layoutXProperty().bind(upperPane.widthProperty().divide(cols).divide(2.0).multiply(1 + column * 2.0));
		toAdd.layoutYProperty().bind(upperPane.heightProperty().divide(rows).divide(2.0).multiply(1 + row * 2.0));

		toAdd.scaleXProperty().bind(upperPane.widthProperty().divide(this.cols));
		toAdd.scaleYProperty().bind(upperPane.heightProperty().divide(this.rows));

		upperPane.getChildren().add(toAdd);
	}

	/**
	 * Removes a node from a selected row/column
	 *
	 */
	public Node remove(int column, int row) {
		Node toReturn = tiles[column][row];

		if (toReturn != null) {
			upperPane.getChildren().remove(toReturn);
		}

		tiles[column][row] = null;
		return toReturn;
	}

	/**
	 * Removes the current selection
	 */
	public void removeSelection() {
		if (selection != null) {
			upperPane.getChildren().remove(selection);
		}
		selection = null;
	}

	/**
	 * Selects a certain row/column to outline with a selection marker.
	 *
	 * Will overwrite previous calls to select.
	 */
	public void select(int column, int row) {
		if (column < 0 || row < 0 || column >= tiles.length || column >= tiles[0].length) {
			throw new IllegalArgumentException("Invalid row or column!");
		}

		if (selection != null) {
			this.removeSelection();
		}

		selection = this.SELECTION_TEMPLATE;
		selection.xProperty().bind(upperPane.widthProperty().divide(cols).divide(2.0).multiply(1 + column * 2.0));
		selection.yProperty().bind(upperPane.heightProperty().divide(rows).divide(2.0).multiply(1 + row * 2.0));

		selection.widthProperty().bind(upperPane.widthProperty().divide(this.cols));
		selection.heightProperty().bind(upperPane.heightProperty().divide(this.rows));

		upperPane.getChildren().add(selection);
	}
}
