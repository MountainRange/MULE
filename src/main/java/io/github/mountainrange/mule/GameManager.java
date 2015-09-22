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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Holds key information about the state of the game, and drives turns.
 */
public class GameManager {

	private List<Player> playerList;

	private Shop shop;
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

}
