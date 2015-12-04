package io.github.mountainrange.mule.gameplay;

import io.github.mountainrange.mule.gameplay.javafx.VisualTile;
import javafx.geometry.Point2D;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import java.util.stream.Stream;
import java.util.stream.Collectors;

/**
 * A class to represent location of things on the board.
 *
 * Visualization classes can extend this to actually show things!
 *
 * Implementations of this class are responsible for filling {@code grid} with their chosen implementation of {@code
 * Tile}.
 */
@SuppressWarnings("unchecked")
public abstract class Grid<T extends Tile> implements Iterable<Tile> {

	protected T[][] grid;
	protected int rows, cols;
	protected Point selection = null;

	protected Point2D playerPosition = null;

	public Grid(int columns, int rows) {
		this.rows = rows;
		this.cols = columns;

		if (this.rows < 2 || this.cols < 2) {
			throw new IllegalArgumentException("Grid can only be constructed with more than 2 rows and columns");
		}
	}

	/**
	 * Gets the grid in use.
	 * @return the grid in this grid
	 * @deprecated use helper functions instead. Mainly for tests.
	 */
	public T[][] getBackingArray() {
		return grid;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	/**
	 * A method to get a tile.
	 * DO NOT USE UNLESS ABSOLUTELY NECESSARY.
	 *
	 * @param column column to get
	 * @param row row to get
	 */
	public T get(int column, int row) {
		if (column < 0 || row < 0 || column >= grid.length || row >= grid[0].length) {
			throw new IllegalArgumentException("Invalid row or column!");
		}
		return grid[column][row];
	}

	/**
	 * Adds a node to this grid.
	 *
	 * Will overwrite any existing element in the grid.
	 */
	public void add(T toAdd, int column, int row) {
		if (column < 0 || row < 0 || column >= grid.length || row >= grid[0].length) {
			throw new IllegalArgumentException("Invalid row or column!");
		}

		if (this.get(column, row) != null) {
			this.remove(column, row);
		}

		grid[column][row] = toAdd;
	}

	/**
	 * Adds a node to this grid.
	 *
	 * Will overwrite any existing element in the grid.
	 */
	public abstract void addToTile(Object toAdd, int column, int row);

	/**
	 * Removes a node from a selected row/column
	 */
	public T remove(int column, int row) {
		T toReturn = grid[column][row];

		grid[column][row] = null;
		return toReturn;
	}

	/**
	 * Removes the current selection.
	 */
	public void removeSelection() {
		selection = null;
	}

	/**
	 * Selects a certain row/column to outline with a selection marker.
	 *
	 * Will overwrite previous calls to select.
	 */
	public void select(int column, int row) {
		if (column < 0 || row < 0 || column >= grid.length || row >= grid[0].length) {
			throw new IllegalArgumentException("Invalid row or column!");
		}

		if (selection != null) {
			this.removeSelection();
		}

		selection = new Point(column, row);
	}

	public int getCursorX() {
		return ((selection == null) ? -1 : (int) selection.getX());
	}

	public int getCursorY() {
		return (selection == null ? -1 : (int) selection.getY());
	}

	/**
	 * Moves an object from one area to another.
	 * @param columnFrom start col
	 * @param rowFrom start row
	 * @param columnTo end column
	 * @param rowTo end row
	 */
	public void move(int columnFrom, int rowFrom, int columnTo, int rowTo) {
		this.move(columnFrom, rowFrom, columnTo, rowTo, grid, grid);
	}

	/**
	 * A more precise version of move.
	 */
	protected void move(int columnFrom, int rowFrom, int columnTo, int rowTo, T[][] sourceGrid, T[][] targetGrid) {
		if (columnFrom < 0 || rowFrom < 0 || columnFrom >= sourceGrid.length || rowFrom >= sourceGrid[0].length) {
			throw new IllegalArgumentException("Invalid row or column! X: " + columnFrom + " Y: " + rowFrom);
		}

		if (columnTo < 0 || rowTo < 0 || columnTo >= targetGrid.length || rowTo >= targetGrid[0].length) {
			throw new IllegalArgumentException("Invalid row or column. X: " + columnTo + " Y: " + rowTo);
		}

		// Create the animation object to move the item to the destination and keep it there.
		T toMove = sourceGrid[columnFrom][rowFrom];

		if (toMove == null) {
			throw new IllegalArgumentException("You tried to move a tile that was non existent");
		}

		sourceGrid[columnFrom][rowFrom] = null;
		targetGrid[columnTo][rowTo] = toMove;
	}

	/**
	 * Randomizes the grid tiles in this grid
	 */
	public void randomize() {
		T[][] newGrid = (T[][]) Array.newInstance(VisualTile.class, this.cols, this.rows);

		int count2 = 0;

		List<Integer> mapping = Stream.iterate(0, i -> ++i)
			.limit(rows * cols)
			.collect(Collectors.toList());

		Collections.shuffle(mapping);
		mapping.remove(new Integer((rows/2) + (rows * (cols / 2))));
		mapping.add(rows / 2 + rows * (cols / 2), rows / 2 + rows * (cols / 2));

		// System.out.println(mapping);
		for (int i : mapping) {
			this.move(count2 % cols, count2 / cols, i % cols, i / cols, grid, newGrid);
			count2++;
		}
		grid = newGrid;
	}

	/**
	 * Checks if toCheck is inside column or row
	 * @param toCheck Point to check
	 * @param column Column to check
	 * @param row row to check
	 * @return Whether point is inside the specified tile
	 */
	public abstract boolean isInside(Point2D toCheck, int column, int row);

	/**
	 * Moves the player to a specified point.
	 * @param newX New player x point
	 * @param newY New player y point
	 */
	public void movePlayer(double newX, double newY) {
		playerPosition = new Point2D(newX, newY);
	}

	/**
	 * Removes the player from this grid.
	 */
	public void removePlayer() {
		playerPosition = null;
	}

	/**
	 * Returns the player position.
	 * @return The player position. Null if no player is on the field.
	 */
	public Point2D getPlayerPosition() {
		return playerPosition;
	}

	/**
	 * Clears this grid completely.
	 */
	public void clear() {
		grid = (T[][]) Array.newInstance(VisualTile.class, this.cols, this.rows);
	}

	/**
	 * Prints the supplied text to the screen.
	 * @param toPrint the text to print
	 */
	public abstract void printText(String toPrint);

	/**
	 * Clears any printed text, if any.
	 */
	public abstract void clearText();

	/**
	 * Prints the supplied text to the headline area.
	 * @param toPrint the text to print
	 */
	public abstract void printHeadline(String toPrint);

	/**
	 * Clears any printed headlines, if any.
	 */
	public abstract void clearHeadline();


	/**
	 * Return an iterator for all the Tile objects in the grid.
	 * @return an iterator for Tiles
	 */
	@Override
	public Iterator<Tile> iterator() {
		return (Iterator<Tile>) Arrays.stream(grid).flatMap(Arrays::stream).iterator();
	}
}
