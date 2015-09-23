package io.github.mountainrange.mule;

import io.github.mountainrange.mule.enums.*;
import io.github.mountainrange.mule.gameplay.Player;

/**
 * Simple config class
 */
public class Config {

	public static final String DEFAULT_PACK = "plain";
	public static final int SELECTOR_SPEED = 1;

	public static Difficulty difficulty = Difficulty.MESA;
	public static GameType gameType = GameType.HOTSEAT;
	public static MapSize mapSize = MapSize.ALPS;
	public static MapType mapType = MapType.CLASSIC;
	public static Player[] playerList;
	public static int currentPlayer = 0;
	public static int numOfPlayers = 2;
	public static int maxPlayers = 4;
	public static Race race = Race.FOLD;

	public static boolean fadeEnabled = false;
	public static boolean selectEnabled = false;
}
