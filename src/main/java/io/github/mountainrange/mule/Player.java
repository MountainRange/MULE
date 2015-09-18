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

	private final int id;
	private String name;
	private Config.Race race;
	private Color color = Color.BLACK;

	public Player(int id, String name, Config.Race race, Color color) {
		this.id = id;
		this.name = name;
		this.race = race;
		this.color = color;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Config.Race getRace() {
		return race;
	}

	public void setRace(Config.Race race) {
		this.race = race;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
