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
 * Controller for the Assay Office (currently unimplemented).
 */
public class AssayOfficeController implements Initializable, SceneAgent {

	private SceneLoader sceneLoader;
	private MULE mule;

	@FXML
	private Button leaveButton;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		leaveButton.setCancelButton(true);
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
//
//	@FXML
//	private void handleOptionsAction(ActionEvent e) {
//		sceneLoader.setScene(MULE.OPTIONS_SCENE);
//	}
//
//	@FXML
//	private void handleCreditsAction(ActionEvent e) {
//		sceneLoader.setScene(MULE.CREDITS_SCENE);
//	}
}
