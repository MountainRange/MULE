package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.Config;
import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Matthew Keezer on 9/9/2015.
 */
public class OptionsController implements Initializable, SceneAgent {

	private SceneLoader sceneLoader;
	private MULE mule;

	@FXML
	private ComboBox<String> comboBox;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		assert comboBox != null : "fx:id=\"myChoices\" was not injected: check your FXML file 'foo.fxml'.";
		comboBox.setItems(FXCollections.observableArrayList());
		comboBox.getItems().add("TRUE");
		comboBox.getItems().add("FALSE");
	}

	public void setSceneParent(SceneLoader sceneLoader, MULE mule){
		this.sceneLoader = sceneLoader;
		this.mule = mule;
	}

	@FXML
	private void handleBackAction(ActionEvent e) {
		sceneLoader.goBack();
	}

	@FXML
	private void handleEnterAction(Event e) {
		comboBox.getSelectionModel().select(String.valueOf(Config.fadeEnabled).toUpperCase());
	}

	@FXML
	private void handleFadeAction(ActionEvent e) {
		Config.fadeEnabled = Boolean.parseBoolean(comboBox.getSelectionModel().getSelectedItem().toLowerCase());
	}

}
