package io.github.mountainrange.mule.managers;

import io.github.mountainrange.mule.enums.MessageType;

import java.util.function.Function;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class RandomEventManager {
	private List<Function<GameState, String>> events;
	private List<Function<GameState, String>> goodEvents;
	private List<Function<GameState, String>> roundEvents;

	private static final Random r = new Random();

	public RandomEventManager() {
		this(true);
	}

	/**
	 * Creates a RandomEventManager object.
	 *
	 * @param useDefaults whether to use default key bindings or not
	 */
	public RandomEventManager(boolean useDefaults) {
		events = new ArrayList<>();
		goodEvents = new ArrayList<>();
		roundEvents = new ArrayList<>();

		if (useDefaults) {
			RandomEventManager.addDefaultEvents(this);
		}
	}

	/**
	 * Adds an event to this RandomEventManager.
	 *
	 * @param toAdd lambda expression to add
	 */
	public void addEvent(Function<GameState, String> toAdd) {
		events.add(toAdd);
	}

	/**
	 * Adds an event to this RandomEventManager
	 * Events added by this method are deemed 'good' and will be exclusively be selected for the lowest player.
	 * Other players will still get these events, though.
	 *
	 * @param toAdd the lambda expression to add
	 */
	public void addGoodEvent(Function<GameState, String> toAdd) {
		events.add(toAdd);
		goodEvents.add(toAdd);
	}

	/**
	 * Adds a round event to this RandomEventManager
	 * these events will be run once per round
	 *
	 * @param toAdd the lambda expression to add
	 */
	public void addRoundEvent(Function<GameState, String> toAdd) {
		roundEvents.add(toAdd);
	}


	/**
	 * Gets the lambda function associated with this keybinding if one is available
	 *
	 * @return binding if is available, or null if no binding was found
	 */
	public List<Function<GameState, String>> getBindings(boolean onlyGood) {
		return (onlyGood ? Collections.unmodifiableList(goodEvents) : Collections.unmodifiableList(events));
	}

	/**
	 * Runs a random event that is in this manager
	 *
	 * @param datapacket game state to give to the lambda
	 * @param lowestPlayer if this is the lowest score player
	 */
	public void runRandomEvent(GameState datapacket, boolean lowestPlayer) {
		if (!lowestPlayer) {
			events.get(r.nextInt(events.size())).apply(datapacket);
		} else {
			goodEvents.get(r.nextInt(goodEvents.size())).apply(datapacket);
		}
	}

	/**
	 * Runs a random round event that is in this manager
	 *
	 * @param datapacket game state to give to the lambda
	 */
	public void runRoundEvent(GameState datapacket) {
		roundEvents.get(r.nextInt(roundEvents.size())).apply(datapacket);
	}

	/**
	 * Clears all bindings from this manager.
	 */
	public void clear() {
		this.events.clear();
		this.goodEvents.clear();
	}

	/**
	 * A method to initialize the defaults.
	 */
	public static void addDefaultEvents(RandomEventManager toBind) {
		// Add all events
		toBind.addEvent((state) -> {
			MessageType msg = MessageType.LOSEFOOD;
			state.manager.showTempText(msg);
			state.manager.changeFood(msg);
			return "Lose 1 food";
		});
		toBind.addEvent((state) -> {
			MessageType msg = MessageType.LOSESOMEFOOD;
			state.manager.showTempText(msg);
			state.manager.changeFood(msg);
			return "Lose 25% of your food";
		});
		toBind.addEvent((state) -> {
			MessageType msg = MessageType.LOSEHALFFOOD;
			state.manager.showTempText(msg);
			state.manager.changeFood(msg);
			return "Lose half of your food";
		});
		toBind.addEvent((state) -> {
			MessageType msg = MessageType.LOSEENERGY;
			state.manager.showTempText(msg);
			state.manager.changeEnergy(msg);
			return "Lose 1 energy";
		});
		toBind.addEvent((state) -> {
			MessageType msg = MessageType.LOSESOMEENERGY;
			state.manager.showTempText(msg);
			state.manager.changeEnergy(msg);
			return "Lose 25% of your energy";
		});
		toBind.addEvent((state) -> {
			MessageType msg = MessageType.LOSEHALFENERGY;
			state.manager.showTempText(msg);
			state.manager.changeEnergy(msg);
			return "Lose half of your energy";
		});
		toBind.addEvent((state) -> {
			MessageType msg = MessageType.LOSESMITHORE;
			state.manager.showTempText(msg);
			state.manager.changeSmithore(msg);
			return "Lose 1 smithore";
		});
		toBind.addEvent((state) -> {
			MessageType msg = MessageType.LOSESOMESMITHORE;
			state.manager.showTempText(msg);
			state.manager.changeSmithore(msg);
			return "Lose 25% of your smithore";
		});
		toBind.addEvent((state) -> {
			MessageType msg = MessageType.LOSEHALFSMITHORE;
			state.manager.showTempText(msg);
			state.manager.changeSmithore(msg);
			return "Lose half of your smithore";
		});

		// Add good events
		toBind.addGoodEvent((state) -> {
			MessageType msg = MessageType.GAINFOOD;
			state.manager.showTempText(msg);
			state.manager.changeFood(msg);
			return "Gain 1 food";
		});
		toBind.addGoodEvent((state) -> {
			MessageType msg = MessageType.GAINSOMEFOOD;
			state.manager.showTempText(msg);
			state.manager.changeFood(msg);
			return "Gain 50% of your food";
		});
		toBind.addGoodEvent((state) -> {
			MessageType msg = MessageType.GAINDOUBLEFOOD;
			state.manager.showTempText(msg);
			state.manager.changeFood(msg);
			return "Gain 100% of your food";
		});
		toBind.addGoodEvent((state) -> {
			MessageType msg = MessageType.GAINENERGY;
			state.manager.showTempText(msg);
			state.manager.changeEnergy(msg);
			return "Gain 1 energy";
		});
		toBind.addGoodEvent((state) -> {
			MessageType msg = MessageType.GAINSOMEENERGY;
			state.manager.showTempText(msg);
			state.manager.changeEnergy(msg);
			return "Gain 50% of your energy";
		});
		toBind.addGoodEvent((state) -> {
			MessageType msg = MessageType.GAINDOUBLEENERGY;
			state.manager.showTempText(msg);
			state.manager.changeEnergy(msg);
			return "Gain 100% of your energy";
		});
		toBind.addGoodEvent((state) -> {
			MessageType msg = MessageType.GAINSMITHORE;
			state.manager.showTempText(msg);
			state.manager.changeSmithore(msg);
			return "Gain 1 smithore";
		});
		toBind.addGoodEvent((state) -> {
			MessageType msg = MessageType.GAINSOMESMITHORE;
			state.manager.showTempText(msg);
			state.manager.changeSmithore(msg);
			return "Gain 50% of your smithore";
		});
		toBind.addGoodEvent((state) -> {
			MessageType msg = MessageType.GAINDOUBLESMITHORE;
			state.manager.showTempText(msg);
			state.manager.changeSmithore(msg);
			return "Gain 100% of your smithore";
		});

		// Add round events
		toBind.addRoundEvent((state) -> {
			MessageType msg = MessageType.ROUNDGAINFOOD;
			state.manager.showTempHeadline(msg);
			state.manager.changeFood(msg);
			return "All gain 10 food";
		});
		toBind.addRoundEvent((state) -> {
			MessageType msg = MessageType.ROUNDLOSEFOOD;
			state.manager.showTempHeadline(msg);
			state.manager.changeFood(msg);
			return "All lose 3 food";
		});
		toBind.addRoundEvent((state) -> {
			MessageType msg = MessageType.ROUNDGAINENERGY;
			state.manager.showTempHeadline(msg);
			state.manager.changeEnergy(msg);
			return "All gain 10 energy";
		});
		toBind.addRoundEvent((state) -> {
			MessageType msg = MessageType.ROUNDLOSEENERGY;
			state.manager.showTempHeadline(msg);
			state.manager.changeEnergy(msg);
			return "All lose 3 energy";
		});
		toBind.addRoundEvent((state) -> {
			MessageType msg = MessageType.ROUNDGAINSMITHORE;
			state.manager.showTempHeadline(msg);
			state.manager.changeSmithore(msg);
			return "All gain 10 smithore";
		});
		toBind.addRoundEvent((state) -> {
			MessageType msg = MessageType.ROUNDLOSESMITHORE;
			state.manager.showTempHeadline(msg);
			state.manager.changeSmithore(msg);
			return "All lose 3 smithore";
		});
		toBind.addRoundEvent((state) -> {
			MessageType msg = MessageType.SHUFFLE;
			state.manager.showTempHeadline(msg);
			state.manager.changeFood(msg);
			return "Shuffle";
		});
	}
}
