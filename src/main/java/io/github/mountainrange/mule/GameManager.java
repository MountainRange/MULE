package io.github.mountainrange.mule;

import io.github.mountainrange.mule.enums.GameType;
import io.github.mountainrange.mule.enums.ResourceType;
import io.github.mountainrange.mule.gameplay.Player;
import io.github.mountainrange.mule.gameplay.Shop;
import io.github.mountainrange.mule.gameplay.Tile;
import io.github.mountainrange.mule.gameplay.WorldMap;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.*;

/**
 * Holds key information about the state of the game, and drives turns.
 */
public class GameManager {

	private List<Player> playerList;

	private Shop shop;
	private WorldMap map;
	private Label turnLabel;
	private Label resourceLabel;
	private MouseHandler mouseHandler;
	private SceneLoader sceneLoader;

	private int turnCount;
	private int currentPlayer;
	private int phaseCount;

	public GameManager(WorldMap map, Label turnLabel, Label resourceLabel, SceneLoader sceneLoader) {
		this.map = map;
		this.turnLabel = turnLabel;
		this.resourceLabel = resourceLabel;
		this.sceneLoader = sceneLoader;
		playerList = new ArrayList<>();
		playerList.addAll(Arrays.asList(Config.getInstance().playerList).subList(0, Config.getInstance().numOfPlayers));
		turnCount = 0;
		currentPlayer = 0;
		phaseCount = 0;
		this.mouseHandler = new MouseHandler();
		nextTurn();
	}

	//TODO FIXME MOVE THIS TO ITS OWN CLASS
	public void handleKey(KeyEvent e) {
		if (phaseCount == 0) {
			if (Config.getInstance().gameType == GameType.SIMULTANEOUS) {
				if (e.getCode() == KeyCode.SPACE) {
					if (Config.getInstance().numOfPlayers > 0) {
						buyTile(playerList.get(0));
					}
				} else if (e.getCode() == KeyCode.P) {
					if (Config.getInstance().numOfPlayers > 1) {
						buyTile(playerList.get(1));
					}
				} else if (e.getCode() == KeyCode.Q) {
					if (Config.getInstance().numOfPlayers > 2) {
						buyTile(playerList.get(2));
					}
				} else if (e.getCode() == KeyCode.PERIOD) {
					if (Config.getInstance().numOfPlayers > 3) {
						buyTile(playerList.get(3));
					}
				}
			} else if (Config.getInstance().gameType == GameType.HOTSEAT) {
				if (!Config.getInstance().selectEnabled) {
					if (e.getCode() == KeyCode.UP) {
						map.selectUp();
					} else if (e.getCode() == KeyCode.DOWN) {
						map.selectDown();
					} else if (e.getCode() == KeyCode.LEFT) {
						map.selectLeft();
					} else if (e.getCode() == KeyCode.RIGHT) {
						map.selectRight();
					}
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
		}
	}

	public void handleMouse(MouseEvent e) {
		this.mouseHandler.handleEvent(e);
	}

	private void buyTile(Player player) {
		if (phaseCount != 0 || player.getLandOwned() < turnCount) {
			if (map.getOwner() == null) {
				player.addLand();
				player.setMoney((int)(player.getMoney() - (300 + (turnCount * Math.random()*100))));
				map.buyTile(player);
				if (Config.getInstance().gameType == GameType.HOTSEAT) {
					currentPlayer = (currentPlayer + 1) % Config.getInstance().numOfPlayers;
					setLabels();
					if (currentPlayer == 0) {
						nextTurn();
					}
				} else if (Config.getInstance().gameType == GameType.SIMULTANEOUS) {
					setLabels();
				}
			}
		}
	}

	private void setLabels() {
		if (Config.getInstance().gameType == GameType.HOTSEAT) {
			turnLabel.setText(playerList.get(currentPlayer).getName() + "'s Turn");
		}
		resourceLabel.setText(playerList.get(currentPlayer).getName() + "'s Money: "
				+ playerList.get(currentPlayer).getMoney() + " Energy: ####");
	}

	/**
	 * Advance the game to the next turn, and perform any associated actions.
	 */
	private void nextTurn() {
		turnCount++;
		setLabels();
		if (turnCount == 3) {
			phaseCount = 1;
		}
		if (phaseCount == 0) {
			landGrabPhase();
		}
	}

	private void landGrabPhase() {
		if (Config.getInstance().gameType == GameType.HOTSEAT) {
			map.select(0, 0);
			if (Config.getInstance().selectEnabled) {
				runSelector();
			}
		} else if (Config.getInstance().gameType == GameType.SIMULTANEOUS) {
			turnLabel.setText("Landgrab Phase");
			map.select(0, 0);
			runSelector();
		}
	}

	private void runSelector() {
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

	/**
	 * Compute the score of the given player. The score of a player is computed as follows:
	 * <code>money + (plot * 500 + price of outfitting) + mules in store * 35 + (resource * price of resource)</code>.
	 * @return map of players to their scores
	 */
	public Map<Player, Integer> scoreGame() {
		Map<Player, Integer> scores = new HashMap<>();

		int muleScore = shop.stockOf(ResourceType.MULE) * 35;
		for (Player player : playerList) {
			// Add score from money and mules
			int score = player.getMoney() + muleScore;

			// Add score from player resources
			for (ResourceType resource : ResourceType.values()) {
				score += player.stockOf(resource) * shop.priceOf(resource);
			}

			scores.put(player, score);
		}

		// Add score from tiles owned and MULEs installed
		for (Tile tile : map) {
			Player owner = tile.getOwner();
			if (owner != null) {
				scores.put(owner, scores.get(owner) + Shop.outfitPriceOf(tile.getMule()));
			}
		}

		return scores;
	}

	/**
	 * A small class for handling mouse events
	 */
	private class MouseHandler {
		public void handleEvent(MouseEvent e) {
			if (e.getEventType().getName() == "MOUSE_PRESSED") {
				if (map.isInside(new Point2D(e.getX(), e.getY()), (map.getColumns() / 2), (map.getRows() / 2))) {
					sceneLoader.setScene(MULE.TOWN_SCENE);
				}
			}
		}
	}
}
