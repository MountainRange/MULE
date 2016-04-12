package io.github.mountainrange.mule;

import io.github.mountainrange.mule.Config;
import io.github.jgkamat.JayLayer.JayLayer;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class MULE extends Application {

	public static final double HSIZE = 800;
	public static final double VSIZE = 480;

	public static final String MAIN_SCENE = "main";
	public static final String MAIN_SCENE_FXML = "/fxml/main.fxml";
	public static final String STORE_SCENE = "storeMenu";
	public static final String STORE_SCENE_FXML = "/fxml/storeMenu.fxml";
	public static final String PUB_SCENE = "pubMenu";
	public static final String PUB_SCENE_FXML = "/fxml/pubMenu.fxml";
	public static final String LAND_OFFICE_SCENE = "landOfficeMenu";
	public static final String LAND_OFFICE_SCENE_FXML = "/fxml/landOfficeMenu.fxml";
	public static final String ASSAY_OFFICE_SCENE = "assayOfficeMenu";
	public static final String ASSAY_OFFICE_SCENE_FXML = "/fxml/assayOfficeMenu.fxml";
	public static final String TOWN_SCENE = "townMenu";
	public static final String TOWN_SCENE_FXML = "/fxml/townMenu.fxml";
	public static final String PLAY_SCENE = "play";
	public static final String PLAY_SCENE_FXML = "/fxml/play.fxml";
	public static final String OPTIONS_SCENE = "options";
	public static final String OPTIONS_SCENE_FXML = "/fxml/options.fxml";
	public static final String CONTROLS_SCENE = "controls";
	public static final String CONTROLS_SCENE_FXML = "/fxml/controls.fxml";
	public static final String CREDITS_SCENE = "credits";
	public static final String CREDITS_SCENE_FXML = "/fxml/credits.fxml";
	public static final String GAME_CONFIG_SCENE = "gameConfiguration";
	public static final String GAME_CONFIG_SCENE_FXML = "/fxml/gameConfiguration.fxml";
	public static final String PLAYER_CONFIG_SCENE = "playerConfiguration";
	public static final String PLAYER_CONFIG_SCENE_FXML = "/fxml/playerConfiguration.fxml";
	public static final String AUCTION_SCENE = "auction";
	public static final String AUCTION_SCENE_FXML = "/fxml/auction.fxml";

	public static final String ABOUT_SCENE = "about";
	public static final String ABOUT_SCENE_FXML = "/fxml/about.fxml";

	public static final String MENU_BAR_SCENE = "menuBar";
	public static final String MENU_BAR_SCENE_FXML = "/fxml/menuBar.fxml";

	private Stage primaryStage;
	private SceneLoader sceneLoader;
	private SceneLoader menuBar;
	private GameManager manager;



	@Override
	public void start(Stage pStage) throws Exception {

		primaryStage = pStage;

		File file = new File(System.getProperty("user.dir") + "/src/main/resources/pictures/MountainRange.png");
		if (!file.exists()) {
			System.out.println(System.getProperty("user.dir") + "/src/main/resources/pictures/MountainRange.png");
		}
		Image loadingImage = new Image(file.toURI().toString());
		StackPane root = new StackPane();
		ImageView view = new ImageView(loadingImage);
		view.setFitHeight(600);
		view.setFitWidth(600);
		root.getChildren().add(view);
		Scene loadingScene = new Scene(root, 600, 600);

		primaryStage.setTitle("MULE");
		primaryStage.setScene(loadingScene);
		primaryStage.setMinHeight(600);
		primaryStage.setMinWidth(600);

		primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
		primaryStage.show();
		long startTime = System.currentTimeMillis();

		// we load all these scenes after displaying the loading screen
		sceneLoader = new SceneLoader(this);
		sceneLoader.loadScene(MAIN_SCENE, MAIN_SCENE_FXML);
		sceneLoader.setScene(MAIN_SCENE);
		sceneLoader.loadScene(STORE_SCENE, STORE_SCENE_FXML);
		sceneLoader.loadScene(PUB_SCENE, PUB_SCENE_FXML);
		sceneLoader.loadScene(LAND_OFFICE_SCENE, LAND_OFFICE_SCENE_FXML);
		sceneLoader.loadScene(ASSAY_OFFICE_SCENE, ASSAY_OFFICE_SCENE_FXML);
		sceneLoader.loadScene(TOWN_SCENE, TOWN_SCENE_FXML);
		sceneLoader.loadScene(PLAY_SCENE, PLAY_SCENE_FXML);
		sceneLoader.loadScene(OPTIONS_SCENE, OPTIONS_SCENE_FXML);
		sceneLoader.loadScene(CONTROLS_SCENE, CONTROLS_SCENE_FXML);
		sceneLoader.loadScene(CREDITS_SCENE, CREDITS_SCENE_FXML);
		sceneLoader.loadScene(GAME_CONFIG_SCENE, GAME_CONFIG_SCENE_FXML);
		sceneLoader.loadScene(PLAYER_CONFIG_SCENE, PLAYER_CONFIG_SCENE_FXML);
		sceneLoader.loadScene(ABOUT_SCENE, ABOUT_SCENE_FXML);
		sceneLoader.loadScene(AUCTION_SCENE, AUCTION_SCENE_FXML);

		menuBar = new SceneLoader(this);
		menuBar.loadScene(MENU_BAR_SCENE, MENU_BAR_SCENE_FXML);
		menuBar.setScene(MENU_BAR_SCENE);

		BorderPane overlay = new BorderPane();
		overlay.setCenter(sceneLoader);
		overlay.setTop(menuBar);

		Scene mainScene = new Scene(overlay, 640, 360);
		PauseTransition delay = new PauseTransition(Duration.seconds(3));
		delay.setOnFinished(event -> primaryStage.setScene(mainScene));
		delay.play();
	}

	public GameManager getGameManager() {
		return manager;
	}

	public void setGameManager(GameManager manager) {
		this.manager = manager;
	}

	public void setCenterScene(String name) {
		sceneLoader.setScene(name);
	}

	public void setMenuScene(String name) {
		menuBar.setScene(name);
	}

	public void setFullscreen(Boolean b) {
		primaryStage.setFullScreen(b);
	}

	public void close() {
		Platform.exit();
		System.exit(0);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
