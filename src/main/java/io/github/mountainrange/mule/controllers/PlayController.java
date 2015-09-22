package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;
import io.github.mountainrange.mule.enums.TerrainType;
import io.github.mountainrange.mule.gameplay.Tile;
import io.github.mountainrange.mule.gameplay.WorldMap;
import io.github.mountainrange.mule.enums.MapSize;
import io.github.mountainrange.mule.enums.MapType;
import io.github.mountainrange.mule.gameplay.VisualGrid;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * A class to manage the main Playing Map Screen
 */
public class PlayController implements Initializable, SceneAgent {

	private SceneLoader sceneLoader;
	private MULE mule;
	private VisualGrid g;
	private WorldMap map;
	private Tile[][] tiles;

	@FXML
	private Pane mapPane;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		g = new VisualGrid(9, 5, MapType.CLASSIC, MapSize.ALPS, mapPane);
		map = new WorldMap(g);

		g.movePlayer(1, 1);
	}

	public void setSceneParent(SceneLoader sceneLoader, MULE mule){
		this.sceneLoader = sceneLoader;
		this.mule = mule;
	}

	@FXML
	private void handleBackAction(ActionEvent e) {
		// g.movePlayer(g.getPlayerPosition().getX() + 1, g.getPlayerPosition().getY() + 1);
		// g.select(4,1);
		// g.move(4,2,1,1);
		sceneLoader.goBack();
	}

	@FXML
	private void handleTownAction(ActionEvent e) {
		sceneLoader.setScene(MULE.TOWN_SCENE);
	}

}
