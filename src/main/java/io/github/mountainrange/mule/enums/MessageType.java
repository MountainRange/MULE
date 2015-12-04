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
			"Your food discovered Mitosis. You doubled your food!"}),
	ROUNDGAINFOOD(new String[] {"Manure falls from the sky. Everyone gains 10 food.",
			"Mansa Musa travels to the planet and gives away free food. Everyone gains 10 food.",
			"Carrots discover mitosis. Everyone gains 10 food."}),
	ROUNDLOSEFOOD(new String[] {"Potatoes sink back into the ground. Everyone loses 3 food.",
			"Some food stops being food. Everyone loses 3 food.",
			"Global warming. Everyone loses 3 food."}),
	LOSEENERGY(new String[] {"A bird stole your energy bar. You lose 1 energy.",
			"You breakfast combusted. You lose 1 energy.",
			"You left the stove on. You lose 1 energy."}),
	LOSESOMEENERGY(new String[] {"You dropped some energy into a crevasse. You lose some energy.",
			"A fire broke out in energy storage. You lose some energy.",
			"You misread the expiration date and threw out some energy. You lose some energy."}),
	LOSEHALFENERGY(new String[] {"A meteorite has struck your energy reserves. You lose half your energy!",
			"A dust storm has blown away a portion of your energy. You lose half your energy!",
			"One of your energy silos has exploded inexplicably. You lose half your energy!"}),
	GAINENERGY(new String[] {"You found an extra box of energy oats in the cabinet. You gain 1 energy.",
			"A bird carrying a fusion rod fell out of the sky. You gain 1 energy.",
			"You eat breakfast. You gain 1 energy."}),
	GAINSOMEENERGY(new String[] {"You have more energy for some reason. You gain some energy.",
			"You quit your diet. You gain some energy.",
			"You won a lifetime supply of triskets. You gain some energy."}),
	GAINDOUBLEENERGY(new String[] {"You decide to use AVL trees to grow your energy. You doubled your energy!",
			"Your energy discovered Mitosis. You doubled your energy!"}),
	ROUNDGAINENERGY(new String[] {"Energy cubes falls from the sky. Everyone gains 10 energy.",
			"Mansa Musa travels to the planet and gives away free energy. Everyone gains 10 energy.",
			"Energy discover mitosis. Everyone gains 10 energy."}),
	ROUNDLOSEENERGY(new String[] {"Energy sinks back into the ground. Everyone loses 3 energy.",
			"Some energy stops being energy. Everyone loses 3 energy.",
			"Entropy rises. Everyone loses 3 energy."}),
	LOSESMITHORE(new String[] {"A bird stole your smithore sandwich. You lose 1 smithore.",
			"You smithore combusted. You lose 1 smithore.",
			"You left the smithore eggs out on the counter. You lose 1 smithore."}),
	LOSESOMESMITHORE(new String[] {"You dropped some smithore into a crevasse. You lose some smithore.",
			"A fire broke out in smithore storage. You lose some smithore.",
			"You misread the expiration date and threw out some smithore. You lose some smithore."}),
	LOSEHALFSMITHORE(new String[] {"A meteorite has struck your smithore reserves. You lose half your smithore!",
			"A dust storm has blown away a portion of your smithore. You lose half your smithore!",
			"One of your smithore silos has exploded inexplicably. You lose half your smithore!"}),
	GAINSMITHORE(new String[] {"You found an extra box of rocks in the cabinet. You gain 1 smithore.",
			"A bird carrying a smithore sandwich fell out of the sky. You gain 1 smithore.",
			"You forget to eat your daily smithore. You gain 1 smithore."}),
	GAINSOMESMITHORE(new String[] {"You have more smithore for some reason. You gain some smithore.",
			"You stopped eating smithore. You gain some smithore.",
			"You won a lifetime supply of rocks. You gain some smithore."}),
	GAINDOUBLESMITHORE(new String[] {"You decide to use AVL trees to mine your smithore. You doubled your smithore!",
			"Your smithore discovered Mitosis. You doubled your smithore!"}),
	ROUNDGAINSMITHORE(new String[] {"Rocks falls from the sky. Everyone gains 10 smithore.",
			"Mansa Musa travels to the planet and gives away free smithore. Everyone gains 10 smithore.",
			"Minerals discover mitosis. Everyone gains 10 smithore."}),
	ROUNDLOSESMITHORE(new String[] {"Rocks sink back into the ground. Everyone loses 3 smithore.",
			"Some smithore stops being smithore. Everyone loses 3 smithore.",
			"Smithore warming. Everyone loses 3 smithore."}),
	SHUFFLE(new String[] {"Earthquake!",
			"The lands have switch places!",
			"The gods have spoken!",
			"Plz gib extra credit jeremy.",
			"Plz gib extra credit jeremy.",
			"Plz gib extra credit jeremy.",
			"Plz gib extra credit jeremy.",
			"Plz gib extra credit jeremy.",
			"Plz gib extra credit jeremy.",
			"Plz gib extra credit jeremy.",
			"Plz gib extra credit jeremy.",
			"Plz gib extra credit jeremy.",
			"Musical chairs!"});

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
