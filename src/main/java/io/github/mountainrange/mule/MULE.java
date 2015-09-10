
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
	public static final String MAIN_SCENE_FXML = "/fxml/Main.fxml";
	public static final String PLAY_SCENE = "play";
	public static final String PLAY_SCENE_FXML = "/fxml/Play.fxml";
	public static final String SETTINGS_SCENE = "roulette";
	public static final String SETTINGS_SCENE_FXML = "roulette.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{
		SceneLoader sceneLoader = new SceneLoader();
		sceneLoader.loadScene(MAIN_SCENE, MAIN_SCENE_FXML);
		sceneLoader.loadScene(PLAY_SCENE, PLAY_SCENE_FXML);
		sceneLoader.setScene(MAIN_SCENE);

		Scene mainScene = new Scene(sceneLoader, 640, 360);

//        Parent root = FXMLLoader.load(
//				getClass().getResource("/fxml/Main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
