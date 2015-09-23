package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.Config;
import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioMenuItem;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Matthew Keezer on 9/9/2015.
 */
public class MenuBarController implements Initializable, SceneAgent {

	private SceneLoader sceneLoader;
	private MULE mule;

	@FXML
	private RadioMenuItem fadeRadio;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

	public void setSceneParent(SceneLoader sceneLoader, MULE mule){
		this.sceneLoader = sceneLoader;
		this.mule = mule;
	}

	public void onSetScene() {
	}

	@FXML
	private void handleFadeAction(ActionEvent e) {
		Config.fadeEnabled = fadeRadio.isSelected();
	}

	@FXML
	private void handleOptionsAction(Event e) {
		fadeRadio.selectedProperty().setValue(Config.fadeEnabled);
	}

	@FXML
	private void handleCloseAction(ActionEvent e) {
		mule.close();
	}

	@FXML
	private void handleFullscreenAction(ActionEvent e) {
		mule.setFullscreen(true);
	}

	@FXML
	private void handleAboutAction(ActionEvent e) {
		mule.setCenterScene(MULE.ABOUT_SCENE);
	}

}
