package io.github.mountainrange.mule;

import io.github.jgkamat.JayLayer.JayLayer;
import io.github.mountainrange.mule.enums.*;
import io.github.mountainrange.mule.gameplay.Player;

import java.util.List;

/**
 * Simple singleton config class.
 */
public class Config {

	private static Config instance = null;

	public static final String DEFAULT_PACK = "fancy";
	public static final int SELECTOR_SPEED = 1;
	public static final int MESSAGE_DURATION = 3;

	/** Current difficulty for the game. */
	public Difficulty difficulty;
	/** Current type of the game. */
	public GameType gameType;
	/** Current size of the map. */
	public MapSize mapSize;
	/** Current type of the map. */
	public MapType mapType;
	/** List of all players (including extra players who aren't playing). */
	public Player[] playerList;
	public int currentPlayer;
	/** Number of players that are actually participating. */
	public int numOfPlayers;
	public int maxPlayers;
	public List<Player> buyers;
	/** Whether fade transitions between screens are enabled. */
	public boolean fadeEnabled;
	public boolean selectEnabled;

	/** Sound manager for the game. */
	public JayLayer soundManager;
	public int gamePlaylist;
	public int titlePlaylist;

	private Config() {
		difficulty = Difficulty.MESA;
		gameType = GameType.HOTSEAT;
		mapSize = MapSize.ALPS;
		mapType = MapType.CLASSIC;
		currentPlayer = 0;
		numOfPlayers = 2;
		maxPlayers = 4;
		fadeEnabled = false;
		selectEnabled = false;

		soundManager = new JayLayer("/audio/", "/audio/");
		titlePlaylist = soundManager.createPlaylist(false);
		soundManager.addToPlaylist(titlePlaylist, "Patterns of Thought.mp3");
		gamePlaylist = soundManager.createPlaylist(false);
		soundManager.addToPlaylist(gamePlaylist, "Means of Production.mp3");
	}

	/**
	 * Get the global instance of Config.
	 * @return global instance of Config
	 */
	public static Config getInstance() {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}
}
