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
	private int currentPlayer;
	private int phaseCount;

	public GameManager(WorldMap map) {
		this.map = map;
		playerList = new ArrayList<>();
		for (int i = 0; i < Config.numOfPlayers; i++) {
			playerList.add(Config.playerList[i]);
		}
		turnCount = 0;
		currentPlayer = 0;
		phaseCount = 0;
		nextTurn();
	}

	public void handleKey(KeyEvent e) {
		if (phaseCount == 0) {
			if (Config.gameType == GameType.SIMULTANEOUS) {
				if(e.getCode() == KeyCode.SPACE) {
					if (Config.numOfPlayers > 0) {
						buyTile(playerList.get(0));
					}
				} else if(e.getCode() == KeyCode.P) {
					if (Config.numOfPlayers > 1) {
						buyTile(playerList.get(1));
					}
				} else if(e.getCode() == KeyCode.Q) {
					if (Config.numOfPlayers > 2) {
						buyTile(playerList.get(2));
					}
				} else if(e.getCode() == KeyCode.PERIOD) {
					if (Config.numOfPlayers > 3) {
						buyTile(playerList.get(3));
					}
				}
			} else if (Config.gameType == GameType.HOTSEAT) {
				if(e.getCode() == KeyCode.UP) {
					map.selectUp();
				} else if(e.getCode() == KeyCode.DOWN) {
					map.selectDown();
				} else if(e.getCode() == KeyCode.LEFT) {
					map.selectLeft();
				} else if(e.getCode() == KeyCode.RIGHT) {
					map.selectRight();
				}
				if(e.getCode() == KeyCode.SPACE) {
					if (currentPlayer == 0) {
						buyTile(playerList.get(0));
					} else if (currentPlayer == 1) {
						buyTile(playerList.get(1));
					} else if (currentPlayer == 2) {
						buyTile(playerList.get(2));
					} else if (currentPlayer == 3) {
						buyTile(playerList.get(3));
					}
				}
			}
		} else {
		}
	}

	private void buyTile(Player player) {
		if (phaseCount != 0 || player.getLandOwned() < turnCount) {
			if (map.getOwner() == null) {
				player.addLand();
				map.buyTile(player);
				if (Config.gameType == GameType.HOTSEAT) {
					currentPlayer = (currentPlayer + 1) % Config.numOfPlayers;
				}
			}
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
