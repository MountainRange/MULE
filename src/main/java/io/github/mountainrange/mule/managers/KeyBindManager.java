package io.github.mountainrange.mule.managers;

import io.github.mountainrange.mule.Config;
import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.enums.GameType;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

public class KeyBindManager {
	private Map<GameView, Map<KeyCode, KeyFunction>> keyMap;

	public KeyBindManager() {
		keyMap = new HashMap<>();
		KeyBindManager.addDefaultBindings(this);
	}

	/**
	 * An add method that takes in a GameView
	 */
	public void add(GameView state, KeyCode toAdd, KeyFunction lambda) {
		if (!keyMap.containsKey(state)) {
			keyMap.put(state, new HashMap<KeyCode, KeyFunction>());
		}
		keyMap.get(state).put(toAdd, lambda);
	}

	/**
	 * An add method that takes in GameView Parts.
	 * This one in particular takes in a Collection of phaseCounts
	 */
	public void add(GameType gt, String sn, Iterable<Integer> phaseCount, KeyCode toAdd, KeyFunction lambda) {
		phaseCount.forEach((input) -> {
				this.add(gt, sn, input, toAdd, lambda);
			});
	}

	/**
	 * An add method that takes in GameView Parts.
	 * This particular one takes in a Collection of GameTypes, all set to this keyCode/function
	 */
	public void add(Iterable<GameType> gt, String sn, int phaseCount, KeyCode toAdd, KeyFunction lambda) {
		gt.forEach((input) -> {
				this.add(input, sn, phaseCount, toAdd, lambda);
			});
	}

	/**
	 * An add method that takes in GameView Parts.
	 * This particular one takes in a Collection of GameTypes and phaseCounts.
	 */
	public void add(Iterable<GameType> gt, String sn, Iterable<Integer> phaseCount, KeyCode toAdd, KeyFunction lambda) {
		gt.forEach((input) -> {
				this.add(input, sn, phaseCount, toAdd, lambda);
			});
	}

	/**
	 * An add method that takes in GameView Parts.
	 */
	public void add(GameType gt, String sn, int phaseCount, KeyCode toAdd, KeyFunction lambda) {
		GameView state = new GameView(gt, sn, phaseCount);
		this.add(state, toAdd, lambda);
	}

	public void handleKey(GameView state, KeyCode key, GameState datapacket) {
		Map<KeyCode, KeyFunction> first = keyMap.get(state);
		if (first == null) {
			// No Game state found.
			return;
		}
		KeyFunction second = first.get(key);
		if (second == null) {
			// No Key In map
			return;
		}
		second.act(datapacket);
	}

