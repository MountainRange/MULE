package io.github.mountainrange.mule.managers;

/**
 * A interface to pass a lambda into this object
 */
@FunctionalInterface
public interface KeyFunction {
    String act(KeyBindPackage m);
}
