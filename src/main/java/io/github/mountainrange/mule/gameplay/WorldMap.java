package io.github.mountainrange.mule.gameplay;

import io.github.mountainrange.mule.enums.MapType;
import io.github.mountainrange.mule.enums.MessageType;
import io.github.mountainrange.mule.enums.MuleType;
import javafx.geometry.Point2D;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A class to represent the map and facilitates interactions between the actual data store and the rest of the program.
 */
public class WorldMap<T extends Tile> implements Iterable<Tile> {

	/** Backing instance of Grid. */
	private Grid<T> baseGrid;

	/** List of tiles owned by each player, sorted by the MULE installed on them. */
	private Map<Player, EnumMap<MuleType, Set<Tile>>> productionTiles;

	public WorldMap(Grid<T> g, MapType mType) {
		this.baseGrid = g;

		productionTiles = new HashMap<>();
	}

	public WorldMap(Grid<T> g, MapType mType, List<Player> playerList) {
		this(g, mType);

		productionTiles = new HashMap<>();
		for (Player p : playerList) {
			productionTiles.put(p, new EnumMap<>(MuleType.class));
		}
	}

	// ----------------------------Logical methods-------------------------------

	/**
	 * Count the number of grid owned by the given player, or zero if the given player owns no tiles.
	 * @param player player to count owned tiles
	 * @return number of grid owned by the given player
	 */
	public int countLandOwnedBy(Player player) {
		if (!productionTiles.containsKey(player)) {
			return 0;
		}

		// Count all the tiles in the individual Sets owned by the given player
		return productionTiles.get(player).values().stream().mapToInt(Set::size).sum();
	}

	/**
	 * Return an unmodifiable set with the grid owned by the given player, or an empty set if the given player owns no
	 * tiles.
	 * @param player player to get owned tiles
	 * @return unmodifiable set with grid owned by the given player
	 */
	public Set<Tile> landOwnedBy(Player player) {
		if (!productionTiles.containsKey(player)) {
			return Collections.emptySet();
		}

		// Construct a flat Set of all the Tiles a given player owns
		return productionTiles.get(player).values().stream()
				.flatMap(Set::stream)
				.collect(Collectors.toCollection(HashSet::new));
	}

	/**
	 * Count the number of tiles owned by the given player with the given MULE installed.
	 * @param player player who owns the tiles
	 * @param mule mule installed on the tiles
	 * @return number of tiles that the player owns with the mule installed
	 */
	public int countTilesWithMule(Player player, MuleType mule) {
		if (!productionTiles.containsKey(player)) {
			return 0;
		}
		return productionTiles.get(player).get(mule).size();
	}

	/**
	 * Return an unmodifiable set with all the tiles a player owns with the given mule installed.
	 * @param player player who owns the tiles
	 * @param mule mule installed on the tiles
	 * @return unmodifiable set of tiles with the given mule installed and owned by the given player
	 */
	public Set<Tile> tilesWithMule(Player player, MuleType mule) {
		if (!productionTiles.containsKey(player)) {
			return Collections.emptySet();
		}
		return Collections.unmodifiableSet(productionTiles.get(player).get(mule));
	}

	/**
	 * Sell the given tile to the given player. Selling will fail if the tile is already owned.
	 * @param player player to sell the tile to
	 * @param tile tile to sell
	 * @return whether the tile was actually sold
	 */
	public boolean sellTile(Player player, Tile tile) {
		if (tile.hasOwner()) {
			return false;
		}

		if (!productionTiles.containsKey(player)) {
			// Adding a new player
			EnumMap<MuleType, Set<Tile>> playerProductionTiles = new EnumMap<>(MuleType.class);

			for (MuleType muleType : MuleType.values()) {
				playerProductionTiles.put(muleType, new HashSet<>());
			}

			productionTiles.put(player, playerProductionTiles);
		}

		tile.setOwner(player);

		productionTiles.get(player).get(MuleType.EMPTY).add(tile);
		return true;
	}

	/**
	 * Calls the backing {@code Grid} to display a custom message on screen.
	 * @param msg message to display
	 */
	public void showCustomText(String msg) {
		baseGrid.printText(msg);
	}

	/**
	 * Calls the backing {@code Grid} to display a message on screen.
	 * @param msg message to display
	 */
	public void showText(MessageType msg) {
		baseGrid.printText(msg.getMsg());
	}

	/**
	 * Calls the backing {@code Grid} to clear the displayed text.
	 */
	public void clearText() {
		baseGrid.clearText();
	}

