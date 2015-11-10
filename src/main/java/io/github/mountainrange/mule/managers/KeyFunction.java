package io.github.mountainrange.mule.managers;

/**
 * A interface that represents a lambda that handles key presses.
 */
@FunctionalInterface
public interface KeyFunction {
	String act(GameState m);
}
