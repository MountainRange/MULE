
package io.github.mountainrange.mule;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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

	public static final String MENU_BAR_SCENE = "menuBar";
	public static final String MENU_BAR_SCENE_FXML = "/fxml/menuBar.fxml";

	public static final String ABOUT_SCENE = "about";
	public static final String ABOUT_SCENE_FXML = "/fxml/about.fxml";

	public static Stage primaryStage;

    @Override
    public void start(Stage pStage) throws Exception{

		primaryStage = pStage;

		SceneLoader sceneLoader = new SceneLoader();
		sceneLoader.loadScene(MAIN_SCENE, MAIN_SCENE_FXML);
		sceneLoader.loadScene(PLAY_SCENE, PLAY_SCENE_FXML);
		sceneLoader.loadScene(OPTIONS_SCENE, OPTIONS_SCENE_FXML);
		sceneLoader.loadScene(CREDITS_SCENE, CREDITS_SCENE_FXML);
		sceneLoader.loadScene(GAME_CONFIG_SCENE, GAME_CONFIG_SCENE_FXML);
		sceneLoader.loadScene(PLAYER_CONFIG_SCENE, PLAYER_CONFIG_SCENE_FXML);
		sceneLoader.setScene(MAIN_SCENE);

		SceneLoader menuBar = new SceneLoader();
		menuBar.loadScene(MENU_BAR_SCENE, MENU_BAR_SCENE_FXML);
		menuBar.setScene(MENU_BAR_SCENE);

		BorderPane overlay = new BorderPane();
		overlay.setCenter(sceneLoader);
		overlay.setTop(menuBar);

		Scene mainScene = new Scene(overlay, 640, 360);

        primaryStage.setTitle("MULE");
        primaryStage.setScene(mainScene);
		primaryStage.setMinHeight(480);
		primaryStage.setMinWidth(800);
		primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
