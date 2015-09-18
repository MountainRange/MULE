package io.github.mountainrange.mule;

import javafx.scene.paint.Color;

import java.util.ArrayList;

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
		FOLD, FAULT_BLOCK, DOME, VOLCANIC, PLATEAU
	}

	public static Difficulty difficulty = Difficulty.MESA;
	public static MapSize mapSize = MapSize.ALPS;
	public static MapType mapType = MapType.CLASSIC;
	public static Player[] playerList;
	public static int currentPlayer = 0;
	public static int numOfPlayers = 2;
	public static int maxPlayers = 4;
	public static Race race = Race.FOLD;

	public static boolean fadeEnabled = false;
}
