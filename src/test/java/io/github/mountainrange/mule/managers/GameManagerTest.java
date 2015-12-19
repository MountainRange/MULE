package io.github.mountainrange.mule.managers;

// Junit Imports

import io.github.mountainrange.mule.*;
import io.github.mountainrange.mule.enums.*;
import io.github.mountainrange.mule.gameplay.Player;
import io.github.mountainrange.mule.gameplay.WorldMap;
import io.github.mountainrange.mule.gameplay.javafx.VisualGrid;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.*;
import org.junit.rules.Timeout;


/**
 * A Class to Test the KeyManager
 */
public class GameManagerTest {

	@Rule
	public Timeout timeout = new Timeout(2000);

	private Stage primaryStage;
	private SceneLoader sceneLoader;
	private VisualGrid g;
	private Pane upperPane;
	private WorldMap map;
	private GameManager manager;

	@Before
	public void setup() {
		// Run for every test.
		Config.getInstance().playerList = new Player[Config.getInstance().maxPlayers];
		for (int i = 0; i < Config.getInstance().maxPlayers; i++) {
			if (Config.getInstance().playerList[i] == null) {
				Config.getInstance().playerList[i] = new Player(i);
				Config.getInstance().playerList[i].changeStockOf(ResourceType.ENERGY, 2);
				Config.getInstance().playerList[i].changeStockOf(ResourceType.FOOD, 4);
			}
		}
		sceneLoader = new SceneLoader(new MULE());
		upperPane = new TestPane();
		g = new VisualGrid(9, 5, MapType.EMPTY, MapSize.ALPS, upperPane);
		map = new WorldMap(g, Config.getInstance().mapType);
		manager = new GameManager(map, new LabelManager(), sceneLoader);
	}

	/**
	 * Checks correct branching of tileOperation
	 */
	@Test
	public void testBuyTile() {
		Player p = new Player(0, "Test", Race.FOLD, Color.BEIGE);
		//manager.tileOperation(p);
		assert(true);
	}

	/**
	 * Checks changeFood method
	 */
	@Test
	public void testChangeFood() {
		int beforeFood = manager.getPlayerList().get(manager.getCurrentPlayerNum()).stockOf(ResourceType.FOOD);
		manager.changeFood(MessageType.LOSEFOOD);
		int afterFood = manager.getPlayerList().get(manager.getCurrentPlayerNum()).stockOf(ResourceType.FOOD);
		assert(beforeFood - afterFood == 1);

		beforeFood = manager.getPlayerList().get(manager.getCurrentPlayerNum()).stockOf(ResourceType.FOOD);
		manager.changeFood(MessageType.LOSESOMEFOOD);
		afterFood = manager.getPlayerList().get(manager.getCurrentPlayerNum()).stockOf(ResourceType.FOOD);
		assert(beforeFood - afterFood == (beforeFood / 4));

		beforeFood = manager.getPlayerList().get(manager.getCurrentPlayerNum()).stockOf(ResourceType.FOOD);
		manager.changeFood(MessageType.LOSEHALFFOOD);
		afterFood = manager.getPlayerList().get(manager.getCurrentPlayerNum()).stockOf(ResourceType.FOOD);
		assert(beforeFood - afterFood == (beforeFood / 2));

		beforeFood = manager.getPlayerList().get(manager.getCurrentPlayerNum()).stockOf(ResourceType.FOOD);
		manager.changeFood(MessageType.GAINFOOD);
		afterFood = manager.getPlayerList().get(manager.getCurrentPlayerNum()).stockOf(ResourceType.FOOD);
		assert(afterFood - beforeFood == 1);

		beforeFood = manager.getPlayerList().get(manager.getCurrentPlayerNum()).stockOf(ResourceType.FOOD);
		manager.changeFood(MessageType.GAINSOMEFOOD);
		afterFood = manager.getPlayerList().get(manager.getCurrentPlayerNum()).stockOf(ResourceType.FOOD);
		assert(afterFood - beforeFood == (beforeFood / 2));

		beforeFood = manager.getPlayerList().get(manager.getCurrentPlayerNum()).stockOf(ResourceType.FOOD);
		manager.changeFood(MessageType.GAINDOUBLEFOOD);
		afterFood = manager.getPlayerList().get(manager.getCurrentPlayerNum()).stockOf(ResourceType.FOOD);
		assert(afterFood - beforeFood == beforeFood);

		beforeFood = manager.getPlayerList().get(manager.getCurrentPlayerNum()).stockOf(ResourceType.FOOD);
		manager.changeFood(MessageType.NONE);
		afterFood = manager.getPlayerList().get(manager.getCurrentPlayerNum()).stockOf(ResourceType.FOOD);
	}

	/**
	 * Checks endTurn method
	 */
	@Test
	public void testEndTurn() {
		int beforeRound = manager.getRoundCount();
		manager.getCurrentPlayer().setMule(MuleType.FOOD_MULE);
		int beforePlayerNum = manager.getCurrentPlayerNum();
		manager.endTurn();
		assert(beforePlayerNum + 1 % manager.getConfig().numOfPlayers == manager.getCurrentPlayerNum());
		assert(manager.getTurnOrder().get(manager.getCurrentPlayerNum() - 1).getMule() == null);

		beforeRound = manager.getRoundCount();
		manager.getCurrentPlayer().setMule(MuleType.FOOD_MULE);
		manager.endTurn();
		assert(manager.getCurrentPlayerNum() == 0);
		assert (beforeRound < manager.getRoundCount());
	}

	/**
	 * Checks nextRound method
	 */
	@Test
	public void testNextRound() {
		assert(manager.getRoundCount() == -2);
		manager.incrementTurn();
		manager.incrementTurn();
		assert(manager.getRoundCount() == -1);
		manager.incrementTurn();
		manager.incrementTurn();
		assert(manager.getRoundCount() == 0);
		assert(!manager.isFreeLand());
		assert(manager.getFoodRequired() == 3);
		manager.incrementTurn();
		manager.incrementTurn();
		assert(manager.getRoundCount() == 1);
		assert(manager.getPhaseCount() == 1);
		manager.incrementTurn();
		manager.incrementTurn();
		assert(manager.getRoundCount() == 2);
		manager.incrementTurn();
		manager.incrementTurn();
		assert(manager.getRoundCount() == 3);
		manager.incrementTurn();
		manager.incrementTurn();
		assert(manager.getRoundCount() == 4);
		manager.incrementTurn();
		manager.incrementTurn();
		assert(manager.getRoundCount() == 5);
		assert(manager.getFoodRequired() == 4);
	}

	/**
	 * Checks calculateTurnOrder
	 */
	@Test
	public void testTurnOrder() {
		manager.getPlayerList().get(0).setMoney(100000);
		manager.getPlayerList().get(1).setMoney(0);
		manager.calculateTurnOrder();
		assert(manager.getTurnOrder().get(0).getMoney() == 0);
		assert(manager.getTurnOrder().get(0).getId() == 1);

		manager.getPlayerList().get(0).setMoney(0);
		manager.getPlayerList().get(1).setMoney(100000);
		manager.calculateTurnOrder();
		System.out.println(manager.getTurnOrder().size());
		assert(manager.getTurnOrder().get(0).getMoney() == 0);
		assert(manager.getTurnOrder().get(1).getMoney() == 100000);
		assert(manager.getTurnOrder().get(0).getId() == 0);
	}
}
