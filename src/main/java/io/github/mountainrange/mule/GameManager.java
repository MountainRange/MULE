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
	private List<Player> buyers;

	private Shop shop;
	private WorldMap map;
	private Label turnLabel;
	private Label resourceLabel;
	private MouseHandler mouseHandler;
	private SceneLoader sceneLoader;

	private Timeline runner;
	private Timeline timeCounter;

	private int roundCount;
	private int currentPlayer;
	private int phaseCount;
	private boolean inAuction;
	private int timeLeft;
	private int passCounter;
	private boolean freeLand;
	private boolean gamble;

	private int[] roundBonus = {0, 50, 50, 50, 100, 100, 100, 100, 150, 150, 150, 150, 200};

	public GameManager(WorldMap map, Label turnLabel, Label resourceLabel, SceneLoader sceneLoader) {
		this.map = map;
		this.turnLabel = turnLabel;
		this.resourceLabel = resourceLabel;
		this.sceneLoader = sceneLoader;
		playerList = new ArrayList<>();
		playerList.addAll(Arrays.asList(Config.getInstance().playerList).subList(0, Config.getInstance().numOfPlayers));
		buyers = new ArrayList<>();
		roundCount = 0;
		currentPlayer = 0;
		phaseCount = 0;
		this.mouseHandler = new MouseHandler();
		timeLeft = 0;
		passCounter = 0;
		freeLand = true;
		nextRound();
	}

	//TODO FIXME MOVE THIS TO ITS OWN CLASS
	public void handleKey(KeyEvent e) {
		if (phaseCount == 0) {
			if (Config.getInstance().gameType == GameType.SIMULTANEOUS) {
				if (e.getCode() == KeyCode.X) {
					if (currentPlayer == 0) {

						passCounter++;
						currentPlayer = (currentPlayer + 1) % Config.getInstance().numOfPlayers;
						setLabels();
						if (Config.getInstance().numOfPlayers == passCounter) {
							nextRound();
						}
					}
				} else if (e.getCode() == KeyCode.O) {
					if (currentPlayer == 1) {
						passCounter++;
						currentPlayer = (currentPlayer + 1) % Config.getInstance().numOfPlayers;
						setLabels();
						if (Config.getInstance().numOfPlayers == passCounter) {
							nextRound();
						}
					}
				} else if (e.getCode() == KeyCode.W) {
					if (currentPlayer == 2) {
						passCounter++;
						currentPlayer = (currentPlayer + 1) % Config.getInstance().numOfPlayers;
						setLabels();
						if (Config.getInstance().numOfPlayers == passCounter) {
							nextRound();
						}
					}
				} else if (e.getCode() == KeyCode.COMMA) {
					if (currentPlayer == 3) {
						passCounter++;
						currentPlayer = (currentPlayer + 1) % Config.getInstance().numOfPlayers;
						setLabels();
						if (Config.getInstance().numOfPlayers == passCounter) {
							nextRound();
						}
					}
				}
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
				if (e.getCode() == KeyCode.X) {
					if (!freeLand) {
						passCounter++;
						currentPlayer = (currentPlayer + 1) % Config.getInstance().numOfPlayers;
						setLabels();
						if (Config.getInstance().numOfPlayers == passCounter) {
							nextRound();
						}
					}
				}
				handleMovement(e);
			}
		} else if (phaseCount == 1) {
			handleMovement(e);
		}
	}

	private void handleMovement(KeyEvent e) {
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

	public void handleMouse(MouseEvent e) {
		this.mouseHandler.handleEvent(e);
	}

	private void buyTile(Player player) {
		if (freeLand == false || player.getLandOwned() < roundCount) {
			if (map.getOwner() == null) {
				if (phaseCount == 0) {
					if (Config.getInstance().gameType == GameType.HOTSEAT) {
						player.addLand();
						map.buyTile(player);
						currentPlayer = (currentPlayer + 1) % Config.getInstance().numOfPlayers;
						setLabels();
						if (!freeLand) {
							player.setMoney((int) (player.getMoney() - (300 + (roundCount * Math.random() * 100))));
							if (Config.getInstance().numOfPlayers == passCounter) {
								nextRound();
							}
						} else if (freeLand) {
							if (currentPlayer == 0) {
								nextRound();
							}
						}
					} else if (Config.getInstance().gameType == GameType.SIMULTANEOUS) {
						buyers.add(player);
					}
				} else if (phaseCount == 1) {
					player.addLand();
					player.setMoney((int)(player.getMoney() - (300 + (roundCount * Math.random()*100))));
					map.buyTile(player);
					setLabels();
				}
			}
		}
	}

	private void delayedBuy() {
		if (buyers.size() > 1 && !freeLand) {
			enterAuction(buyers);
		} else if (buyers.size() == 1) {
			Player player = buyers.get(0);
			player.addLand();
			player.setMoney((int) (player.getMoney() - (300 + (roundCount * Math.random() * 100))));
			map.buyTile(player);
			setLabels();
		}
		buyers.clear();
	}

	private boolean allBoughtLand() {
		for (int i = 0; i < playerList.size(); i++) {
			if (playerList.get(i).getLandOwned() < roundCount) {
				return false;
			}
		}
		return true;
	}

	private void setLabels() {
		turnLabel.setText(playerList.get(currentPlayer).getName() + "'s Turn " + timeLeft);
		resourceLabel.setText(playerList.get(currentPlayer).getName() + "'s Money: "
				+ playerList.get(currentPlayer).getMoney() + " Energy: ####");
	}

	/**
	 * Advance the game to the next round, and perform any associated actions.
	 */
	private void nextRound() {
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
	}

	/**
	 * End the current player's turn, begin the next player's turn, and perform any other associated actions.
	 */
	public void endTurn() {
		if (!sceneLoader.getCurrentScene().equals(MULE.PLAY_SCENE)) {
			sceneLoader.setScene(MULE.PLAY_SCENE);
		}
		timeLeft = 50;
		currentPlayer = (currentPlayer + 1) % Config.getInstance().numOfPlayers;
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

	private void turnTimer() {
		timeLeft = 50;
		timeCounter = new Timeline(
				new KeyFrame(
						Duration.seconds(Config.SELECTOR_SPEED),
						new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								timeLeft--;
								setLabels();
								if (gamble == true) {
									gamble = false;
									playerList.get(currentPlayer).addMoney(Math.max(0, Math.min(250,
											(int)(roundBonus[roundCount] * (Math.random() * timeLeft)))));
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
			if (e.getEventType().getName().equals("MOUSE_PRESSED")) {
				if (map.isInside(new Point2D(e.getX(), e.getY()), (map.getColumns() / 2), (map.getRows() / 2))) {
					sceneLoader.setScene(MULE.TOWN_SCENE);
				}
			}
		}
	}
}
