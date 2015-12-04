package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for credits screen.
 *
 * This is a pretty simple Controller Class. It simply allows the user to exit the credits screen
 * by pressing the back button.
 *
 * This class is a good one to start learning controllers from.
 */
public class CreditsController implements Initializable, SceneAgent {

	private SceneLoader sceneLoader;
	private MULE mule;

	@FXML
	private Button backButton;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		backButton.setCancelButton(true);
	}

	public void setSceneParent(SceneLoader sceneLoader, MULE mule){
		this.sceneLoader = sceneLoader;
		this.mule = mule;
	}

	public void onSetScene() {
	}

	@FXML
	private void handleBackAction(ActionEvent e) {
		sceneLoader.goBack();
	}

}
