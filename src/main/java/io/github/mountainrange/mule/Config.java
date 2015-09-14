package io.github.mountainrange.mule;

/**
 * Created by Matthew Keezer on 9/10/2015.
 */
public class Config {

	public static enum Difficulty {
		HILL, MESA, PLATEAU, MOUNTAIN
	}
	public static enum MapSize {
		PYRENEES, ALPS, HIMALAYAS
	}
	public static enum MapType {
		CLASSIC, RANDOM, EXPERIMENTAL
	}
	public static enum Race {
		HUMAN, FLAPPER, BONZOID, UGAITE, BUZZAITE
	}

	public static Difficulty difficulty = Difficulty.MESA;
	public static MapSize mapSize = MapSize.ALPS;
	public static MapType mapType = MapType.CLASSIC;
	public static String playerName = "Player 1";
	public static int numOfPlayers = 2;
	public static Race race = Race.BONZOID;

	public static boolean fadeEnabled = false;


}
