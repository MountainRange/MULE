package io.github.mountainrange.mule;

import io.github.jgkamat.JayLayer.JayLayer;
import io.github.mountainrange.mule.enums.*;
import io.github.mountainrange.mule.gameplay.Player;

import java.util.List;

/**
 * Simple config class
 */
public class Config {

	private static Config instance = null;

	public static final String DEFAULT_PACK = "fancy";
	public static final int SELECTOR_SPEED = 1;
	public static final int MESSAGE_DURATION = 3;

	public Difficulty difficulty;
	public GameType gameType;
	public MapSize mapSize;
	public MapType mapType;
	public Player[] playerList;
	public int currentPlayer;
	public int numOfPlayers;
	public int maxPlayers;
	public List<Player> buyers;
	public Race race;
	public boolean fadeEnabled;
	public boolean selectEnabled;

	// Sound stuff
	public int gamePlaylist;
	public int titlePlaylist;
	public JayLayer soundManager;

	protected Config() {
		difficulty = Difficulty.MESA;
		gameType = GameType.HOTSEAT;
		mapSize = MapSize.ALPS;
		mapType = MapType.CLASSIC;
		currentPlayer = 0;
		numOfPlayers = 2;
		maxPlayers = 4;
		race = Race.FOLD;
		fadeEnabled = false;
		selectEnabled = false;

		soundManager = new JayLayer("/audio/", "/audio/");
		titlePlaylist = soundManager.createPlaylist(false);
		soundManager.addToPlaylist(titlePlaylist, "Patterns of Thought.mp3");
		gamePlaylist = soundManager.createPlaylist(false);
		soundManager.addToPlaylist(gamePlaylist, "Means of Production.mp3");
	}

	public static Config getInstance() {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}
}
