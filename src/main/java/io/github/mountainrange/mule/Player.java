package io.github.mountainrange.mule;

import javafx.scene.paint.Color;

/**
 * Created by white on 9/16/2015.
 */
public class Player {

	public static final String[] DEFAULT_NAME = {"Player 1", "Player 2", "Player 3", "Player 4"};
	public static final Config.Race[] DEFAULT_RACE = { Config.Race.BONZOID, Config.Race.FLAPPER,
			Config.Race.HUMAN, Config.Race.BUZZAITE };
	public static final Color[] DEFAULT_COLOR = { Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE };

	private int playerNum;
	private String playerName;
	private Config.Race playerRace;
	private Color playerColor = Color.BLACK;

	public Player(int num, String name, Config.Race race, Color color) {
		playerNum = num;
		playerName = name;
		playerRace = race;
		playerColor = color;
	}

	public int getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Config.Race getPlayerRace() {
		return playerRace;
	}

	public void setPlayerRace(Config.Race playerRace) {
		this.playerRace = playerRace;
	}

	public Color getPlayerColor() {
		return playerColor;
	}

	public void setPlayerColor(Color playerColor) {
		this.playerColor = playerColor;
	}

}
