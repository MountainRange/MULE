package io.github.mountainrange.mule.managers;

import io.github.mountainrange.mule.GameManager;
import io.github.mountainrange.mule.gameplay.WorldMap;

/**
 * A class for lambdas to the KeyBindManager to have
 */
public class KeyBindPackage {
    /*
     * We don't care too much about getters and setters here, since this is used
     * only once...
     */
    public GameManager manager;
	public WorldMap map;

    public KeyBindPackage(GameManager m, WorldMap map) {
        this.manager = m;
		this.map = map;
    }
}
