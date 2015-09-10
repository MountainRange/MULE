package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;
import javafx.application.Application;

/**
 * Created by Matthew Keezer on 9/9/2015.
 */
public interface SceneAgent {

	public void setSceneParent(SceneLoader scenes, MULE mule);

}
