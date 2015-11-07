package io.github.mountainrange.mule;

import com.sun.javafx.collections.TrackableObservableList;
import com.sun.javafx.collections.VetoableListDecorator;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.List;

/**
 * A pane that isn't a real pane and won't open a window. Used for testing purposes.
 */
public class TestPane extends Pane {

	private final ObservableList<Node> children = new VetoableListDecorator<Node>(new TrackableObservableList<Node>() {
		protected void onChanged(ListChangeListener.Change<Node> c) {
			// Do nothing
		}
	}){
		@Override
		protected void onProposedChange(final List<Node> newNodes, int[] toBeRemoved) {
			// Do nothing
		}

		private String constructExceptionMessage(String cause, Node offendingNode) {
			return "This error was part of the VisualGridTest junit and should be fixed";
		}
	};

	private ObservableList<Node> testList = new SimpleListProperty<>();

	public ObservableList<Node> getChildren() {
		return children;
	}

}
