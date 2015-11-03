package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.Config;
import io.github.mountainrange.mule.GameManager;
import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;
import io.github.mountainrange.mule.gameplay.WorldMap;
import io.github.mountainrange.mule.enums.MapSize;
import io.github.mountainrange.mule.enums.MapType;
import io.github.mountainrange.mule.gameplay.javafx.VisualGrid;
import io.github.mountainrange.mule.gameplay.javafx.VisualTile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.*;
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

	@FXML
	private void handleSave() {
		try
		{
			FileOutputStream fileOut =
					new FileOutputStream("manager.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(manager);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in manager.ser");
		}catch(IOException i)
		{
			i.printStackTrace();
		}
	}

	@FXML
	private void handleLoad() {
		try
		{
			FileInputStream fileIn = new FileInputStream("manager.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			manager.endTimers();
			manager = (GameManager) in.readObject();
			manager.initialize(map, turnLabel, resourceLabel, sceneLoader);
			mule.setGameManager(manager);
			in.close();
			fileIn.close();
		}catch(IOException i)
		{
			i.printStackTrace();
			return;
		}catch(ClassNotFoundException c)
		{
			System.out.println("Employee class not found");
			c.printStackTrace();
			return;
		}
		System.out.println("Deserialized Employee...");
	}

}
