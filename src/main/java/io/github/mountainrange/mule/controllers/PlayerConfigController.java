package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.Config;
import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.gameplay.Player;
import io.github.mountainrange.mule.SceneLoader;
import io.github.mountainrange.mule.enums.Race;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Matthew Keezer on 9/9/2015.
 */
public class PlayerConfigController implements Initializable, SceneAgent {

	private SceneLoader sceneLoader;
	private MULE mule;

	@FXML
	private Slider playerSlider;

	@FXML
	private Slider numSlider;

	@FXML
	private ComboBox<String> raceCombo;

	@FXML
	private ColorPicker colorPicker;

	@FXML
	private TextField nameField;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Config.playerList = new Player[Config.maxPlayers];
		adjustPlayerCount();
		assert raceCombo != null : "fx:id=\"myChoices\" was not injected: check your FXML file 'foo.fxml'.";
		raceCombo.setItems(FXCollections.observableArrayList());
		raceCombo.getItems().add("FOLD");
		raceCombo.getItems().add("FAULT-BLOCK");
		raceCombo.getItems().add("DOME");
		raceCombo.getItems().add("VOLCANIC");
		raceCombo.getItems().add("PLATEAU");
		updateValues();
	}

	public void onSetScene() {

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
		adjustPlayerCount();
	}

	@FXML
	private void handleNameAction(Event e) {
		Config.playerList[Config.currentPlayer].setName(nameField.getText());
	}

	@FXML
	private void handleNumAction(Event e) {
		Config.numOfPlayers = (int)numSlider.getValue();
		adjustPlayerCount();
	}

	@FXML
	private void handlePlayerAction(Event e) {
		changePlayer(((int)playerSlider.getValue()) - 1);
	}

	@FXML
	private void handleRaceAction(ActionEvent e) {
		Config.playerList[Config.currentPlayer].setRace(Race.values()[raceCombo
				.getSelectionModel().getSelectedIndex()]);
	}

	@FXML
	private void handleStartAction(ActionEvent e) {
		sceneLoader.setScene(MULE.PLAY_SCENE);
	}

	@FXML
	private void handleColorAction(ActionEvent e) {
		Config.playerList[Config.currentPlayer].setColor(colorPicker.getValue());
	}

	private void updateValues() {
		raceCombo.getSelectionModel().select(Config.playerList[Config.currentPlayer].getRace().ordinal());
		colorPicker.setValue(Config.playerList[Config.currentPlayer].getColor());
		nameField.setText(Config.playerList[Config.currentPlayer].getName());
		playerSlider.setValue(Config.currentPlayer + 1);
	}

	private void adjustPlayerCount() {
		for (int i = 0; i < Config.maxPlayers; i++) {
			if (Config.playerList[i] == null) {
				Config.playerList[i] = new Player(i);
			}
		}
		playerSlider.setMax(Config.numOfPlayers);
		if(Config.currentPlayer >= Config.numOfPlayers) {
			changePlayer(0);
		}
	}

	private void changePlayer(int id) {
		Config.currentPlayer = id;
		updateValues();
	}

}
