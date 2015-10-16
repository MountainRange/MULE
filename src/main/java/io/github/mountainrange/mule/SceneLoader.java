package io.github.mountainrange.mule;

import io.github.mountainrange.mule.controllers.SceneAgent;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Stack;

/**
 * This class handles setting the displayed scene a given FXML layout.
 */
public class SceneLoader extends AnchorPane {

	private MULE mule;
	private HashMap<String, Node> scenes = new HashMap<>();
	private HashMap<String, SceneAgent> controllers = new HashMap<>();
	private Stack<String> sceneHistory;
	private boolean settingScene = false;

	public SceneLoader(MULE mule) {
		this.mule = mule; // application reference for frame, other sceneloaders, etc.
		sceneHistory = new Stack<>();
	}

	private void addScene(String name, Node scene) {
		scenes.put(name, scene);
	}

	private void addController(String name, SceneAgent scene) {
		controllers.put(name, scene);
	}

	// Loads the scene once so it never has to reload
	public boolean loadScene(String name, String resource) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
			Parent loadScreen = loader.load();
			SceneAgent sceneControl = loader.getController();
			sceneControl.setSceneParent(this, mule);
			addScene(name, loadScreen);
			addController(name, sceneControl);
			return true;
		} catch(Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	// swaps the current scene to another loaded scene
	public boolean setScene(final String name) {
		if (settingScene) {
			System.err.println("Cannot load scene while already loading another!\n");
			return false;
		}

		Node sceneNode = scenes.get(name);
		if (scenes.get(name) == null) {
			System.err.println("Scene hasn't been loaded!\n");
			return false;
		}

		settingScene = true;
		final DoubleProperty opacity = opacityProperty();

		if (!getChildren().isEmpty()) {
			if (Config.getInstance().fadeEnabled) {
				fade(name, opacity);
			} else {
				getChildren().remove(0);
				getChildren().add(0, sceneNode);
				setAnchors(sceneNode);
				settingScene = false;
			}
		} else {
			if (Config.getInstance().fadeEnabled) {
				fadeIn(name, opacity);
			} else {
				getChildren().add(sceneNode);
				setAnchors(sceneNode);
				settingScene = false;
			}
		}

		controllers.get(name).onSetScene();

		sceneHistory.push(name);
		return true;
	}

	public String getCurrentScene() {
		return sceneHistory.peek();
	}

	public boolean unloadScene(String name) {
		if (scenes.remove(name) == null) {
			System.err.println("Scene doesn't exist");
			return false;
		} else {
			return true;
		}
	}

	// fills the anchorpane
	private void setAnchors(Node node) {
		setTopAnchor(node, 0.0);
		setBottomAnchor(node, 0.0);
		setRightAnchor(node, 0.0);
		setLeftAnchor(node, 0.0);
	}

	// fade animation
	private void fade(String name, DoubleProperty opacity) {
		Timeline fade = new Timeline(
				new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
				new KeyFrame(new Duration(200), (ActionEvent e) -> {
					getChildren().remove(0);
					getChildren().add(0, scenes.get(name));
					setAnchors(scenes.get(name));
					Timeline fadeIn = new Timeline(
							new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
							new KeyFrame(new Duration(200), new KeyValue(opacity, 1.0)));
					fadeIn.play();
					settingScene = false;
				}, new KeyValue(opacity, 0.0)));
		fade.play();
	}

	// fade for first menu (Only fires if fade is enabled before the program starts, currently never)
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
		settingScene = false;
	}

	// Go to previous scene
	public void goBack() {
		if (!settingScene) {
			sceneHistory.pop();
			setScene(sceneHistory.pop());
		}
	}
}
