package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;

/**
 * Interface for controllers to work with {@code SceneLoader}.
 */
public interface SceneAgent {
	void setSceneParent(SceneLoader scenes, MULE mule);
	void onSetScene();
}
