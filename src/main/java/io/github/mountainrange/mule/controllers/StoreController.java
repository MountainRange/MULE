package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;
import io.github.mountainrange.mule.enums.MuleType;
import io.github.mountainrange.mule.gameplay.Player;
import io.github.mountainrange.mule.gameplay.Shop;
import io.github.mountainrange.mule.enums.ResourceType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Josh Ting on 9/12/2015.
 */
public class StoreController implements Initializable, SceneAgent {

	private SceneLoader sceneLoader;
	private MULE mule;
	private boolean buyNotSell;
	private Shop shop;
	private Player player;

	@FXML
	private ComboBox buySellCombo;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		buySellCombo.setItems(FXCollections.observableArrayList());
		buySellCombo.getItems().add("Buy");
		buySellCombo.getItems().add("Sell");
	}

	public void setSceneParent(SceneLoader sceneLoader, MULE mule){
		this.sceneLoader = sceneLoader;
		this.mule = mule;
	}

	public void onSetScene() {
	}

	@FXML
	private void handleBuySellToggle(ActionEvent e) {
		if (buySellCombo.getValue().equals("Buy")) {
			buyNotSell = true;
		} else if (buySellCombo.getValue().equals("Sell")) {
			buyNotSell = false;
		}
	}

	@FXML
	private void handleExchangeFoodAction(ActionEvent e) {
		shop = mule.getGameManager().getShop();
		player = mule.getGameManager().getCurrentPlayer();
		if (buyNotSell) {
			shop.buy(player, ResourceType.FOOD);
		} else {
			shop.sell(player, ResourceType.FOOD);
		}
		mule.getGameManager().setLabels();
	}

	@FXML
	private void handleExchangeEnergyAction(ActionEvent e) {
		shop = mule.getGameManager().getShop();
		player = mule.getGameManager().getCurrentPlayer();
		if (buyNotSell) {
			shop.buy(player, ResourceType.ENERGY);
		} else {
			shop.sell(player, ResourceType.ENERGY);
		}
		mule.getGameManager().setLabels();
	}

	@FXML
	private void handleExchangeSmithoreAction(ActionEvent e) {
		shop = mule.getGameManager().getShop();
		player = mule.getGameManager().getCurrentPlayer();
		if (buyNotSell) {
			shop.buy(player, ResourceType.SMITHORE);
		} else {
			shop.sell(player, ResourceType.SMITHORE);
		}
		mule.getGameManager().setLabels();
	}

	@FXML
	private void handleExchangeCrystiteAction(ActionEvent e) {
		shop = mule.getGameManager().getShop();
		player = mule.getGameManager().getCurrentPlayer();
		if (buyNotSell) {
			shop.buy(player, ResourceType.CRYSTITE);
		} else {
			shop.sell(player, ResourceType.CRYSTITE);
		}
		mule.getGameManager().setLabels();
	}

	@FXML
	private void handleExchangeFoodMuleAction(ActionEvent e) {
		doMule(MuleType.FOOD_MULE);
	}

	@FXML
	private void handleExchangeEnergyMuleAction(ActionEvent e) {
		doMule(MuleType.ENERGY_MULE);
	}

	@FXML
	private void handleExchangeSmithoreMuleAction(ActionEvent e) {
		doMule(MuleType.SMITHORE_MULE);
	}

	private void doMule(MuleType muleType) {
		shop = mule.getGameManager().getShop();
		player = mule.getGameManager().getCurrentPlayer();
		if (buyNotSell) {
			player.setCurrentMuleType(muleType);
			shop.buy(player, ResourceType.MULE);
		} else {
			shop.sell(player, ResourceType.MULE);
		}
		mule.getGameManager().setLabels();
	}


	@FXML
	private void handleLeaveAction(ActionEvent e) {
		sceneLoader.goBack();
	}
}
