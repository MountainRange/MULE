package io.github.mountainrange.mule.gameplay;

import io.github.mountainrange.mule.enums.MapSize;
import io.github.mountainrange.mule.enums.MapType;
import io.github.mountainrange.mule.gameplay.javafx.VisualTile;
import javafx.geometry.Point2D;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A class to represent location of things on the board.
 *
 * Visualization classes can extend this to actually show things!
 */
@SuppressWarnings("unchecked")
public abstract class Grid<T extends TileInterface> implements Iterable<TileInterface> {

	protected T[][] grid;
	protected int rows, cols;
	protected Point selection = null;

	protected Point2D playerPosition = null;

	public Grid (int columns, int rows, MapType m, MapSize s) {
		this.rows = rows;
		this.cols = columns;

		if (this.rows < 2 || this.cols < 2) {
			throw new IllegalArgumentException("Grid can only be constructed with more than 2 rows and columns");
		}

		grid = (T[][]) Array.newInstance(VisualTile.class, this.cols, this.rows);

		if (m.map.length <= 0 || grid.length != m.map[0].length || grid[0].length != m.map.length) {
			throw new IllegalArgumentException("Mismatch detected betwen grid size and m.map size!");
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
	 * A method to get a tile
	 * DO NOT USE UNLESS ABSOLUTELY NECESSARY
	 *
	 * @param column column to get
	 * @param row row to get
	 * @deprecated
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
	 * Removes the current selection
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
	 *
	 * @param columnFrom start col
	 * @param rowFrom start row
	 * @param columnTo end column
	 * @param rowTo end row
	 */
	public void move(int columnFrom, int rowFrom, int columnTo, int rowTo) {
		if (columnFrom < 0 || rowFrom < 0 || columnFrom >= grid.length || rowFrom >= grid[0].length) {
			throw new IllegalArgumentException("Invalid row or column!");
		}

		if (columnTo < 0 || rowTo < 0 || columnTo >= grid.length || rowTo >= grid[0].length) {
			throw new IllegalArgumentException("Invalid row or column!");
		}

		// Create the animation object to move the item to the destination and keep it there.
		T toMove = grid[columnFrom][rowFrom];

		if (toMove == null) {
			throw new IllegalArgumentException("You tried to move a tile that was non existent");
		}

		grid[columnFrom][rowFrom] = null;
		grid[columnTo][rowTo] = toMove;
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
	 * Return an iterator for all the Tile objects in the grid.
	 * @return an iterator for Tiles
	 */
	@Override
	public Iterator<TileInterface> iterator() {
		return (Iterator<TileInterface>) Arrays.stream(grid).flatMap(Arrays::stream).iterator();
	}
}