	/**
	 * Places a MULE for the given player on the given tile. Placing fails if the given player does not actually own the
	 * given tile, the player is not carrying a MULE, or the player is carrying a MULE that has not been outfitted.
	 * @param player player to get mule from
	 * @param tile tile to set mule
	 * @return whether the mule was placed
	 */
	public boolean placeMule(Player player, Tile tile) {
		if (tile.getOwner() != player || !player.hasMule() || player.getMule() == MuleType.EMPTY) {
			return false;
		}

		MuleType previousMule = tile.getMule();
		MuleType newMule = player.getMule();

		// Place the MULE in the actual tile
		tile.setMule(newMule);
		tile.setMuleDraw(newMule);
		// Take the MULE away from the player
		player.setMule(null);

		// Take tile out of its old set into its new one, based on MULE installed
		Map<MuleType, Set<Tile>> playerProductionTiles = productionTiles.get(player);
		playerProductionTiles.get(previousMule).remove(tile);
		playerProductionTiles.get(newMule).add(tile);
		return true;
	}

	@Override
	public Iterator<Tile> iterator() {
		return baseGrid.iterator();
	}

	/**
	 * Gets the number of columns in this baseGrid
	 * @return cols the number of columns in this baseGrid
	 */
	public int getColumns() {
		return baseGrid.getCols();
	}

	/**
	 * Gets the number of rows in this baseGrid
	 * @return rows the number of rows in this baseGrid
	 */
	public int getRows() {
		return baseGrid.getRows();
	}

	// ----------------------------Graphical methods-----------------------------

	/**
	 * Get the tile the cursor is currently on.
	 * @return the tile the cursor is on
	 * @deprecated This is bad style.
	 */
	@SuppressWarnings("deprecated")
	public Tile cursorTile() {
		int x = baseGrid.getCursorX();
		int y = baseGrid.getCursorY();
		return baseGrid.get(x, y);
	}

	/**
	 * Sell the tile at the cursor location to the given player. Selling will fail if the tile is already owned.
	 * @param player player to sell the tile to
	 * @return whether the tile was actually sold
	 */
	public boolean sellTile(Player player) {
		Tile tile = cursorTile();
		return sellTile(player, tile);
	}

	/**
	 * Places a MULE for the given player on the currently selected tile. Placing fails if the given player does not
	 * actually own the given tile, the player is not carrying a MULE, or the player is carrying a MULE that has not
	 * been outfitted.
	 * @param player player to get mule from
	 * @return whether the mule was placed
	 */
	public boolean placeMule(Player player) {
		Tile tile = cursorTile();
		return placeMule(player, tile);
	}

	public boolean select(int x, int y) {
		if (x < 0 || y < 0 || x >= baseGrid.getCols() || y >= baseGrid.getRows()) {
			System.out.println("Cannot select off baseGrid");
			return false;
		}
		baseGrid.select(x, y);
		return true;
	}

	public boolean selectRel(int xmove, int ymove) {
		int newposx = baseGrid.getCursorX() + xmove;
		int newposy = baseGrid.getCursorY() + ymove;
		if (newposx < 0) {
			newposx = baseGrid.getCols() - 1;
		} else if (newposy < 0) {
			newposy = baseGrid.getRows() - 1;
		} else if (newposx >= baseGrid.getCols()) {
			newposx = 0;
		} else if (newposy >= baseGrid.getRows()) {
			newposy = 0;
		}
		baseGrid.select(newposx, newposy);
		return true;
	}

	public boolean selectUp() {
		return selectRel(0, -1);
	}

	public boolean selectDown() {
		return selectRel(0, 1);
	}

	public boolean selectRight() {
		return selectRel(1, 0);
	}

	public boolean selectLeftWrap() {
		int newposx = baseGrid.getCursorX() - 1;
		int newposy = baseGrid.getCursorY() - 1;
		if (newposx < 0 && newposy < 0) {
			return select(baseGrid.getCols() - 1, newposy);
		}
		return selectRel(1, 0);
	}

	public boolean selectRightWrap() {
		int newposx = baseGrid.getCursorX() + 1;
		int newposy = baseGrid.getCursorY() + 1;
		if (newposx >= baseGrid.getCols()) {
			return select(0, newposy % baseGrid.getRows());
		}
		return selectRel(1, 0);
	}

	public boolean selectLeft() {
		return selectRel(-1, 0);
	}

	public Player getOwner() {
		return cursorTile().getOwner();
	}

	public boolean isInside(Point2D toCheck, int column, int row) {
		return baseGrid.isInside(toCheck, column, row);
	}

}
