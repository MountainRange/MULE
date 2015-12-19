package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;

/**
 * Interface for controllers to work with {@code SceneLoader}.
 * Every scene in the program has a subclass of sceneagent,
 * See the controller package for more details.
 */
public interface SceneAgent {

	/**
	 * Sets the scene parent for this class, allows this class to talk with classes that are
	 * higher in the heiarchy than it.
	 *
	 * @param scenes The sceneloader to set for this SceneAgent. Used when this scene requests a scene transision.
	 * @param mule The MULE object to set for this SceneAgent. Used when this class wants to talk to other high level classes occasionally
	 */
	void setSceneParent(SceneLoader scenes, MULE mule);

	/**
	 * A method that will be called on this scene whenever the current view switches to it.
	 *
	 * This is uesful for scenes that start a process when switched to.
	 */
	void onSetScene();
}
