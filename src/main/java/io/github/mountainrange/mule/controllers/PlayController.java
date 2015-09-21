package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;
import io.github.mountainrange.mule.Tile;
import io.github.mountainrange.mule.WorldMap;
import io.github.mountainrange.mule.enums.MapSize;
import io.github.mountainrange.mule.enums.MapType;
import io.github.mountainrange.mule.gameplay.Grid;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Matthew Keezer on 9/9/2015.
 */
public class PlayController implements Initializable, SceneAgent {

	private SceneLoader sceneLoader;
	private MULE mule;
	private Grid g;
	private WorldMap map;
	private Tile[][] tiles;

	@FXML
	private Pane mapPane;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		g = new Grid(9, 5, mapPane);
		map = new WorldMap(MapType.CLASSIC, MapSize.ALPS);
		tiles = map.getTiles();

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 9; j++) {
				g.add(tiles[i][j], j, i);
			}
		}

		//ImageView image = new ImageView("pictures/ViPaint.png");
		//image.setFitWidth(1);
		//image.setFitHeight(1);
	    //g.add(image, 4, 4);

		//g.select(7, 3);

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
	private void handleTownAction(ActionEvent e) {
		sceneLoader.setScene(MULE.TOWN_SCENE);
	}

}
