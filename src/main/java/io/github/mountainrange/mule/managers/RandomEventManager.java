package io.github.mountainrange.mule.managers;

import io.github.mountainrange.mule.Config;
import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.enums.GameType;
import io.github.mountainrange.mule.enums.MessageType;
import javafx.scene.input.KeyCode;

import java.util.Arrays;
import java.util.function.Function;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class RandomEventManager {
	private ArrayList<Function<GameState, String>> events;
	private ArrayList<Function<GameState, String>> goodEvents;

	private static final Random r = new Random();

	public RandomEventManager() {
		this(true);
	}

	/**
	 * Creates a RandomEventManager object
	 *
	 * @param useDefaults whether to use default key bindings or not
	 */
	public RandomEventManager(boolean useDefaults) {
		events = new ArrayList<>();
		goodEvents = new ArrayList<>();

		if (useDefaults) {
			RandomEventManager.addDefaultEvents(this);
		}
	}

	/**
	 * Adds an event to this RandomEventManager
	 *
	 * @param toAdd the lambda expression to add
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
	 * Gets the lambda function associated with this keybinding if one is available
	 * @return The binding if is available, null if no binding was found
	 */
	public List<Function<GameState, String>> getBindings(boolean onlyGood) {
		return (onlyGood ? Collections.unmodifiableList(goodEvents) : Collections.unmodifiableList(events));
	}

	/**
	 * Runs a random event that is in this manager
	 *
	 * @param datapacket the Datapacket to use on the lambda
	 */
	public void runRandomEvent(GameState datapacket, boolean lowestPlayer) {
		if (!lowestPlayer) {
			events.get(r.nextInt(events.size())).apply(datapacket);
		} else {
			goodEvents.get(r.nextInt(goodEvents.size())).apply(datapacket);
		}
	}

	/**
	 * Clears all bindings from this manager
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
				state.manager.decreaseFood(msg);
				return "Lose 1 food";
			});
		toBind.addEvent((state) -> {
				MessageType msg = MessageType.LOSESOMEFOOD;
				state.manager.showTempText(msg);
				state.manager.decreaseFood(msg);
				return "Lose 25% of your food";
			});
		toBind.addEvent((state) -> {
				MessageType msg = MessageType.LOSEHALFFOOD;
				state.manager.showTempText(msg);
				state.manager.decreaseFood(msg);
				return "Lose half of your food";
			});
		toBind.addEvent((state) -> {
				MessageType msg = MessageType.GAINFOOD;
				state.manager.showTempText(msg);
				state.manager.decreaseFood(msg);
				return "Gain 1 food";
			});
		toBind.addEvent((state) -> {
				MessageType msg = MessageType.GAINSOMEFOOD;
				state.manager.showTempText(msg);
				state.manager.decreaseFood(msg);
				return "Gain 50% of your food";
			});
		toBind.addEvent((state) -> {
				MessageType msg = MessageType.GAINDOUBLEFOOD;
				state.manager.showTempText(msg);
				state.manager.decreaseFood(msg);
				return "Gain 100% of your food";
			});

		// Add good events
		toBind.addGoodEvent((state) -> {
				MessageType msg = MessageType.GAINFOOD;
				state.manager.showTempText(msg);
				state.manager.decreaseFood(msg);
				return "Gain 1 food";
			});
		toBind.addGoodEvent((state) -> {
				MessageType msg = MessageType.GAINSOMEFOOD;
				state.manager.showTempText(msg);
				state.manager.decreaseFood(msg);
				return "Gain 50% of your food";
			});
		toBind.addGoodEvent((state) -> {
				MessageType msg = MessageType.GAINDOUBLEFOOD;
				state.manager.showTempText(msg);
				state.manager.decreaseFood(msg);
				return "Gain 100% of your food";
			});
	}
}
