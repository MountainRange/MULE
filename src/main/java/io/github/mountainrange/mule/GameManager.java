package io.github.mountainrange.mule;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds key information about the state of the game, and drives turns.
 */
public class GameManager {

	private List<Player> playerList;

	private int turnCount;
	private int phaseCount;

	public GameManager(int numPlayers) {
		playerList = new ArrayList<>(numPlayers);

		for (int i = 0; i <= numPlayers; i++) {
			playerList.add(new Player(i));
		}
	}

}
