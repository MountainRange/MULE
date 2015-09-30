package io.github.mountainrange.mule.managers;

import javafx.scene.input.KeyCode;
import io.github.mountainrange.mule.enums.GameType;
import io.github.mountainrange.mule.GameManager;
import java.util.HashMap;
import java.util.Map;

public class KeyBindManager {
	private Map<GameState, Map<KeyCode, KeyFunction>> keyMap;

	public KeyBindManager() {
		keyMap = new HashMap<GameState, Map<KeyCode, KeyFunction>>();
	}

	public void add(GameState state, KeyCode toAdd, KeyFunction lambda) {
		if (!keyMap.containsKey(state)) {
			keyMap.put(state, new HashMap<KeyCode, KeyFunction>());
		}
		keyMap.get(state).put(toAdd, lambda);
	}

	public void handleKey(GameState state, KeyCode key, KeyBindPackage datapacket) {
		keyMap.get(state).get(key).act(datapacket);
	}

	public class GameState {
		private GameType gameType;
		private String sceneName;

		public GameState(GameType gt, String sn) {
			this.gameType = gt;
			this.sceneName = sn;
		}

		@Override
		public int hashCode() {
			return this.gameType.hashCode() + this.sceneName.hashCode() * 7;
		}

		@Override
		public boolean equals(Object other) {
			if (!(other instanceof GameState)) {
				return false;
			} else if (other == this) {
				return true;
			} else {
				GameState toCompare = (GameState) other;
				return toCompare.gameType.equals(this.gameType) && toCompare.sceneName.equals(this.sceneName);
			}
		}
	}

	public class KeyBindPackage {
		public GameManager manager;

		public KeyBindPackage(GameManager m) {
			this.manager = m;
		}
	}

	public interface KeyFunction {
		public String act(KeyBindPackage m);
	}
}
