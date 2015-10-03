package io.github.mountainrange.mule.managers;

import io.github.mountainrange.mule.enums.GameType;

/**
 * Represents the state of the game.
 */
public class GameView {
	private GameType gameType;
	private String sceneName;
	private int phaseCount;

	public GameView(GameType gt, String sn, int phaseCount) {
		this.gameType = gt;
		this.sceneName = sn;
		this.phaseCount = phaseCount;
	}

	@Override
	public int hashCode() {
		return (this.gameType.hashCode() + this.sceneName.hashCode() * 7
				+ this.phaseCount * 49);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof GameView)) {
			return false;
		} else if (other == this) {
			return true;
		} else {
			GameView toCompare = (GameView) other;
			return (toCompare.gameType.equals(this.gameType) && toCompare.sceneName.equals(this.sceneName)
					&& toCompare.phaseCount == this.phaseCount);
		}
	}
}
