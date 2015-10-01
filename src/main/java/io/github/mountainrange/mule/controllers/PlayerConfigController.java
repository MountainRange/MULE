package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.Config;
import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.enums.Difficulty;
import io.github.mountainrange.mule.enums.ResourceType;
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
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Matthew Keezer on 9/9/2015.
 */
public class PlayerConfigController implements Initializable, SceneAgent {

	private SceneLoader sceneLoader;
	private MULE mule;
	private boolean invalidColor;
	private boolean emptyName;

	@FXML
	private Slider playerSlider;

	@FXML
	private Slider numSlider;

	@FXML
	private ComboBox<String> raceCombo;

	@FXML
	private Label colorLabel;

	@FXML
	private ColorPicker colorPicker;

	@FXML
	private Label nameLabel;
	@FXML
	private TextField nameField;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Config.getInstance().playerList = new Player[Config.getInstance().maxPlayers];
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
		numSlider.setValue(Config.getInstance().numOfPlayers);
		adjustPlayerCount();
	}

	@FXML
	private void handleNameAction(Event e) {
		emptyName = false;
		for (Player player : Config.getInstance().playerList) {
			if (player.getName().equals(new String(""))) {
				emptyName = true;
			}
		}
		if (emptyName) {
			nameLabel.setTextFill(Color.RED); // disallow empty name
		} else {
			nameLabel.setTextFill(Color.BLACK);
		}
		Config.getInstance().playerList[Config.getInstance().currentPlayer].setName(nameField.getText());
	}

	@FXML
	private void handleNumAction(Event e) {
		Config.getInstance().numOfPlayers = (int)numSlider.getValue();
		adjustPlayerCount();
	}

	@FXML
	private void handlePlayerAction(Event e) {
		changePlayer(((int) playerSlider.getValue()) - 1);
	}

	@FXML
	private void handleRaceAction(ActionEvent e) {
		Config.getInstance().playerList[Config.getInstance().currentPlayer].setRace(Race.values()[raceCombo
				.getSelectionModel().getSelectedIndex()]);
	}

	@FXML
	private void handleStartAction(ActionEvent e) {
		sceneLoader.setScene(MULE.PLAY_SCENE);
	}

	@FXML
	private void handleColorAction(ActionEvent e) {
		invalidColor = false;
		for (Player player : Config.getInstance().playerList) {
			if (colorPicker.getValue().equals(player.getColor())) {
				invalidColor = true;
			}
		}
		if (invalidColor) {
			colorLabel.setTextFill(Color.RED); // disallow same colors
		} else {
			colorLabel.setTextFill(Color.BLACK);
		}
		Config.getInstance().playerList[Config.getInstance().currentPlayer].setColor(colorPicker.getValue());
	}

	private void updateValues() {
		raceCombo.getSelectionModel().select(Config.getInstance().playerList[Config.getInstance().currentPlayer].getRace().ordinal());
		colorPicker.setValue(Config.getInstance().playerList[Config.getInstance().currentPlayer].getColor());
		nameField.setText(Config.getInstance().playerList[Config.getInstance().currentPlayer].getName());
		playerSlider.setValue(Config.getInstance().currentPlayer + 1);
	}

	private void adjustPlayerCount() {
		int difficulty = 0;
		if (Config.getInstance().difficulty == Difficulty.HILL) {
			difficulty = 0;
		} else if (Config.getInstance().difficulty == Difficulty.MESA) {
			difficulty = 1;
		} else if (Config.getInstance().difficulty == Difficulty.PLATEAU) {
			difficulty = 2;
		} else if (Config.getInstance().difficulty == Difficulty.MOUNTAIN) {
			difficulty = 3;
		}
		int foodCount;
		if (difficulty == 0) {
			foodCount = 8;
		} else {
			foodCount = 4;
		}
		int energyCount;
		if (difficulty == 0) {
			energyCount = 4;
		} else {
			energyCount = 2;
		}
		for (int i = 0; i < Config.getInstance().maxPlayers; i++) {
			if (Config.getInstance().playerList[i] == null) {
				Config.getInstance().playerList[i] = new Player(i);
				Config.getInstance().playerList[i].addStock(ResourceType.ENERGY, energyCount);
				Config.getInstance().playerList[i].addStock(ResourceType.FOOD, foodCount);
			}
		}
		playerSlider.setMax(Config.getInstance().numOfPlayers);
		if(Config.getInstance().currentPlayer >= Config.getInstance().numOfPlayers) {
			changePlayer(0);
		}
	}

	private void changePlayer(int id) {
		Config.getInstance().currentPlayer = id;
		updateValues();
	}

}
