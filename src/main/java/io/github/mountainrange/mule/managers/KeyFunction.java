package io.github.mountainrange.mule.managers;

/**
 * A interface that represents a lambda that handles keypresses.
 */
@FunctionalInterface
public interface KeyFunction {
	String act(KeyBindPackage m);
}
