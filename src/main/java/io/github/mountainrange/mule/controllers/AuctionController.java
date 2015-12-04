package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.Config;
import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;
import io.github.mountainrange.mule.gameplay.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for auction screen.
 *
 * Not in extensive use now, but this handles all button presses that take place in the Auction Screen.
 */
public class AuctionController implements Initializable, SceneAgent {

	private SceneLoader sceneLoader;
	private MULE mule;
	private List<Player> buyers;
	private List<Integer> offers;
	private List<Label> labels;
	private List<Button> buttons;
	private int highestPrice;
	private Player currentLeader;

	@FXML
	private Label auctionLeader;

	@FXML
	private Label p1offer;

	@FXML
	private Label p2offer;

	@FXML
	private Label p3offer;

	@FXML
	private Label p4offer;

	@FXML
	private Button p1button;

	@FXML
	private Button p2button;

	@FXML
	private Button p3button;

	@FXML
	private Button p4button;

	@FXML
	private Button backButton;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		backButton.setCancelButton(true);
		offers = new ArrayList<>();
		labels = new ArrayList<>();
		labels.add(p1offer);
		labels.add(p2offer);
		labels.add(p3offer);
		labels.add(p4offer);
		buttons = new ArrayList<>();
		buttons.add(p1button);
		buttons.add(p2button);
		buttons.add(p3button);
		buttons.add(p4button);
	}

	public void setSceneParent(SceneLoader sceneLoader, MULE mule){
		this.sceneLoader = sceneLoader;
		this.mule = mule;
	}

	public void onSetScene() {
		buyers = Config.getInstance().buyers;
		currentLeader = buyers.get(0);
		highestPrice = 0;
		offers.clear();
		for (int i = 0; i < labels.size(); i++) {
			offers.add(i, 0);
			labels.get(i).setText("" + offers.get(i));
			if (i >= buyers.size()) {
				labels.get(i).setDisable(true);
				buttons.get(i).setDisable(true);
			}
		}
	}

	@FXML
	private void handleP1OfferAction(ActionEvent e) {
		increaseOffer(0);
	}

	@FXML
	private void handleP2OfferAction(ActionEvent e) {
		increaseOffer(1);
	}

	@FXML
	private void handleP3OfferAction(ActionEvent e) {
		increaseOffer(2);
	}

	@FXML
	private void handleP4OfferAction(ActionEvent e) {
		increaseOffer(3);
	}

	private void increaseOffer(int i) {
		if (buyers.size() > i) {
			Player buyer = buyers.get(i);
			int offer = offers.get(i);
			if (buyer.getMoney() >= offer + 5) {
				offers.set(i, offer + 5);
				labels.get(i).setText("" + offer);
				if (offer > highestPrice) {
					highestPrice = offer;
					currentLeader = buyer;
					auctionLeader.setText(buyer.getName() + " - " + offer);
				}
			}
		}
	}

	@FXML
	private void handleBackAction(ActionEvent e) {
		sceneLoader.goBack();
	}
}
