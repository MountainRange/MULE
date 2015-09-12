package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Josh Ting on 9/12/2015.
 */
public class StoreController implements Initializable, SceneAgent {

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
	private void handlePlayAction(ActionEvent e) {
		sceneLoader.setScene(MULE.GAME_CONFIG_SCENE);
	}

	@FXML
	private void handleOptionsAction(ActionEvent e) {
		sceneLoader.setScene(MULE.OPTIONS_SCENE);
	}

	@FXML
	private void handleCreditsAction(ActionEvent e) {
		sceneLoader.setScene(MULE.CREDITS_SCENE);
	}
}
