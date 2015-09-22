package io.github.mountainrange.mule;

import io.github.mountainrange.mule.enums.GameType;
import io.github.mountainrange.mule.gameplay.Player;
import io.github.mountainrange.mule.gameplay.WorldMap;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds key information about the state of the game, and drives turns.
 */
public class GameManager {

	private List<Player> playerList;

	private WorldMap map;

	private int turnCount;
	private int phaseCount;

	public GameManager(WorldMap map) {
		this.map = map;
		playerList = new ArrayList<>();
		for (int i = 0; i < Config.numOfPlayers; i++) {
			playerList.add(Config.playerList[i]);
		}
		turnCount = 0;
		phaseCount = 0;
		nextTurn();
	}

	public void handleKey(KeyEvent e) {
		if(e.getCode() == KeyCode.UP) {
			map.selectUp();
		} else if(e.getCode() == KeyCode.DOWN) {
			map.selectDown();
		} else if(e.getCode() == KeyCode.LEFT) {
			map.selectLeft();
		} else if(e.getCode() == KeyCode.RIGHT) {
			map.selectRight();
		}
	}

	private void nextTurn() {
		turnCount++;
		if (turnCount == 3) {
			phaseCount = 1;
		}
		if (phaseCount == 0) {
			landGrabPhase();
		}
	}

	private void landGrabPhase() {
		if (Config.gameType == GameType.HOTSEAT) {
			map.select(4, 2);
		} else if (Config.gameType == GameType.SIMULTANEOUS) {
			map.select(0, 0);
			Timeline timeline = new Timeline(
					new KeyFrame(
						Duration.seconds(Config.SELECTOR_SPEED),
						new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								map.selectRightWrap();
							}
						}
					)
			);
			timeline.setCycleCount(Timeline.INDEFINITE);
			timeline.play();

		}
	}

}
