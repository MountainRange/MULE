package io.github.mountainrange.mule;

import io.github.mountainrange.mule.enums.GameType;
import io.github.mountainrange.mule.enums.MuleType;
import io.github.mountainrange.mule.enums.ResourceType;
import io.github.mountainrange.mule.gameplay.Player;
import io.github.mountainrange.mule.gameplay.Shop;
import io.github.mountainrange.mule.gameplay.Tile;
import io.github.mountainrange.mule.gameplay.WorldMap;
import io.github.mountainrange.mule.managers.GameView;
import io.github.mountainrange.mule.managers.KeyBindManager;
import io.github.mountainrange.mule.managers.GameState;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.*;

/**
 * Holds key information about the state of the game, and drives turns.
 */
public class GameManager {

	private List<Player> playerList;
	private List<Player> buyers;
	private List<Player> turnOrder;

	private Shop shop;
	private WorldMap map;
	private Label turnLabel;
	private Label resourceLabel;
	private MouseHandler mouseHandler;
	private SceneLoader sceneLoader;

	private Timeline runner;
	private Timeline timeCounter;
	private KeyBindManager keyManager;

	private int roundCount;
	private int currentPlayer;
	private int phaseCount;
	private boolean inAuction;
	private int timeLeft;
	private int passCounter;
	private boolean freeLand;
	private boolean gamble;
	private int foodRequired;

	private int[] roundBonus = {0, 50, 50, 50, 100, 100, 100, 100, 150, 150, 150, 150, 200};

	public GameManager(WorldMap map, Label turnLabel, Label resourceLabel, SceneLoader sceneLoader) {
		this.map = map;
		this.turnLabel = turnLabel;
		this.resourceLabel = resourceLabel;
		this.sceneLoader = sceneLoader;
		playerList = new ArrayList<>();
		playerList.addAll(Arrays.asList(Config.getInstance().playerList).subList(0, Config.getInstance().numOfPlayers));
		buyers = new ArrayList<>();
		turnOrder = new ArrayList<>(playerList);
		shop = new Shop(Config.getInstance().difficulty);
		roundCount = 0;
		currentPlayer = 0;
		phaseCount = 0;
		this.mouseHandler = new MouseHandler();
		timeLeft = 0;
		passCounter = 0;
		freeLand = true;
		this.keyManager = new KeyBindManager();
		nextRound();
	}

	public void handleKey(KeyEvent e) {
		this.keyManager.handleKey(new GameView(Config.getInstance().gameType, sceneLoader.getCurrentScene(), phaseCount),
								  e.getCode(), new GameState(this, map));
	}

	/*
	 * Someone tell me what this does
	 */
	public void commentYourCodeGuys() {
		if (!freeLand) {
			passCounter++;
			currentPlayer = (currentPlayer + 1) % Config.getInstance().numOfPlayers;
			setLabels();
			System.out.println(passCounter);
			if (Config.getInstance().numOfPlayers == passCounter) {
				nextRound();
			}
		}

	}

	public List<Player> getPlayerList() {
		return playerList;
	}

