package io.github.mountainrange.mule.enums;

import java.util.Random;

/**
 * List of valid difficulties.
 */
public enum MessageType {
	NONE(""),
	TURN(),
	NORMAL("The game of MULE begins now. Player 1's turn has begun."),
	LANDGRAB("Land Selection Phase"),
	BUY("You have purchased a promising plot of land."),
	GOTMULE("You have purchased an eager MULE ready for work."),
	LOSEFOOD(new String[] {"A bird stole your sandwich. You lose 1 food.",
			"You breakfast combusted. You lose 1 food.",
			"You left the eggs out on the counter. You lose 1 food."}),
	LOSESOMEFOOD(new String[] {"You dropped some food into a crevasse. You lose some food.",
			"A fire broke out in food storage. You lose some food.",
			"You misread the expiration date and threw out some food. You lose some food."}),
	LOSEHALFFOOD(new String[] {"A meteorite has struck your food reserves. You lose half your food!",
			"A dust storm has blown away a portion of your food. You lose half your food!",
			"One of your food silos has exploded inexplicably. You lose half your food!"}),
	GAINFOOD(new String[] {"You found an extra box of cereal in the cabinet. You gain 1 food.",
			"A bird carrying a sandwich fell out of the sky. You gain 1 food.",
			"You forget to eat breakfast. You gain 1 food."}),
	GAINSOMEFOOD(new String[] {"You have more food for some reason. You gain some food.",
			"You went on a diet. You gain some food.",
			"You won a lifetime supply of triskets. You gain some food."}),
	GAINDOUBLEFOOD(new String[] {"You decide to use AVL trees to grow your food. You doubled your food!",
			"Something happened. Something happened.",
			"Your food discovered Mitosis. You doubled your food!"});

	private String msg;
	private String[] msgs;
	private boolean multiEvent;

	MessageType() {
		multiEvent = false;
		this.msg = "";
	}

	MessageType(String msg) {
		multiEvent = false;
		this.msg = msg;
	}

	MessageType(String[] msgs) {
		multiEvent = true;
		this.msgs = msgs;
	}

	public String getMsg() {
		if (!multiEvent) {
			return msg;
		} else {
			return msgs[new Random().nextInt(msgs.length)];
		}
	}

	public String getPlayerTurnMessage(int num) {
		this.msg = String.format("Player %d's turn has now begun.", (num + 1));
		return getMsg();
	}
}
