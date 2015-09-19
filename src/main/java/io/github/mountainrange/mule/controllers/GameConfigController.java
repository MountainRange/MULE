package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.Config;
import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;
import io.github.mountainrange.mule.enums.Difficulty;
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
				switch (s) {
					case "Hill":
						return 0.0;
					case "Mesa":
						return 1.0;
					case "Plateau":
						return 2.0;
					case "Mountain":
						return 3.0;

					default:
						return 4.0;
				}
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
				switch (s) {
					case "Pyrenees":
						return 0.0;
					case "Alps":
						return 1.0;
					case "Himalayas":
						return 2.0;

					default:
						return 3.0;
				}
			}
		});
		assert mapTypeCombo != null : "fx:id=\"myChoices\" was not injected: check your FXML file 'foo.fxml'.";
		mapTypeCombo.setItems(FXCollections.observableArrayList());
		mapTypeCombo.getItems().add("CLASSIC");
		mapTypeCombo.getItems().add("RANDOM");
		mapTypeCombo.getItems().add("EXPERIMENTAL");
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
		diffSlider.setValue(Config.difficulty.ordinal());
		mapSlider.setValue(Config.mapSize.ordinal());
		mapTypeCombo.getSelectionModel().select(Config.mapType.ordinal());
	}

	@FXML
	private void handleDiffAction(Event e) {
		Config.difficulty = Difficulty.values()[(int)diffSlider.getValue()];
	}

	@FXML
	private void handleMapSizeAction(Event e) {
		Config.mapSize = MapSize.values()[(int)mapSlider.getValue()];
	}

	@FXML
	private void handleMapTypeAction(ActionEvent e) {
		Config.mapType = MapType.values()[mapTypeCombo.getSelectionModel().getSelectedIndex()];
	}

	@FXML
	private void handleContinueAction(ActionEvent e) {
		sceneLoader.setScene(MULE.PLAYER_CONFIG_SCENE);
	}

}
