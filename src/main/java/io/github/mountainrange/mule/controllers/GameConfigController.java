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
	private ComboBox<String> comboBox;

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
		assert comboBox != null : "fx:id=\"myChoices\" was not injected: check your FXML file 'foo.fxml'.";
		comboBox.setItems(FXCollections.observableArrayList());
		comboBox.getItems().add("CLASSIC");
		comboBox.getItems().add("RANDOM");
		comboBox.getItems().add("EXPERIMENTAL");
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
		comboBox.getSelectionModel().select(Config.mapType.ordinal());
	}

	@FXML
	private void handleDiffAction(Event e) {
		Config.difficulty = Config.Difficulty.values()[(int)diffSlider.getValue()];
	}

	@FXML
	private void handleMapSizeAction(Event e) {
		Config.mapSize = Config.MapSize.values()[(int)mapSlider.getValue()];
	}

	@FXML
	private void handleMapTypeAction(ActionEvent e) {
		Config.mapType = Config.MapType.values()[comboBox.getSelectionModel().getSelectedIndex()];
	}

	@FXML
	private void handleContinueAction(ActionEvent e) {
		sceneLoader.setScene(MULE.PLAYER_CONFIG_SCENE);
	}

}
