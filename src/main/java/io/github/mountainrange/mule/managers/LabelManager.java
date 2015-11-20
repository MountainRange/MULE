package io.github.mountainrange.mule.managers;

import javafx.scene.control.Label;

/**
 * Created by white on 11/11/2015.
 */
public class LabelManager {

	Label turnLabel;
	Label resourceLabel;

	public LabelManager() {

	}

	public LabelManager(Label turnLabel, Label resourceLabel) {
		this.turnLabel = turnLabel;
		this.resourceLabel = resourceLabel;
	}

	public void processTurnLabel(String turnText) {
		if (turnLabel != null) {
			turnLabel.setText(turnText);
		}
	}

	public void processResourceLabel(String resourceText) {
		if (resourceLabel != null) {
			resourceLabel.setText(resourceText);
		}
	}

}
