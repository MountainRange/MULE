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
 * Created by Matthew Keezer on 9/9/2015.
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