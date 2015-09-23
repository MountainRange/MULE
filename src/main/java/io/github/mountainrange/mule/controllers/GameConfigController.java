package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.Config;
import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;
import io.github.mountainrange.mule.enums.Difficulty;
import io.github.mountainrange.mule.enums.GameType;
import io.github.mountainrange.mule.enums.MapSize;
import io.github.mountainrange.mule.enums.MapType;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Matthew Keezer on 9/9/2015.
 */
public class GameConfigController implements Initializable, SceneAgent {

	private SceneLoader sceneLoader;
	private MULE mule;

	@FXML
	private Slider diffSlider;

	@FXML
	private Slider mapSlider;

	@FXML
	private ComboBox<String> gameTypeCombo;

	@FXML
	private ComboBox<String> mapTypeCombo;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		diffSlider.setLabelFormatter(new StringConverter<Double>() {
			@Override
			public String toString(Double n) {
				if (n == 0) return "Hill";
				if (n == 1) return "Mesa";
				if (n == 2) return "Plateau";

				return "Mountain";
			}

			@Override
			public Double fromString(String s) {
				if (s.equals("Hill")) {
					return 0.0;
				} else if (s.equals("Mesa")) {
					return 1.0;
				} else if (s.equals("Plateau")) {
					return 2.0;
				}
				return 3.0;
			}
		});
		mapSlider.setLabelFormatter(new StringConverter<Double>() {
			@Override
			public String toString(Double n) {
				if (n == 0) return "Pyrenees";
				if (n == 1) return "Alps";

				return "Himalayas";
			}

			@Override
			public Double fromString(String s) {
				if (s.equals("Pyrenees")) {
					return 0.0;
				} else if (s.equals("Alps")) {
					return 1.0;
				}
				return 2.0;
			}
		});
		assert gameTypeCombo != null : "fx:id=\"gameTypeCombo\" was not injected: check your FXML file 'foo.fxml'.";
		gameTypeCombo.setItems(FXCollections.observableArrayList());
		gameTypeCombo.getItems().add("HOTSEAT");
		gameTypeCombo.getItems().add("SIMULTANEOUS");
		gameTypeCombo.getItems().add("ONLINE");
		assert mapTypeCombo != null : "fx:id=\"mapTypeCombo\" was not injected: check your FXML file 'foo.fxml'.";
		mapTypeCombo.setItems(FXCollections.observableArrayList());
		mapTypeCombo.getItems().add("CLASSIC");
		mapTypeCombo.getItems().add("RANDOM");
		mapTypeCombo.getItems().add("EXPERIMENTAL");
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
		diffSlider.setValue(Config.getInstance().difficulty.ordinal());
		mapSlider.setValue(Config.getInstance().mapSize.ordinal());
		mapTypeCombo.getSelectionModel().select(Config.getInstance().mapType.ordinal());
		gameTypeCombo.getSelectionModel().select(Config.getInstance().gameType.ordinal());
	}

	@FXML
	private void handleDiffAction(Event e) {
		Config.getInstance().difficulty = Difficulty.values()[(int)diffSlider.getValue()];
	}

	@FXML
	private void handleGameTypeAction(Event e) {
		Config.getInstance().gameType = GameType.values()[gameTypeCombo.getSelectionModel().getSelectedIndex()];
	}

	@FXML
	private void handleMapSizeAction(Event e) {
		Config.getInstance().mapSize = MapSize.values()[(int)mapSlider.getValue()];
	}

	@FXML
	private void handleMapTypeAction(ActionEvent e) {
		Config.getInstance().mapType = MapType.values()[mapTypeCombo.getSelectionModel().getSelectedIndex()];
	}

	@FXML
	private void handleContinueAction(ActionEvent e) {
		sceneLoader.setScene(MULE.PLAYER_CONFIG_SCENE);
	}

}
