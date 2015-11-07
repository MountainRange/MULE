package io.github.mountainrange.mule.managers;

// Junit Imports
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.rules.Timeout;

import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.enums.GameType;
import io.github.mountainrange.mule.managers.KeyBindManager;

import java.util.*;
import javafx.scene.input.KeyCode;


/**
 * A Class to Test the KeyManager
 */
public class KeyManagerTest {

	@Rule
	public Timeout timeout = Timeout.seconds(10);

	private KeyBindManager keyManager;

	@Before
	public void setup() {
		// Run for every test.
		this.keyManager = new KeyBindManager(false);
	}

	@After
	public void teardown() {
		// Run for every test.
		this.keyManager.clear();
	}

	/**
	 * Tests if the gameView add method is working
	 */
	@Test
	public void testAddGameView() {
		KeyFunction lambda = (a) -> "Test";
		keyManager.add(new GameView(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0), KeyCode.X, lambda);
		KeyFunction lambda2 = keyManager.getBinding(new GameView(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0), KeyCode.X);
		assertSame(lambda, lambda2);
	}

	/**
	 * Tests if the raw add method is working
	 */
	@Test
	public void testAddRaw() {
		KeyFunction lambda = (a) -> "Test";
		keyManager.add(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0, KeyCode.X, lambda);
		KeyFunction lambda2 = keyManager.getBinding(new GameView(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0), KeyCode.X);
		assertSame(lambda, lambda2);
	}

	/**
	 * Tests if the iterable GameType method is working
	 */
	@Test
	public void testAddIterableGameType() {
		KeyFunction lambda = (a) -> "Test";
		keyManager.add(Arrays.asList(GameType.SIMULTANEOUS, GameType.HOTSEAT), MULE.PLAY_SCENE, 0, KeyCode.X, lambda);
		KeyFunction lambda2 = keyManager.getBinding(new GameView(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0), KeyCode.X);
		KeyFunction lambda3 = keyManager.getBinding(new GameView(GameType.HOTSEAT, MULE.PLAY_SCENE, 0), KeyCode.X);
		assertSame(lambda, lambda2);
		assertSame(lambda, lambda3);
	}

	/**
	 * Tests if the iterable GameType method is working
	 */
	@Test
	public void testAddIterablePhaseCount() {
		KeyFunction lambda = (a) -> "Test";
		keyManager.add(GameType.HOTSEAT, MULE.PLAY_SCENE, Arrays.asList(0,1,2), KeyCode.X, lambda);
		KeyFunction lambda2 = keyManager.getBinding(new GameView(GameType.HOTSEAT, MULE.PLAY_SCENE, 0), KeyCode.X);
		KeyFunction lambda3 = keyManager.getBinding(new GameView(GameType.HOTSEAT, MULE.PLAY_SCENE, 1), KeyCode.X);
		KeyFunction lambda4 = keyManager.getBinding(new GameView(GameType.HOTSEAT, MULE.PLAY_SCENE, 2), KeyCode.X);
		assertSame(lambda, lambda2);
		assertSame(lambda, lambda3);
		assertSame(lambda, lambda4);
	}

	/**
	 * Tests if the deluxe iterable add is working
	 */
	@Test
	public void testAddDeluxe() {
		KeyFunction lambda = (a) -> "Test";
		keyManager.add(Arrays.asList(GameType.HOTSEAT, GameType.SIMULTANEOUS), MULE.PLAY_SCENE, Arrays.asList(0,1,2), KeyCode.X, lambda);
		KeyFunction lambda2 = keyManager.getBinding(new GameView(GameType.HOTSEAT, MULE.PLAY_SCENE, 0), KeyCode.X);
		KeyFunction lambda3 = keyManager.getBinding(new GameView(GameType.HOTSEAT, MULE.PLAY_SCENE, 1), KeyCode.X);
		KeyFunction lambda4 = keyManager.getBinding(new GameView(GameType.HOTSEAT, MULE.PLAY_SCENE, 2), KeyCode.X);
		KeyFunction lambda5 = keyManager.getBinding(new GameView(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0), KeyCode.X);
		KeyFunction lambda6 = keyManager.getBinding(new GameView(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 1), KeyCode.X);
		KeyFunction lambda7 = keyManager.getBinding(new GameView(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 2), KeyCode.X);

		KeyFunction null1 = keyManager.getBinding(new GameView(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 2), KeyCode.Y);
		KeyFunction null2 = keyManager.getBinding(new GameView(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 4), KeyCode.X);
		KeyFunction null3 = keyManager.getBinding(new GameView(GameType.SIMULTANEOUS, MULE.CREDITS_SCENE, 2), KeyCode.X);

		assertSame(lambda, lambda2);
		assertSame(lambda, lambda3);
		assertSame(lambda, lambda4);
		assertSame(lambda, lambda5);
		assertSame(lambda, lambda6);
		assertSame(lambda, lambda7);

		assertNull(null1);
		assertNull(null2);
		assertNull(null3);
	}
}
