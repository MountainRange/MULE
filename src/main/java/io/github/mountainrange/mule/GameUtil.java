package io.github.mountainrange.mule;

import io.github.mountainrange.mule.gameplay.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unrelated static utility methods.
 */
public class GameUtil {

	/**
	 * Compute the score of the given player. The score of a player is computed as follows:
	 * <code>money + (plot * 500 + price of outfitting) + mules in store * 35 + (resource * price of resource) </code>.
	 * @param playerList list of players to compute the score of
	 * @return map of players to their scores
	 */
	public static Map<Player, Integer> scoreAll(List<Player> playerList) {
		if (playerList == null) {
			throw new IllegalArgumentException("Can't score all players: player list null");
		}

		Map<Player, Integer> scores = new HashMap<>();

		for (Player player : playerList) {
			// Add all the money
			scores.put(player, player.getMoney());
		}

		// TODO: also count the score from resources and plots the player owns

		return scores;
	}

	/**
	 * Get the score for only the given player.
	 * @param player player to compute the score of
	 * @return the given player's score
	 */
	public static int playerScore(Player player) {
		int score = player.getMoney();

		return score;
	}

}
