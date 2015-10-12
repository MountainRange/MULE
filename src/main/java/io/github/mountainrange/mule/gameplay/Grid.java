package io.github.mountainrange.mule.gameplay;

import io.github.mountainrange.mule.enums.MapSize;
import io.github.mountainrange.mule.enums.MapType;
import io.github.mountainrange.mule.enums.TerrainType;
import javafx.geometry.Point2D;
import javafx.scene.Node;

import java.awt.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A class to represent location of things on the board.
 *
 * Visualization classes can extend this to actually show things!
 */
public abstract class Grid implements Iterable<Tile> {

	protected Tile[][] tiles;
	protected int rows, cols;
	protected Point selection = null;

	protected Point2D playerPosition = null;

	public Grid (int columns, int rows, MapType m, MapSize s) {
		int[][] map;

		this.rows = rows;
		this.cols = columns;

		if (this.rows < 2 || this.cols < 2) {
			throw new IllegalArgumentException("Grid can only be constructed with more than 2 rows and columns");
		}

		map = m.map;

		tiles = new Tile[this.cols][this.rows];

		if (map.length <= 0 || tiles.length != map[0].length || tiles[0].length != map.length) {
			throw new IllegalArgumentException("Mismatch detected betwen grid size and map size!");
		}

		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				if (map[j][i] == 0) {
					tiles[i][j] = new Tile(TerrainType.PLAIN);
				} else if (map[j][i] == 1) {
					tiles[i][j] = new Tile(TerrainType.RIVER);
				} else if (map[j][i] == 2) {
					tiles[i][j] = new Tile(TerrainType.MOUNTAIN1);
				} else if (map[j][i] == 3) {
					tiles[i][j] = new Tile(TerrainType.MOUNTAIN2);
				} else if (map[j][i] == 4) {
					tiles[i][j] = new Tile(TerrainType.MOUNTAIN3);
				} else if (map[j][i] == 5) {
					tiles[i][j] = new Tile(TerrainType.TOWN);
				}
			}
		}
	}

	/**
	 * Gets the tiles in use.
	 * @return the tiles in this grid
	 * @deprecated use helper functions instead
	 */
	public Tile[][] getTiles() {
		return tiles;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	/**
	 * A method to get a tile
	 * DO NOT USE UNLESS ABSOLUTELY NECCESARY
	 *
	 * @param column column to get
	 * @param row row to get
	 * @deprecated
	 */
	public Tile get(int column, int row) {
		if (column < 0 || row < 0 || column >= tiles.length || row >= tiles[0].length) {
			throw new IllegalArgumentException("Invalid row or column!");
		}
		return tiles[column][row];
	}

	/**
	 * Adds a node to this grid.
	 *
	 * Will overwrite any existing element in the grid.
	 */
	public void add(Tile toAdd, int column, int row) {
		if (column < 0 || row < 0 || column >= tiles.length || row >= tiles[0].length) {
			throw new IllegalArgumentException("Invalid row or column!");
		}

		toAdd.maxHeight(1);
		toAdd.maxWidth(1);

		if (this.get(column, row) != null) {
			this.remove(column, row);
		}

		tiles[column][row] = toAdd;
	}

	/**
	 * Adds a node to this grid.
	 *
	 * Will overwrite any existing element in the grid.
	 */
	public void addToTile(Node toAdd, int column, int row) {
		tiles[column][row].getChildren().add(toAdd);
	}

	/**
	 * Removes a node from a selected row/column
	 */
	public Tile remove(int column, int row) {
		Tile toReturn = tiles[column][row];

		tiles[column][row] = null;
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
		if (column < 0 || row < 0 || column >= tiles.length || row >= tiles[0].length) {
			throw new IllegalArgumentException("Invalid row or column!");
		}

		if (selection != null) {
			this.removeSelection();
		}

		selection = new Point(column, row);
	}

	public int getCursorX() {
		return (int)selection.getX();
	}

	public int getCursorY() {
		return (int)selection.getY();
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
		if (columnFrom < 0 || rowFrom < 0 || columnFrom >= tiles.length || rowFrom >= tiles[0].length) {
			throw new IllegalArgumentException("Invalid row or column!");
		}

		if (columnTo < 0 || rowTo < 0 || columnTo >= tiles.length || rowTo >= tiles[0].length) {
			throw new IllegalArgumentException("Invalid row or column!");
		}

		// Create the animation object to move the item to the destination and keep it there.
		Tile toMove = tiles[columnFrom][rowFrom];

		if (toMove == null) {
			throw new IllegalArgumentException("You tried to move a tile that was non existent");
		}

		tiles[columnFrom][rowFrom] = null;
		tiles[columnTo][rowTo] = toMove;
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
	public Iterator<Tile> iterator() {
		return new GridIterator();
	}

	private class GridIterator implements Iterator<Tile> {
		int nextCol;
		int nextRow;

		@Override
		public boolean hasNext() {
			return nextRow != rows - 1 && nextCol != cols;
		}

		@Override
		public Tile next() {
			if (!hasNext()) {
				throw new NoSuchElementException("Can't get next: no more elements");
			}

			if (nextCol == cols) {
				nextCol = 0;
				nextRow++;
			}

			Tile next = tiles[nextCol][nextRow];
			nextCol++;
			return next;
		}
	}

}
