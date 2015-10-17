package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.Config;
import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
	private ComboBox<String> fadeCombo;

	@FXML
	private Button backButton;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		backButton.setCancelButton(true);
		assert fadeCombo != null : "fx:id=\"myChoices\" was not injected: check your FXML file 'foo.fxml'.";
		fadeCombo.setItems(FXCollections.observableArrayList());
		fadeCombo.getItems().add("TRUE");
		fadeCombo.getItems().add("FALSE");
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

	@FXML
	private void handleEnterAction(Event e) {
		fadeCombo.getSelectionModel().select(String.valueOf(Config.getInstance().fadeEnabled).toUpperCase());
	}

	@FXML
	private void handleFadeAction(ActionEvent e) {
		Config.getInstance().fadeEnabled = Boolean.parseBoolean(fadeCombo.getSelectionModel().getSelectedItem().toLowerCase());
	}

}
