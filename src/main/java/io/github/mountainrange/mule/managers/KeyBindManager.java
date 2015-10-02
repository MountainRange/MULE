package io.github.mountainrange.mule.managers;

import io.github.mountainrange.mule.Config;
import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.enums.GameType;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

public class KeyBindManager {
	private Map<GameState, HashMap<KeyCode, KeyFunction>> keyMap;

	public KeyBindManager() {
		keyMap = new HashMap<>();
		KeyBindManager.addDefaultBindings(this);
	}

	public void add(GameState state, KeyCode toAdd, KeyFunction lambda) {
		if (!keyMap.containsKey(state)) {
			keyMap.put(state, new HashMap<KeyCode, KeyFunction>());
		}
		keyMap.get(state).put(toAdd, lambda);
	}

	public void handleKey(GameState state, KeyCode key, KeyBindPackage datapacket) {
		HashMap<KeyCode, KeyFunction> first = keyMap.get(state);
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
		toBind.add(new GameState(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0), KeyCode.X,
				   (a) -> {
					   if (a.manager.getCurrentPlayer() == 0) {
						   a.manager.incrementTurn();
					   }
					   return "Turn Incremented"; });
		// Player 2 Next Turn
		toBind.add(new GameState(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0), KeyCode.O,
				   (a) -> {
					   if (a.manager.getCurrentPlayer() == 1) {
						   a.manager.incrementTurn();
					   }
					   return "Turn Incremented"; });
		// Player 3 Next Turn
		toBind.add(new GameState(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0), KeyCode.W,
				   (a) -> {
					   if (a.manager.getCurrentPlayer() == 2) {
						   a.manager.incrementTurn();
					   }
					   return "Turn Incremented"; });
		// Player 3 Next Turn
		toBind.add(new GameState(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0), KeyCode.COMMA,
				   (a) -> {
					   if (a.manager.getCurrentPlayer() == 3) {
						   a.manager.incrementTurn();
					   }
					   return "Turn Incremented"; });

		// ----------------------------BUYING LAND-------------------------------

		// Buy Land for Player 1
		toBind.add(new GameState(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0), KeyCode.SPACE,
				   (a) -> {
					   if (Config.getInstance().numOfPlayers > 0) {
						   a.manager.buyTile(a.manager.getPlayerList().get(0));
					   }
					   return "Bought land for Player 1"; });

		// Buy Land for Player 2
		toBind.add(new GameState(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0), KeyCode.P,
				   (a) -> {
					   if (Config.getInstance().numOfPlayers > 1) {
						   a.manager.buyTile(a.manager.getPlayerList().get(1));
					   }
					   return "Bought land for Player 2"; });

		// Buy Land for Player 3
		toBind.add(new GameState(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0), KeyCode.Q,
				   (a) -> {
					   if (Config.getInstance().numOfPlayers > 2) {
						   a.manager.buyTile(a.manager.getPlayerList().get(2));
					   }
					   return "Bought land for Player 3"; });

		// Buy Land for Player 3
		toBind.add(new GameState(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0), KeyCode.PERIOD,
				   (a) -> {
					   if (Config.getInstance().numOfPlayers > 3) {
						   a.manager.buyTile(a.manager.getPlayerList().get(3));
					   }
					   return "Bought land for Player 4"; });


		// ----------------------------Hotseat Hacks-------------------------------

		toBind.add(new GameState(GameType.HOTSEAT, MULE.PLAY_SCENE, 0), KeyCode.X,
				   (a) -> {
					   a.manager.commentYourCodeGuys();
					   return "Bought land for Player 4"; });



		// ----------------------------Buying Tiles-------------------------------

		toBind.add(new GameState(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 0), KeyCode.SPACE,
				   (a) -> {
					   a.manager.buyTile();
					   return "Bought Tile"; });
		toBind.add(new GameState(GameType.HOTSEAT, MULE.PLAY_SCENE, 0), KeyCode.SPACE,
				   (a) -> {
					   a.manager.buyTile();
					   return "Bought Tile"; });
		toBind.add(new GameState(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 1), KeyCode.SPACE,
				   (a) -> {
					   a.manager.buyTile();
					   return "Bought Tile"; });
		toBind.add(new GameState(GameType.HOTSEAT, MULE.PLAY_SCENE, 1), KeyCode.SPACE,
				   (a) -> {
					   a.manager.buyTile();
					   return "Bought Tile"; });


		// ----------------------------Movement Keys-------------------------------
		// Moving Up
		toBind.add(new GameState(GameType.HOTSEAT, MULE.PLAY_SCENE, 0), KeyCode.UP,
				   (a) -> {
					   a.map.selectUp();
					   return "Moved Up"; });
		toBind.add(new GameState(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 1), KeyCode.UP,
				   (a) -> {
					   a.map.selectUp();
					   return "Moved Up"; });
		toBind.add(new GameState(GameType.HOTSEAT, MULE.PLAY_SCENE, 1), KeyCode.UP,
				   (a) -> {
					   a.map.selectUp();
					   return "Moved Up"; });

		// Moving Down
		toBind.add(new GameState(GameType.HOTSEAT, MULE.PLAY_SCENE, 0), KeyCode.DOWN,
				   (a) -> {
					   a.map.selectDown();
					   return "Moved Down"; });
		toBind.add(new GameState(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 1), KeyCode.DOWN,
				   (a) -> {
					   a.map.selectDown();
					   return "Moved Down"; });
		toBind.add(new GameState(GameType.HOTSEAT, MULE.PLAY_SCENE, 1), KeyCode.DOWN,
				   (a) -> {
					   a.map.selectDown();
					   return "Moved Down"; });

		// Moving Left
		toBind.add(new GameState(GameType.HOTSEAT, MULE.PLAY_SCENE, 0), KeyCode.LEFT,
				   (a) -> {
					   a.map.selectLeft();
					   return "Moved Left"; });
		toBind.add(new GameState(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 1), KeyCode.LEFT,
				   (a) -> {
					   a.map.selectLeft();
					   return "Moved Left"; });
		toBind.add(new GameState(GameType.HOTSEAT, MULE.PLAY_SCENE, 1), KeyCode.LEFT,
				   (a) -> {
					   a.map.selectLeft();
					   return "Moved Left"; });

		// Moving Right
		toBind.add(new GameState(GameType.HOTSEAT, MULE.PLAY_SCENE, 0), KeyCode.RIGHT,
				   (a) -> {
					   a.map.selectRight();
					   return "Moved Right"; });
		toBind.add(new GameState(GameType.SIMULTANEOUS, MULE.PLAY_SCENE, 1), KeyCode.RIGHT,
				   (a) -> {
					   a.map.selectRight();
					   return "Moved Right"; });
		toBind.add(new GameState(GameType.HOTSEAT, MULE.PLAY_SCENE, 1), KeyCode.RIGHT,
				   (a) -> {
					   a.map.selectRight();
					   return "Moved Right"; });
	}

}
