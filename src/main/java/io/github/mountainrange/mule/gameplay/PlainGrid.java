package io.github.mountainrange.mule.gameplay;

import io.github.mountainrange.mule.enums.MapSize;
import io.github.mountainrange.mule.enums.MapType;
import javafx.geometry.Point2D;

import java.lang.reflect.Array;

/**
 * Alternate Grid implementation that doesn't use JavaFX or draw anything to the screen. Used for testing purposes.
 */
public class PlainGrid extends Grid<PlainTile> {
	public PlainGrid(int columns, int rows, MapType mapType, MapSize s) {
		super(columns, rows);

		grid = (PlainTile[][]) Array.newInstance(PlainTile.class, this.cols, this.rows);

		// Copy data terrain information for the current MapType into this grid
		for (int i = 0; i < mapType.getMap().length; i++) {
			for (int j = 0; j < mapType.getMap()[0].length; j++) {
				if (mapType.getMap()[i][j] != null) {
					add(new PlainTile(mapType.getMap()[i][j]), j, i);
				}
			}
		}
	}

	@Override
	public void addToTile(Object toAdd, int column, int row) {
		throw new UnsupportedOperationException("PlainGrid doesn't implement any graphical methods.");
	}

	@Override
	public boolean isInside(Point2D toCheck, int column, int row) {
		throw new UnsupportedOperationException("PlainGrid doesn't implement any graphical methods.");
	}

	@Override
	public void printText(String toPrint) {
		throw new UnsupportedOperationException("PlainGrid doesn't implement any graphical methods.");
	}

	@Override
	public void clearText() {
		throw new UnsupportedOperationException("PlainGrid doesn't implement any graphical methods.");
	}

	@Override
	public void printHeadline(String toPrint) {
		throw new UnsupportedOperationException("PlainGrid doesn't implement any graphical methods.");

	}

	@Override
	public void clearHeadline() {
		throw new UnsupportedOperationException("PlainGrid doesn't implement any graphical methods.");
	}
}
