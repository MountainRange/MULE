package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneAgent;
import io.github.mountainrange.mule.SceneLoader;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Matthew Keezer on 9/9/2015.
 */
public class OptionsController implements Initializable, SceneAgent {

	private SceneLoader sceneLoader;

	@FXML
	private ComboBox<String> comboBox;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		assert comboBox != null : "fx:id=\"myChoices\" was not injected: check your FXML file 'foo.fxml'.";
		comboBox.setItems(FXCollections.observableArrayList());
		comboBox.getItems().add("TRUE");
		comboBox.getItems().add("FALSE");
		comboBox.getSelectionModel().select(String.valueOf(Config.fadeEnabled).toUpperCase());
	}

	public void setSceneParent(SceneLoader sceneLoader){
		this.sceneLoader = sceneLoader;
	}

	@FXML
	private void handleBackAction(ActionEvent e) {
		sceneLoader.setScene(MULE.MAIN_SCENE);
	}

	@FXML
	private void handleFadeAction(ActionEvent e) {
		Config.fadeEnabled = Boolean.parseBoolean(comboBox.getSelectionModel().getSelectedItem().toLowerCase());
	}

}
