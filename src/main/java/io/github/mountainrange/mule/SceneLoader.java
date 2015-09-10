package io.github.mountainrange.mule;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.HashMap;

/**
 * Created by Matthew Keezer on 9/9/2015.
 */
public class SceneLoader extends StackPane {

	private HashMap<String, Node> scenes = new HashMap<>();

	private void addScene(String name, Node scene) {
		scenes.put(name, scene);
	}

	public boolean loadScene(String name, String resource) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
			Parent loadScreen = (Parent) loader.load();
			SceneAgent sceneControl = ((SceneAgent) loader.getController());
			sceneControl.setSceneParent(this);
			addScene(name, loadScreen);
			return true;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean setScene(final String name) {

		if (scenes.get(name) != null) {
			final DoubleProperty opacity = opacityProperty();

			if (!getChildren().isEmpty()) {
				Timeline fade = new Timeline(
					new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
					new KeyFrame(new Duration(200),
						new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								getChildren().remove(0);
								getChildren().add(0, scenes.get(name));
								Timeline fadeIn = new Timeline(
									new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
									new KeyFrame(new Duration(200), new KeyValue(opacity, 1.0)));
								fadeIn.play();
							}
						}, new KeyValue(opacity, 0.0)));
				fade.play();
			} else {
				setOpacity(0.0);
				getChildren().add(scenes.get(name));
				Timeline fadeIn = new Timeline(
					new KeyFrame(Duration.ZERO,
						new KeyValue(opacity, 0.0)),
					new KeyFrame(new Duration(200),
						new KeyValue(opacity, 1.0)));
				fadeIn.play();
			}
			return true;
		} else {
			System.out.println("scene hasn't been loaded!\n");
			return false;
		}
	}

	public boolean unloadScene(String name) {
		if(scenes.remove(name) == null) {
			System.out.println("Screen didn't exist");
			return false;
		} else {
			return true;
		}
	}
}
