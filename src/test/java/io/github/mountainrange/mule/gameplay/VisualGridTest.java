// Junit Imports
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.Timeout;

import io.github.mountainrange.mule.gameplay.VisualGrid;
import io.github.mountainrange.mule.gameplay.Tile;
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

	private VisualGrid grid;
	private Pane upperPane;

	@Before
	public void setup() {
		// Run for every test.
		this.upperPane = new TestPane();
		this.grid = new VisualGrid(9, 5, MapType.EMPTY, MapSize.ALPS, upperPane);
	}

	@Test
	public void basicAddTest() {
		Tile input = new Tile(TerrainType.PLAIN);
		grid.add(input, 2, 3);
		Tile output = grid.get(2, 3);
		assertEquals(input, output);
	}

	private class TestPane extends Pane {

		private final ObservableList<Node> children = new VetoableListDecorator<Node>(new TrackableObservableList<Node>() {
				protected void onChanged(Change<Node> c) {
					// Do Nothing
					// TODO test
				}
			}) {
				@Override
				protected void onProposedChange(final List<Node> newNodes, int[] toBeRemoved) {
					// Do nothing
					// TODO test
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
