package io.github.mountainrange.mule.managers;

import io.github.mountainrange.mule.GameManager;
import io.github.mountainrange.mule.gameplay.WorldMap;

/**
 * A wrapper class that gives KeyFunctions information/access to the state of the game.
 */
public class GameState {
	/*
	 * We don't care too much about getters and setters here, since this is used
	 * only once...
	 */
	public GameManager manager;
	public WorldMap map;

	public GameState(GameManager m, WorldMap map) {
		this.manager = m;
		this.map = map;
	}
}
