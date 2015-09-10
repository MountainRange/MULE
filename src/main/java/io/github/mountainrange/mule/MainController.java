package io.github.mountainrange.mule;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Matthew Keezer on 9/9/2015.
 */
public class MainController implements Initializable, SceneAgent {

	SceneLoader sceneLoader;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

	public void setSceneParent(SceneLoader sceneLoader){
		this.sceneLoader = sceneLoader;
	}

	@FXML
	private void handlePlayAction(ActionEvent e) {
		sceneLoader.setScene(MULE.PLAY_SCENE);
	}

	@FXML
	private void handleOptionsAction(ActionEvent e) {
		sceneLoader.setScene(MULE.OPTIONS_SCENE);
	}

	@FXML
	private void handleCreditsAction(ActionEvent e) {
		sceneLoader.setScene(MULE.CREDITS_SCENE);
	}

	@FXML
	private void handleGameConfigAction(ActionEvent e) {
		sceneLoader.setScene(MULE.GAME_CONFIG_SCENE);
	}

	@FXML
	private void handlePlayerConfigAction(ActionEvent e) {
		sceneLoader.setScene(MULE.PLAYER_CONFIG_SCENE);
	}

}
