package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Josh Ting on 9/13/2015.
 */
public class TownController implements Initializable, SceneAgent {

	private SceneLoader sceneLoader;
	private MULE mule;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

	public void setSceneParent(SceneLoader sceneLoader, MULE mule){
		this.sceneLoader = sceneLoader;
		this.mule = mule;
	}

	@FXML
	private void handleStoreAction(ActionEvent e) {
		sceneLoader.setScene(MULE.STORE_SCENE);
	}

	@FXML
	private void handlePubAction(ActionEvent e) {
		sceneLoader.setScene(MULE.PUB_SCENE);
	}

	@FXML
	private void handleLandOfficeAction(ActionEvent e) {
		sceneLoader.setScene(MULE.LAND_OFFICE_SCENE);
	}

	@FXML
	private void handleAssayOfficeAction(ActionEvent e) {
		sceneLoader.setScene(MULE.ASSAY_OFFICE_SCENE);
	}

	@FXML
	private void handleLeaveAction(ActionEvent e) {
		sceneLoader.setScene(MULE.PLAY_SCENE);
	}
}
