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
	SOMEEVENT(new String[] {"TEST EVENT TEXT 1", "TEST EVENT TEXT 2", "TEST EVENT TEXT 3"}),
	OTHER(new String[] {"OTHER EVENT TEXT 1", "OTHER EVENT TEXT 2", "OTHER EVENT TEXT 3"});

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
		this.msg = "Player " + (num+1) + "'s turn has now begun.";
		return getMsg();
	}
}
