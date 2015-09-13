package io.github.mountainrange.mule.gameplay;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
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
	public int rows, cols;
	public Pane upperPane;

	public static final double THICKNESS=5;
	public static final Color COLOR=Color.BLACK;

	public Grid(int cols, int rows, Pane upperPane) {
		super();
		this.rows = rows;
		this.cols = cols;
		this.upperPane = upperPane;

		if (this.rows < 2 || this.cols < 2) {
			throw new IllegalArgumentException("Grid can only be constructed with more than 2 rows and columns");
		}

		generateGrid();
	}

	private void generateGrid() {
		for (int i = 1; i < this.cols; i++) {
			Rectangle toAdd = new Rectangle();
			toAdd.setFill(this.COLOR);
			toAdd.setWidth(this.THICKNESS);
			toAdd.setHeight(1);
			toAdd.layoutXProperty().bind(upperPane.widthProperty().divide(cols).multiply(i));
			toAdd.scaleYProperty().bind(upperPane.heightProperty().multiply(2));
			upperPane.getChildren().add(toAdd);
		}

		for (int i = 1; i < this.cols; i++) {
			Rectangle toAdd = new Rectangle();
			toAdd.setFill(this.COLOR);
			toAdd.setHeight(this.THICKNESS);
			toAdd.setWidth(1);
			toAdd.layoutYProperty().bind(upperPane.heightProperty().divide(rows).multiply(i));
			toAdd.scaleXProperty().bind(upperPane.widthProperty().multiply(2));
			upperPane.getChildren().add(toAdd);
		}

	}

}
