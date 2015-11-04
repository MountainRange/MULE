package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;

/**
 * Created by Matthew Keezer on 9/9/2015.
 */
public interface SceneAgent {
	void setSceneParent(SceneLoader scenes, MULE mule);
	void onSetScene();
}
