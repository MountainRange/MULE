package io.github.mountainrange.mule;

import io.github.mountainrange.mule.controllers.SceneAgent;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Stack;

/**
 * Created by Matthew Keezer on 9/9/2015.
 */
public class SceneLoader extends AnchorPane {

	private MULE mule;
	private HashMap<String, Node> scenes = new HashMap<>();
	private Stack<String> sceneHistory;
	private String currentScene;
	private String previousScene;

	public SceneLoader(MULE mule) {
		this.mule = mule;
		sceneHistory = new Stack<String>();
	}

	private void addScene(String name, Node scene) {
		scenes.put(name, scene);
	}

	public boolean loadScene(String name, String resource) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
			Parent loadScreen = (Parent) loader.load();
			SceneAgent sceneControl = ((SceneAgent) loader.getController());
			sceneControl.setSceneParent(this, mule);
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
				if (Config.fadeEnabled) {
					fade(name, opacity);
				} else {
					getChildren().remove(0);
					getChildren().add(0, scenes.get(name));
					setAnchors(scenes.get(name));
				}
			} else {
				if (Config.fadeEnabled) {
					fadeIn(name, opacity);
				} else {
					getChildren().add(scenes.get(name));
					setAnchors(scenes.get(name));
				}
			}
			sceneHistory.push(name);
			return true;
		} else {
			System.out.println("Scene hasn't been loaded!\n");
			return false;
		}
	}

	public boolean unloadScene(String name) {
		if (scenes.remove(name) == null) {
			System.out.println("Scene doesn't exist");
			return false;
		} else {
			return true;
		}
	}

	private void setAnchors(Node node) {
		setTopAnchor(node, 0.0);
		setBottomAnchor(node, 0.0);
		setRightAnchor(node, 0.0);
		setLeftAnchor(node, 0.0);
	}

	private void fade(String name, DoubleProperty opacity) {
		Timeline fade = new Timeline(
				new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
				new KeyFrame(new Duration(200),
						new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								getChildren().remove(0);
								getChildren().add(0, scenes.get(name));
								setAnchors(scenes.get(name));
								Timeline fadeIn = new Timeline(
										new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
										new KeyFrame(new Duration(200), new KeyValue(opacity, 1.0)));
								fadeIn.play();
							}
						}, new KeyValue(opacity, 0.0)));
		fade.play();
	}

	private void fadeIn(String name, DoubleProperty opacity) {
		setOpacity(0.0);
		getChildren().add(scenes.get(name));
		setAnchors(scenes.get(name));
		Timeline fadeIn = new Timeline(
				new KeyFrame(Duration.ZERO,
						new KeyValue(opacity, 0.0)),
				new KeyFrame(new Duration(200),
						new KeyValue(opacity, 1.0)));
		fadeIn.play();
	}

	public void goBack() {
		sceneHistory.pop();
		setScene(sceneHistory.pop());
	}
}
