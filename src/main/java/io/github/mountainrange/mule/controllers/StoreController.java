package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;
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
		if (buySellCombo.getValue().equals(new String("Buy"))) {
			buyNotSell = true;
		} else if (buySellCombo.getValue().equals(new String("Sell"))) {
			buyNotSell = false;
		}
	}

	@FXML
	private void handleExchangeFoodAction(ActionEvent e) {
		Shop shop = mule.getGameManager().getShop();
		Player player = mule.getGameManager().getCurrentPlayer();
		if (buyNotSell) {
			if (shop.stockOf(ResourceType.FOOD) > 0) {
				if (player.getMoney() >= shop.priceOf(ResourceType.FOOD)) {
					shop.buy(player, ResourceType.FOOD);
					System.out.println(player.getMoney());
				} else {
					System.out.println("Player does not have enough money");
				}
			} else {
				System.out.println("Shop is out of food");
			}
		} else {
			if (player.stockOf(ResourceType.FOOD) > 0) {
				shop.sell(player, ResourceType.FOOD);
				System.out.println(player.getMoney());
			} else {
				System.out.println("You are out of food");
			}
		}
		mule.getGameManager().setLabels();
	}

	@FXML
	private void handleExchangeEnergyAction(ActionEvent e) {
		Shop shop = mule.getGameManager().getShop();
		Player player = mule.getGameManager().getCurrentPlayer();
		if (buyNotSell) {
			if (shop.stockOf(ResourceType.ENERGY) > 0) {
				if (player.getMoney() >= shop.priceOf(ResourceType.ENERGY)) {
					shop.buy(player, ResourceType.ENERGY);
					System.out.println(player.getMoney());
				} else {
					System.out.println("Player does not have enough money");
				}
			} else {
				System.out.println("Shop is out of energy");
			}
		} else {
			if (player.stockOf(ResourceType.ENERGY) > 0) {
				shop.sell(player, ResourceType.ENERGY);
				System.out.println(player.getMoney());
			} else {
				System.out.println("You are out of energy");
			}
		}
		mule.getGameManager().setLabels();
	}

	@FXML
	private void handleExchangeSmithoreAction(ActionEvent e) {
		Shop shop = mule.getGameManager().getShop();
		Player player = mule.getGameManager().getCurrentPlayer();
		if (buyNotSell) {
			if (shop.stockOf(ResourceType.SMITHORE) > 0) {
				if (player.getMoney() >= shop.priceOf(ResourceType.SMITHORE)) {
					shop.buy(player, ResourceType.SMITHORE);
					System.out.println(player.getMoney());
				} else {
					System.out.println("Player does not have enough money");
				}
			} else {
				System.out.println("Shop is out of smithore");
			}
		} else {
			if (player.stockOf(ResourceType.SMITHORE) > 0) {
				shop.sell(player, ResourceType.SMITHORE);
				System.out.println(player.getMoney());
			} else {
				System.out.println("You are out of smithore");
			}
		}
		mule.getGameManager().setLabels();
	}

	@FXML
	private void handleExchangeCrystiteAction(ActionEvent e) {
		Shop shop = mule.getGameManager().getShop();
		Player player = mule.getGameManager().getCurrentPlayer();
		if (buyNotSell) {
			if (shop.stockOf(ResourceType.CRYSTITE) > 0) {
				if (player.getMoney() >= shop.priceOf(ResourceType.CRYSTITE)) {
					shop.buy(player, ResourceType.CRYSTITE);
					System.out.println(player.getMoney());
				} else {
					System.out.println("Player does not have enough money");
				}
			} else {
				System.out.println("Shop is out of crystite");
			}
		} else {
			if (player.stockOf(ResourceType.CRYSTITE) > 0) {
				shop.sell(player, ResourceType.CRYSTITE);
				System.out.println(player.getMoney());
			} else {
				System.out.println("You are out of crystite");
			}
		}
		mule.getGameManager().setLabels();
	}

	@FXML
	private void handleExchangeMuleAction(ActionEvent e) {
		// todo
	}


	@FXML
	private void handleLeaveAction(ActionEvent e) {
		sceneLoader.goBack();
	}
}
