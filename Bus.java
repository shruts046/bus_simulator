/*
 * Copyright 2017 Marc Liberatore.
 */

package simulator;

public class Bus {
	public final int number;
	private final RoadMap roadMap;
	private int x;
	private int y;
	public String direct = "";
	public String mot = "STOPPED";

	public Bus(int number, RoadMap roadMap, int x, int y) {
		this.number = number;
		this.roadMap = roadMap;
		this.x = x;
		this.y = y;

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
	 * Move the bus. Buses only move along the cardinal directions
	 * (north/south/east/west), not diagonally.
	 * 
	 * If the bus is stopped (that is, if it was just placed, or if it didn't
	 * move last time move() was called), then it should attempt to move north.
	 * If it cannot (no road, or off the map), then it should attempt south,
	 * then east, then west. If no move is available, it should stay in its
	 * current position.
	 * 
	 * If the bus is moving (that is, if it successfully moved the last time
	 * move() was called), then it should attempt to continue moving in the same
	 * direction.
	 * 
	 * If it cannot (no road, or off the map), then it should attempt to turn
	 * right. For example, if the bus was moving north, but there is no more
	 * road to the north, it should move east if possible.
	 * 
	 * If it cannot turn right, it should turn left. If it cannot turn left, it
	 * should reverse direction (that is, move backward, if possible).
	 * If it cannot do any of these things, it should stay in its current position.
	 */
	public void move() {
		if (mot.equals("STOPPED")) {
			if (roadMap.isRoad(x, y - 1)) {
				y--;
				direct = "NORTH";
				mot = "MOVING";
			} else if (roadMap.isRoad(x, y + 1)) {
				y++;
				direct = "SOUTH";
				mot = "MOVING";
			} else if (roadMap.isRoad(x + 1, y)) {
				x++;
				direct = "EAST";
				mot = "MOVING";
			} else if (roadMap.isRoad(x - 1, y)) {
				x--;
				direct = "WEST";
				mot = "MOVING";
			} else {
				mot = "STOPPED";
			}
		}

		else if (mot.equals("MOVING")) {

			if (direct.equals("NORTH")) {

				if (roadMap.isRoad(x, y - 1)) {
					y--;
					direct = "NORTH";
				} else if (roadMap.isRoad(x + 1, y)) {
					x++;
					direct = "EAST";
				} else if (roadMap.isRoad(x - 1, y)) {
					x--;
					direct = "WEST";
				} else if (roadMap.isRoad(x, y + 1)) {
					y++;
					direct = "SOUTH";
				} else {
					mot = "STOPPED";
				}
			}

			else if (direct.equals("SOUTH")) {

				if (roadMap.isRoad(x, y + 1)) {
					y++;
					direct = "SOUTH";
				} else if (roadMap.isRoad(x - 1, y)) {
					x--;
					direct = "WEST";
				} else if (roadMap.isRoad(x + 1, y)) {
					x++;
					direct = "EAST";
				} else if (roadMap.isRoad(x, y - 1)) {
					y--;
					direct = "NORTH";
				} else {
					mot = "STOPPED";
				}
			} else if (direct.equals("EAST")) {

				if (roadMap.isRoad(x + 1, y)) {
					x++;
					direct = "EAST";
				} else if (roadMap.isRoad(x, y + 1)) {
					y++;
					direct = "SOUTH";
				} else if (roadMap.isRoad(x, y - 1)) {
					y--;
					direct = "NORTH";
				} else if (roadMap.isRoad(x - 1, y)) {
					x--;
					direct = "WEST";
				} else {
					mot = "STOPPED";
				}

			} else if (direct.equals("WEST")) {

				if (roadMap.isRoad(x - 1, y)) {
					x--;
					direct = "WEST";
				} else if (roadMap.isRoad(x, y - 1)) {
					y--;
					direct = "NORTH";
				} else if (roadMap.isRoad(x, y + 1)) {
					y++;
					direct = "SOUTH";
				} else if (roadMap.isRoad(x + 1, y)) {
					x++;
					direct = "EAST";
				} else {
					mot = "STOPPED";
				}
			}
		}
	}
}
