package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneAgent;
import io.github.mountainrange.mule.SceneLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Matthew Keezer on 9/9/2015.
 */
public class MenuBarController implements Initializable, SceneAgent {

	private SceneLoader sceneLoader;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

	public void setSceneParent(SceneLoader sceneLoader){
		this.sceneLoader = sceneLoader;
	}

	@FXML
	private void handleFadeAction(ActionEvent e) {
		Config.fadeEnabled = !Config.fadeEnabled;
	}

	@FXML
	private void handleCloseAction(ActionEvent e) {
		MULE.primaryStage.close();
	}

	@FXML
	private void handleAboutAction(ActionEvent e) {

		SceneLoader sceneLoader = new SceneLoader();
		sceneLoader.loadScene(MULE.ABOUT_SCENE, MULE.ABOUT_SCENE_FXML);
		sceneLoader.setScene(MULE.ABOUT_SCENE);

		SceneLoader menuBar = new SceneLoader();
		menuBar.loadScene(MULE.MENU_BAR_SCENE, MULE.MENU_BAR_SCENE_FXML);
		menuBar.setScene(MULE.MENU_BAR_SCENE);

		BorderPane overlay = new BorderPane();
		overlay.setCenter(sceneLoader);
		overlay.setTop(menuBar);

		Scene mainScene = new Scene(overlay, 640, 360);

		Stage secondaryStage = new Stage();
		secondaryStage.setTitle("About");
		secondaryStage.setScene(mainScene);
		secondaryStage.setMinHeight(480);
		secondaryStage.setMinWidth(800);
		secondaryStage.show();
	}

}
