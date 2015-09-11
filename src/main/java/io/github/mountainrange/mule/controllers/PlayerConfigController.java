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
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Matthew Keezer on 9/9/2015.
 */
public class PlayerConfigController implements Initializable, SceneAgent {

	private SceneLoader sceneLoader;
	private MULE mule;

	@FXML
	private Slider numSlider;

	@FXML
	private ComboBox<String> raceCombo;

	@FXML
	private TextField nameField;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		assert raceCombo != null : "fx:id=\"myChoices\" was not injected: check your FXML file 'foo.fxml'.";
		raceCombo.setItems(FXCollections.observableArrayList());
		raceCombo.getItems().add("HUMAN");
		raceCombo.getItems().add("FLAPPER");
		raceCombo.getItems().add("BONZOID");
		raceCombo.getItems().add("UGAITE");
		raceCombo.getItems().add("BUZZITE");
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
		numSlider.setValue(Config.numOfPlayers);
		raceCombo.getSelectionModel().select(Config.race.ordinal());
		nameField.setText(Config.playerName);
	}

	@FXML
	private void handleNameAction(Event e) {
		Config.playerName = nameField.getText();
	}

	@FXML
	private void handleNumAction(Event e) {
		Config.numOfPlayers = (int)numSlider.getValue();
	}

	@FXML
	private void handleRaceAction(ActionEvent e) {
		Config.race = Config.Race.values()[raceCombo.getSelectionModel().getSelectedIndex()];
	}

	@FXML
	private void handleStartAction(ActionEvent e) {
		sceneLoader.setScene(MULE.PLAY_SCENE);
	}

}