	/**
	 * A method to initialize the defaults.
	 */
	public static void addDefaultBindings(KeyBindManager toBind) {

		// ----------------------------TURN INCREMENTERS-------------------------------
		// Player 1 Next Turn
		toBind.add(new GameView(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0), KeyCode.X,
				(a) -> {
					if (a.manager.getCurrentPlayer() == 0) {
						a.manager.incrementTurn();
					}
					return "Turn Incremented"; });
		// Player 2 Next Turn
		toBind.add(new GameView(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0), KeyCode.O,
				(a) -> {
					if (a.manager.getCurrentPlayer() == 1) {
						a.manager.incrementTurn();
					}
					return "Turn Incremented"; });
		// Player 3 Next Turn
		toBind.add(new GameView(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0), KeyCode.W,
				(a) -> {
					if (a.manager.getCurrentPlayer() == 2) {
						a.manager.incrementTurn();
					}
					return "Turn Incremented"; });
		// Player 3 Next Turn
		toBind.add(new GameView(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0), KeyCode.COMMA,
				(a) -> {
					if (a.manager.getCurrentPlayer() == 3) {
						a.manager.incrementTurn();
					}
					return "Turn Incremented"; });

		// ----------------------------BUYING LAND-------------------------------

		// Buy Land for Player 1
		toBind.add(new GameView(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0), KeyCode.SPACE,
				(a) -> {
					if (Config.getInstance().numOfPlayers > 0) {
						a.manager.buyTile(a.manager.getPlayerList().get(0));
					}
					return "Bought land for Player 1"; });

		// Buy Land for Player 2
		toBind.add(new GameView(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0), KeyCode.P,
				(a) -> {
					if (Config.getInstance().numOfPlayers > 1) {
						a.manager.buyTile(a.manager.getPlayerList().get(1));
					}
					return "Bought land for Player 2"; });

		// Buy Land for Player 3
		toBind.add(new GameView(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0), KeyCode.Q,
				(a) -> {
					if (Config.getInstance().numOfPlayers > 2) {
						a.manager.buyTile(a.manager.getPlayerList().get(2));
					}
					return "Bought land for Player 3"; });

		// Buy Land for Player 3
		toBind.add(new GameView(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0), KeyCode.PERIOD,
				(a) -> {
					if (Config.getInstance().numOfPlayers > 3) {
						a.manager.buyTile(a.manager.getPlayerList().get(3));
					}
					return "Bought land for Player 4"; });


		// ----------------------------Hotseat Hacks-------------------------------

		toBind.add(new GameView(GameType.HOTSEAT, MULE.PLAY_SCENE, 0), KeyCode.X,
				(a) -> {
					a.manager.commentYourCodeGuys();
					return "Bought land for Player 4"; });



		// ----------------------------Buying Tiles-------------------------------

		toBind.add(Arrays.asList(GameType.SIMULTANEOUS, GameType.HOTSEAT), MULE.PLAY_SCENE, Arrays.asList(0, 1), KeyCode.SPACE,
				(a) -> {
					a.manager.buyTile();
					return "Bought Tile"; });


		// ----------------------------Movement Keys-------------------------------
		// Moving Up
		toBind.add(new GameView(GameType.HOTSEAT, MULE.PLAY_SCENE, 0), KeyCode.UP,
				(a) -> {
					a.map.selectUp();
					return "Moved Up"; });
		toBind.add(Arrays.asList(GameType.HOTSEAT, GameType.SIMULTANEOUS), MULE.PLAY_SCENE, 1, KeyCode.UP,
				(a) -> {
					a.map.selectUp();
					return "Moved Up"; });

		// Moving Down
		toBind.add(new GameView(GameType.HOTSEAT, MULE.PLAY_SCENE, 0), KeyCode.DOWN,
				(a) -> {
					a.map.selectDown();
					return "Moved Down"; });
		toBind.add(Arrays.asList(GameType.SIMULTANEOUS, GameType.HOTSEAT), MULE.PLAY_SCENE, 1, KeyCode.DOWN,
				(a) -> {
					a.map.selectDown();
					return "Moved Down"; });

		// Moving Left
		toBind.add(new GameView(GameType.HOTSEAT, MULE.PLAY_SCENE, 0), KeyCode.LEFT,
				(a) -> {
					a.map.selectLeft();
					return "Moved Left"; });
		toBind.add(Arrays.asList(GameType.SIMULTANEOUS, GameType.HOTSEAT), MULE.PLAY_SCENE, 1, KeyCode.LEFT,
				(a) -> {
					a.map.selectLeft();
					return "Moved Left"; });
		toBind.add(new GameView(GameType.HOTSEAT, MULE.PLAY_SCENE, 1), KeyCode.LEFT,
				(a) -> {
					a.map.selectLeft();
					return "Moved Left"; });

		// Moving Right
		toBind.add(new GameView(GameType.HOTSEAT, MULE.PLAY_SCENE, 0), KeyCode.RIGHT,
				(a) -> {
					a.map.selectRight();
					return "Moved Right"; });
		toBind.add(Arrays.asList(GameType.SIMULTANEOUS, GameType.HOTSEAT), MULE.PLAY_SCENE, 1, KeyCode.RIGHT,
				(a) -> {
					a.map.selectRight();
					return "Moved Right"; });
	}

}
