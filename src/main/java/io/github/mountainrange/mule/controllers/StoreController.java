package io.github.mountainrange.mule.controllers;

import io.github.mountainrange.mule.MULE;
import io.github.mountainrange.mule.SceneLoader;
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
		if (buyNotSell) {
			mule.getGameManager().getShop().buy(mule.getGameManager().getCurrentPlayer(), ResourceType.FOOD);
		} else {
			mule.getGameManager().getShop().sell(mule.getGameManager().getCurrentPlayer(), ResourceType.FOOD);
		}
	}

	@FXML
	private void handleExchangeEnergyAction(ActionEvent e) {
		if (buyNotSell) {
			mule.getGameManager().getShop().buy(mule.getGameManager().getCurrentPlayer(), ResourceType.ENERGY);
		} else {
			mule.getGameManager().getShop().sell(mule.getGameManager().getCurrentPlayer(), ResourceType.ENERGY);
		}
	}

	@FXML
	private void handleExchangeSmithoreAction(ActionEvent e) {
		if (buyNotSell) {
			mule.getGameManager().getShop().buy(mule.getGameManager().getCurrentPlayer(), ResourceType.SMITHORE);
		} else {
			mule.getGameManager().getShop().sell(mule.getGameManager().getCurrentPlayer(), ResourceType.SMITHORE);
		}
	}

	@FXML
	private void handleExchangeCrystiteAction(ActionEvent e) {
		if (buyNotSell) {
			mule.getGameManager().getShop().buy(mule.getGameManager().getCurrentPlayer(), ResourceType.CRYSTITE);
		} else {
			mule.getGameManager().getShop().sell(mule.getGameManager().getCurrentPlayer(), ResourceType.CRYSTITE);
		}
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
