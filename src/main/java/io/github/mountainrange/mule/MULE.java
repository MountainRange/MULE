
package io.github.mountainrange.mule;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.border.Border;
import java.awt.*;

public class MULE extends Application {

	public static final String MAIN_SCENE = "main";
	public static final String MAIN_SCENE_FXML = "/fxml/main.fxml";
	public static final String PLAY_SCENE = "play";
	public static final String PLAY_SCENE_FXML = "/fxml/play.fxml";
	public static final String OPTIONS_SCENE = "options";
	public static final String OPTIONS_SCENE_FXML = "/fxml/options.fxml";
	public static final String CREDITS_SCENE = "credits";
	public static final String CREDITS_SCENE_FXML = "/fxml/credits.fxml";
	public static final String GAME_CONFIG_SCENE = "gameConfiguration";
	public static final String GAME_CONFIG_SCENE_FXML = "/fxml/gameConfiguration.fxml";
	public static final String PLAYER_CONFIG_SCENE = "playerConfiguration";
	public static final String PLAYER_CONFIG_SCENE_FXML = "/fxml/playerConfiguration.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{
		SceneLoader sceneLoader = new SceneLoader();
		sceneLoader.loadScene(MAIN_SCENE, MAIN_SCENE_FXML);
		sceneLoader.loadScene(PLAY_SCENE, PLAY_SCENE_FXML);
		sceneLoader.loadScene(OPTIONS_SCENE, OPTIONS_SCENE_FXML);
		sceneLoader.loadScene(CREDITS_SCENE, CREDITS_SCENE_FXML);
		sceneLoader.loadScene(GAME_CONFIG_SCENE, GAME_CONFIG_SCENE_FXML);
		sceneLoader.loadScene(PLAYER_CONFIG_SCENE, PLAYER_CONFIG_SCENE_FXML);
		sceneLoader.setScene(MAIN_SCENE);

		Scene mainScene = new Scene(sceneLoader, 640, 360);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(mainScene);
		primaryStage.setMinHeight(480);
		primaryStage.setMinWidth(800);
		primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