	/**
	 * Increments the turn
	 *
	 * Matthew don't copy paste code...
	 */
	public void incrementTurn() {
		passCounter++;
		currentPlayer = (currentPlayer + 1) % Config.getInstance().numOfPlayers;
		setLabels();
		if (Config.getInstance().numOfPlayers == passCounter) {
			nextRound();
		}
		if (currentPlayer == 0) {
			passCounter = 0;
		}
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Buys a tile for the current player.
	 */
	public void buyTile() {
		buyTile(turnOrder.get(currentPlayer));
	}

	public void handleMouse(MouseEvent e) {
		this.mouseHandler.handleEvent(e);
	}

	public void buyTile(Player player) {
		if (!freeLand || map.countLandOwnedBy(player) < roundCount) {
			if (map.getOwner() == null) {
				int cost = (int) (300 + (roundCount * Math.random() * 100));
				if (cost > player.getMoney()) {
					System.out.println("Not enough money");
					return;
				}
				if (phaseCount == 0) {
					if (Config.getInstance().gameType == GameType.HOTSEAT) {
						map.sellTile(player);
						currentPlayer = (currentPlayer + 1) % Config.getInstance().numOfPlayers;
						setLabels();
						if (!freeLand) {
							player.setMoney(player.getMoney() - cost);
							if (currentPlayer == 0) {
								passCounter = 0;
							}
							if (Config.getInstance().numOfPlayers == passCounter) {
								nextRound();
							}
						} else if (freeLand) {
							if (currentPlayer == 0) {
								passCounter = 0;
								nextRound();
							}
						}
					} else if (Config.getInstance().gameType == GameType.SIMULTANEOUS) {
						buyers.add(player);
					}
				} else if (phaseCount == 1) {
					player.setMoney(player.getMoney() - cost);
					map.sellTile(player);
					setLabels();
				}
			}
		}
	}

	private void delayedBuy() {
		/* if (buyers.size() > 1 && !freeLand) {
			enterAuction(buyers);
		} else */
		if (buyers.size() > 0) {
			Player player = buyers.get(0);
			if (!freeLand) {
				int cost = (int)(300 + (roundCount * Math.random() * 100));
				if (cost > player.getMoney()) {
					System.out.println("Not enough money");
					return;
				}
				map.sellTile(player);
				setLabels();
				player.setMoney(player.getMoney() - cost);
			} else  {
				map.sellTile(player);
				setLabels();
			}
		}
		buyers.clear();
	}

	private boolean allBoughtLand() {
		for (Player p : playerList) {
			if (map.countLandOwnedBy(p) < roundCount) {
				return false;
			}
		}
		return true;
	}

	private void setLabels() {
		turnLabel.setText(turnOrder.get(currentPlayer).getName() + "'s Turn " + timeLeft);
		resourceLabel.setText(turnOrder.get(currentPlayer).getName() + "'s Money: "
				+ turnOrder.get(currentPlayer).getMoney() + " Energy: ####");
	}

	/**
	 * Advance the game to the next round, and perform any associated actions.
	 */
	private void nextRound() {
		calculateTurnOrder();
		roundCount++;
		passCounter = 0;
		setLabels();
		if (roundCount == 3) {
			freeLand = false;
		}
		if (roundCount == 4) {
			phaseCount = 1;
		}
		if (phaseCount == 0) {
			landGrabPhase();
		} else if (phaseCount == 1) {
			normalPhase();
		}
		if (roundCount == 3) {
			foodRequired = 3;
		} else if (roundCount == 7) {
			foodRequired++;
		} else if (roundCount == 11) {
			foodRequired++;
		}
		System.out.println("Food required: " + foodRequired);
	}

	/**
	 * End the current player's turn, begin the next player's turn, and perform any other associated actions.
	 */
	public void endTurn() {
		if (!sceneLoader.getCurrentScene().equals(MULE.PLAY_SCENE)) {
			sceneLoader.setScene(MULE.PLAY_SCENE);
		}
		currentPlayer = (currentPlayer + 1) % Config.getInstance().numOfPlayers;
		resetTimer();
		setLabels();
		if (currentPlayer == 0) {
			nextRound();
			if (timeCounter != null) timeCounter.stop();
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

	private void normalPhase() {
		map.select(4, 2);
		setLabels();
		turnTimer();
	}

	private void enterAuction(List<Player> buyers) {
		setInAuction(true);
		Config.getInstance().buyers = new ArrayList<>(buyers);
		sceneLoader.setScene(MULE.AUCTION_SCENE);
	}

	public void setInAuction(boolean inAuction) {
		this.inAuction = inAuction;
	}

	private void runSelector() {
		runner = new Timeline(
				new KeyFrame(
						Duration.seconds(Config.SELECTOR_SPEED),
						new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								if (!inAuction && phaseCount == 0) {
									delayedBuy();
									map.selectRightWrap();
								}
								if (allBoughtLand() && freeLand) {
									runner.stop();
									nextRound();
								}
								if (Config.getInstance().numOfPlayers == passCounter) {
									runner.stop();
									nextRound();
								}
							}
						}
				)
		);
		runner.setCycleCount(Timeline.INDEFINITE);
		runner.play();
	}

	private void resetTimer() {
		if (playerList.get(currentPlayer).stockOf(ResourceType.FOOD) < foodRequired) {
			timeLeft = 30;
		} else if (playerList.get(currentPlayer).stockOf(ResourceType.FOOD) <= 0) {
			timeLeft = 5;
		} else {
			timeLeft = 50;
		}
	}

	private void turnTimer() {
		resetTimer();
		timeCounter = new Timeline(
				new KeyFrame(
						Duration.seconds(Config.SELECTOR_SPEED),
						new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								timeLeft--;
								setLabels();
								if (gamble) {
									gamble = false;
									turnOrder.get(currentPlayer).addMoney(Math.max(0, Math.min(250,
											(int) (roundBonus[roundCount] * (Math.random() * timeLeft)))));
									endTurn();
								}
								if (timeLeft <= 0) {
									endTurn();
								}
							}
						}
				)
		);
		timeCounter.setCycleCount(Timeline.INDEFINITE);
		timeCounter.play();
	}

	public void gamble() {
		gamble = true;
	}

	/**
	 * Compute the score of the given player. The score of a player is computed as follows:
	 * <code>money + (plot * 500 + price of outfitting) + mules in store * 35 + (resource * price of resource)</code>.
	 * @return map of players to their scores
	 */
	public Map<Player, Integer> scoreGame() {
		Map<Player, Integer> scores = new HashMap<>();

		// Add score from total number of mules in store
		int muleScore = shop.stockOf(ResourceType.MULE) * 35;
		for (Player player : playerList) {
			// Add score from money and mules
			int score = player.getMoney() + muleScore;

			// Add score from player resources
			for (ResourceType resource : ResourceType.values()) {
				score += player.stockOf(resource) * shop.priceOf(resource);
			}

			// Add score from tiles owned and MULEs installed
			score += map.countLandOwnedBy(player) * 500;
			for (Tile tile : map.landOwnedBy(player)) {
				if (tile.getMule() != MuleType.EMPTY) {
					score += Shop.outfitPriceOf(tile.getMule());
				}
			}

			scores.put(player, score);
		}

		return scores;
	}

	/**
	 * Sort players in ascending order by score (so players with the lowest score go first).
	 *
	 * <code>turnOrder</code> holds the sorted version of <code>playerList</code>.
	 */
	public void calculateTurnOrder() {
		Map<Player, Integer> scores = scoreGame();
		turnOrder.sort((p1, p2) -> scores.get(p2) - scores.get(p1));
	}

	/**
	 * A small class for handling mouse events
	 */
	private class MouseHandler {
		public void handleEvent(MouseEvent e) {
			if (e.getEventType().getName().equals("MOUSE_PRESSED")) {
				if (map.isInside(new Point2D(e.getX(), e.getY()), (map.getColumns() / 2), (map.getRows() / 2))) {
					if (phaseCount == 1) {
						sceneLoader.setScene(MULE.TOWN_SCENE);
					}
				}
			}
		}
	}
}
