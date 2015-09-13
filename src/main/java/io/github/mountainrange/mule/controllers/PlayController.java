package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;
import io.github.mountainrange.mule.Tile;
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

	@FXML
	private Pane mapPane;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		g = new Grid(10, 10, mapPane);
		g.add(new Rectangle(0, 0, 1, 1), 2, 3);

		Circle c = new Circle(0, 0, 0.5);
		c.setFill(Color.GREEN);
		g.add(c, 3, 5);
		// g.remove(2, 5);

		ImageView image = new ImageView("pictures/ViPaint.png");
		image.setFitWidth(1);
		image.setFitHeight(1);
	    g.add(image, 4, 4);

		g.select(7, 3);
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
