package io.github.mountainrange.mule;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Matthew Keezer on 9/9/2015.
 */
public class GameConfigController implements Initializable, SceneAgent {

	SceneLoader sceneLoader;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

	public void setSceneParent(SceneLoader sceneLoader){
		this.sceneLoader = sceneLoader;
	}

	@FXML
	private void handleBackAction(ActionEvent e) {
		sceneLoader.setScene(MULE.MAIN_SCENE);
	}

}
