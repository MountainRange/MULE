package io.github.mountainrange.mule.enums;

import java.util.Random;

/**
 * List of valid difficulties.
 */
public enum MessageType {
	BUY("You have purchased a promising plot of land."),
	GOTMULE("You have purchased an eager MULE ready for work."),
	OTHER(new String[] {"TEST EVENT 1", "TEST EVENT 2", "TEST EVENT 3"});

	private String msg;
	private String[] msgs;
	private boolean multiEvent;

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
}
