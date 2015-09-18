package io.github.mountainrange.mule;

/**
 * Created by Matthew Keezer on 9/10/2015.
 */
public class Config {

	public static enum MapSize {
		PYRENEES, ALPS, HIMALAYAS
	}
	public static enum MapType {
		CLASSIC, RANDOM, EXPERIMENTAL
	}
	public static enum Race {
		FOLD, FAULT_BLOCK, DOME, VOLCANIC, PLATEAU
	}

	public static DifficultyType difficulty = DifficultyType.MESA;
	public static MapSize mapSize = MapSize.ALPS;
	public static MapType mapType = MapType.CLASSIC;
	public static String playerName = "Player 1";
	public static int numOfPlayers = 2;
	public static Race race = Race.FOLD;

	public static boolean fadeEnabled = false;


}
