package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.Config;
import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller to manage the first pane we see
 */
public class MainController implements Initializable, SceneAgent {

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

	public void onSetScene() {
		Config.getInstance().soundManager
				.changePlaylist(Config.getInstance().titlePlaylist);
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
