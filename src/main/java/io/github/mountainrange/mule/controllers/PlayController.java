package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;
import io.github.mountainrange.mule.Tile;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Matthew Keezer on 9/9/2015.
 */
public class PlayController implements Initializable, SceneAgent {

	private SceneLoader sceneLoader;
	private MULE mule;

	@FXML
	private Pane mapPane;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Rectangle test1 = Tile.PLAIN.getRect();
		test1.widthProperty().bind(mapPane.widthProperty().divide(2));
		test1.heightProperty().bind(mapPane.heightProperty().divide(2));
		mapPane.getChildren().addAll(test1);
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
	private void handleStoreAction(ActionEvent e) {
		sceneLoader.setScene(MULE.STORE_SCENE);
	}

}
