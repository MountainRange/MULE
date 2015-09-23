package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.GameManager;
import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.*;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Matthew Keezer on 9/9/2015.
 */
public class AboutController implements Initializable, SceneAgent {

	private SceneLoader sceneLoader;
	private MULE mule;

	@FXML
	private TextFlow textFlow;

	@FXML
	private ScrollPane scrollPane;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Text h1 = new Text("About MULE\n\n");
		h1.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
		textFlow.getChildren().add(h1);
		Text p1 = new Text("Hi! Welcome to MULE! This game is a recreation of an old Atari game from 1983. Here is an excerpt from Wikipedia explaining the game:\n" +
				"\n" +
				"Set on the fictional planet Irata (which is Atari backwards), the game is an exercise in supply and demand economics involving competition among four players, with computer opponents automatically filling in for any missing players. Players are provided with several different choices for the race of their colonist, providing different advantages and disadvantages that can be paired to their respective strategies. To win, players not only compete against each other to amass the largest amount of wealth, but must also cooperate for the survival of the colony.\n" +
				"\n" +
				"Central to the game is the acquisition and use of \"M.U.L.E.\"s (Multiple Use Labor Element) to develop and harvest resources from the player's real estate. Depending on how it is outfitted, a M.U.L.E. can be configured to harvest Energy, Food, Smithore (from which M.U.L.E.s are constructed), and Crystite (a valuable mineral available only at the \"Tournament\" level). Players must balance supply and demand of these elements, buying what they need, and selling what they don't. Players may also exploit or create shortages by refusing to sell to other players or to the \"store,\" which raises the price of the resource on the following turns. Scheming between players is encouraged by allowing collusion, which initiates a mode allowing a private transaction. Crystite is the one commodity that is not influenced by supply and demand considerations, being deemed to be sold 'off world,' so the strategy with this resource is somewhat different player may attempt to maximize production without fear of having too much supply for the demand.\n" +
				"\n" +
				"Each resource is required to do certain things on each turn. For instance, if a player is short on Food, there will be less time to take one's turn. Similarly, if a player is short on Energy, some land plots won't produce any output, while a shortage of Smithore will raise the price of M.U.L.E.s in the store and prevent the store from manufacturing new M.U.L.E.s to make use of one's land.\n" +
				"\n" +
				"Players must also deal with periodic random events such as run-away M.U.L.E.s, sunspot activity, theft by space pirates and a meteorite,[6] with potentially destructive and beneficial effects. The game features a balancing system for random events that impact only a single player, such that favorable events never happen to the player currently in first place, while unfavorable events never happen to the player in last place.[7] This same \"leveling of the playfield\" is applied whenever a tie happens in the game (e.g. when two players want to buy a resource at the same price); the player in the losing position automatically wins the tie. The players also can hunt the mountain wampus for a cash reward.");
		p1.setFont(Font.font("Verdana", 18));
		textFlow.getChildren().add(p1);
		textFlow.translateXProperty().bind(scrollPane.widthProperty().subtract(textFlow.widthProperty()).divide(2));
	}

	public void setSceneParent(SceneLoader sceneLoader, MULE mule) {
		this.sceneLoader = sceneLoader;
		this.mule = mule;
	}

	public void onSetScene() {
	}

	@FXML
	private void handleBackAction(ActionEvent e) {
		sceneLoader.goBack();
	}

}
