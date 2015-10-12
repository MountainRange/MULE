package io.github.mountainrange.mule.gameplay;

// Junit Imports
import io.github.mountainrange.mule.gameplay.javafx.VisualTile;
import io.github.mountainrange.mule.gameplay.javafx.VisualTile;
import io.github.mountainrange.mule.gameplay.javafx.VisualGrid;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.Timeout;

import io.github.mountainrange.mule.enums.MapSize;
import io.github.mountainrange.mule.enums.MapType;
import io.github.mountainrange.mule.enums.TerrainType;

import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import com.sun.javafx.collections.TrackableObservableList;
import com.sun.javafx.collections.VetoableListDecorator;
import javafx.collections.ListChangeListener.Change;

import java.util.List;

/**
 * A class to test the visual Grid
 */
public class VisualGridTest {

	@Rule
	public Timeout timeout = new Timeout(10000);

	private VisualGrid<VisualTile> grid;
	private Pane upperPane;

	@Before
	public void setup() {
		// Run for every test.
		this.upperPane = new TestPane();
		this.grid = new VisualGrid<>(9, 5, MapType.EMPTY, MapSize.ALPS, upperPane);
	}

	@Test
	@SuppressWarnings("deprecated") // We need this for tests
	public void basicAddTest() {
		VisualTile input = new VisualTile(TerrainType.NULL);
		grid.add(input, 2, 3);
		VisualTile output = grid.get(2, 3);
		VisualTile empty = grid.get(2, 4);
		assertEquals(input, output);
		assertNull(empty);
	}

	@Test
	@SuppressWarnings("deprecated") // We need this for tests
	public void testRemove() {
		VisualTile input = new VisualTile(TerrainType.NULL);
		VisualTile input2 = new VisualTile(TerrainType.NULL);
		grid.add(input, 5, 3);
		grid.add(input2, 8, 2);
		VisualTile output = grid.get(5, 3);
		VisualTile output2 = grid.get(8, 2);
		VisualTile empty = grid.get(2, 4);
		// Check everything was added
		assertEquals(input, output);
		assertEquals(input2, output2);
		assertNull(empty);

		grid.remove(5, 3);

		output = grid.get(5, 3);
		output2= grid.get(8, 2);

		// Check if the removed tile was indeed removed
		empty = grid.get(2, 4);
		assertNull(output);
		assertEquals(input2, output2);
		assertNull(empty);

	}

	@Test
	@SuppressWarnings("deprecated") // We need this for tests
	public void basicSelectionTest() {
		assertEquals(-1, grid.getCursorX());
		assertEquals(-1, grid.getCursorY());

		grid.select(1, 2);
		assertEquals(1, grid.getCursorX());
		assertEquals(2, grid.getCursorY());
	}


	@Test
	@SuppressWarnings("deprecated") // We need this for tests
	public void basicMoveTest() {
		VisualTile input = new VisualTile(TerrainType.NULL);
		grid.add(input, 2, 3);
		VisualTile output = grid.get(2, 3);
		VisualTile empty = grid.get(2, 4);
		assertEquals(input, output);
		assertNull(empty);

		grid.move(2, 3, 5, 3);
		output = grid.get(5, 3);
		empty = grid.get(2, 4);
		VisualTile empty2 = grid.get(2, 3);
		assertEquals(input, output);
		assertNull(empty);
		assertNull(empty2);
	}




	// A pane that isn't a real pane and won't open a window
	private class TestPane extends Pane {

		private final ObservableList<Node> children = new VetoableListDecorator<Node>(new TrackableObservableList<Node>() {
				protected void onChanged(Change<Node> c) {
					// Do Nothing
					// TODO add a test?
				}
			}) {
				@Override
				protected void onProposedChange(final List<Node> newNodes, int[] toBeRemoved) {
					// Do nothing
					// TODO add a test?
				}

				private String constructExceptionMessage(
					String cause, Node offendingNode) {

					return "This error was part of the VisualGridTest junit and should be fixed";
				}
			};

		private ObservableList<Node> testList = new SimpleListProperty<>();

		public ObservableList<Node> getChildren() {
			return children;
		}
	}
}
