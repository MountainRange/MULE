package io.github.mountainrange.mule.gameplay;

import io.github.mountainrange.mule.enums.MapSize;
import io.github.mountainrange.mule.enums.MapType;
import io.github.mountainrange.mule.enums.TerrainType;

import java.util.Iterator;

/**
 * A class to represent the map and facilitates interactions
 * between the actual data store and the rest of the program
 */
public class WorldMap implements Iterable<Tile> {

	private Grid map;

	public WorldMap(Grid g) {
		this.map = g;
	}

	public boolean select(int x, int y) {
		if (x < 0 || y < 0 || x >= map.getCols() || y >= map.getRows()) {
			System.out.println("Cannot select off map");
			return false;
		}
		map.select(x, y);
		return true;
	}

	public boolean selectRel(int xmove, int ymove) {
		int newposx = map.getCursorX() + xmove;
		int newposy = map.getCursorY() + ymove;
		if (newposx < 0 || newposy < 0 || newposx >= map.getCols()
				|| newposy >= map.getRows()) {
			System.out.println("Cannot select off map");
			return false;
		}
		map.select(newposx, newposy);
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
		int newposx = map.getCursorX() - 1;
		int newposy = map.getCursorY() - 1;
		if (newposx < 0 && newposy < 0) {
			return select(map.getCols() - 1, newposy);
		}
		return selectRel(1, 0);
	}

	public boolean selectRightWrap() {
		int newposx = map.getCursorX() + 1;
		int newposy = map.getCursorY() + 1;
		if (newposx >= map.getCols()) {
			return select(0, newposy % map.getRows());
		}
		return selectRel(1, 0);
	}

	public boolean selectLeft() {
		return selectRel(-1, 0);
	}

	public int height() {
		return map.getRows();
	}

	public int width() {
		return map.getCols();
	}

	public void buyTile(Player player) {
		int x = map.getCursorX();
		int y = map.getCursorY();
		Tile t = map.get(x, y);
		if (!t.hasOwner()) {
			t.setOwner(player);
		}
	}

	public Player getOwner() {
		int x = map.getCursorX();
		int y = map.getCursorY();
		return map.get(x, y).getOwner();
	}

	@Override
	public Iterator<Tile> iterator() {
		return map.iterator();
	}

}
