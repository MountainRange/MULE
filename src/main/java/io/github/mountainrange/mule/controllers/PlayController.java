package io.github.mountainrange.mule.controllers;

import io.github.jgkamat.JayLayer.JayLayer;
import io.github.mountainrange.mule.Config;
import io.github.mountainrange.mule.GameManager;
import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;
import io.github.mountainrange.mule.gameplay.WorldMap;
import io.github.mountainrange.mule.enums.MapSize;
import io.github.mountainrange.mule.gameplay.javafx.VisualGrid;
import io.github.mountainrange.mule.gameplay.javafx.VisualTile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * A class to manage the main Playing Map Screen
 */
public class PlayController implements Initializable, SceneAgent {

	private SceneLoader sceneLoader;
	private MULE mule;
	private VisualGrid<VisualTile> g;
	private WorldMap map;
	private GameManager manager;

	@FXML
	private Pane mapPane;

	@FXML
	private Label turnLabel;

	@FXML
	private Label resourceLabel;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}

	public void setSceneParent(SceneLoader sceneLoader, MULE mule){
		this.sceneLoader = sceneLoader;
		this.mule = mule;
	}

	public void onSetScene() {
		if (g == null || map == null) {
			g = new VisualGrid<>(9, 5, Config.getInstance().mapType, MapSize.ALPS, mapPane);
			map = new WorldMap(g, Config.getInstance().mapType);
		}
		if (manager == null) {
			manager = new GameManager(map, turnLabel, resourceLabel, sceneLoader);
		}
		manager.setInAuction(false);
		mule.setGameManager(manager);

		Config.getInstance().soundManager
				.changePlaylist(Config.getInstance().gamePlaylist);
	}
	@FXML
	private void handleMouseMoved(MouseEvent e) {
	}

	@FXML
	private void handleMousePressed(MouseEvent e) {
		manager.handleMouse(e);
	}

	@FXML
	private void handleMouseReleased(MouseEvent e) {
		manager.handleMouse(e);
	}

	@FXML
	private void handleKeyPress(KeyEvent e) {
		manager.handleKey(e);
	}

	@FXML
	private void handleKeyRelease(KeyEvent e) {

	}

	@FXML
	private void handleBackAction(ActionEvent e) {
		// g.randomize();
		sceneLoader.goBack();
	}

	@FXML
	private void handlePassAction(ActionEvent e) {
		manager.incrementTurn();
	}

}
