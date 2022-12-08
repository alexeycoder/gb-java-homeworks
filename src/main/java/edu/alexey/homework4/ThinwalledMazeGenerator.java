package edu.alexey.homework4;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class ThinwalledMazeGenerator extends MazeUtilsBase {
	private final Deque<Pos> stack;
	private final Random rnd;

	public ThinwalledMazeGenerator(MazeBaze labyrinth) {
		super(labyrinth);
		this.stack = new ArrayDeque<>();
		this.rnd = new Random();
	}

	public void generate() {
		labyrinth.populateWalledCells();
		Pos startCoords = new Pos(1, 1);
		// Pos startCoords = new Pos(labyrinth.cols() / 2, labyrinth.rows() / 2);
		makeVisited(startCoords);
		stack.push(startCoords);

		var current = startCoords;
		Pos next;
		while (stack.size() > 0) {
			next = getRandomUnvisitedNeigbor(current);
			if (next != null) {
				stack.push(next);
				labyrinth.removeWallBetween(next.col(), next.row(), current.col(), current.row());
				makeVisited(next);
				current = next;
			} else {
				current = stack.pop();
			}
		}
	}

	private Pos getRandomUnvisitedNeigbor(Pos p) {
		var unvisited = getUnvisited(p);
		int size = unvisited.size();
		if (size == 0) {
			return null;
		}
		if (size == 1) {
			return unvisited.get(0);
		}
		int i = rnd.nextInt(size);
		return unvisited.get(i);
	}

	public void breakSomeWalls() {
		int colMin = 2;
		int colMaxExcl = labyrinth.cols() - 1;
		int rowMin = 2;
		int rowMaxExcl = labyrinth.rows() - 1;
		if (colMaxExcl <= colMin || rowMaxExcl <= rowMin) {
			return;
		}
		int cellsCount = (colMaxExcl - 1 - colMin) * (rowMaxExcl - 1 - rowMin);
		var dirs = Direction.values();
		Random rnd = new Random();
		for (int i = 0; i < cellsCount; ++i) {
			boolean proceed = rnd.nextBoolean();
			if (proceed) {
				int col = rnd.nextInt(colMin, colMaxExcl);
				int row = rnd.nextInt(rowMin, rowMaxExcl);
				int dirId = rnd.nextInt(dirs.length);
				labyrinth.removeWall(col, row, dirs[dirId]);
			}
		}
	}

	// private void makeMoreWaysV2() {
	// int divFactor = 10;
	// int colMin = 2;
	// int colMaxExcl = labyrinth.cols() - 1;
	// int rowMin = 2;
	// int rowMaxExcl = labyrinth.rows() - 1;
	// int cellsCount = (colMaxExcl - 1 - colMin) * (rowMaxExcl - 1 - rowMin);
	// if (cellsCount <= 0) {
	// return;
	// }
	// Random rnd = new Random();
	// for (int i = 0; i < cellsCount / divFactor; ++i) {
	// boolean proceed = rnd.nextBoolean();
	// if (proceed) {
	// int col = rnd.nextInt(colMin, colMaxExcl);
	// int row = rnd.nextInt(rowMin, rowMaxExcl);
	// for (Direction dir : Direction.values()) {
	// labyrinth.removeWall(col, row, dir);
	// }
	// }
	// }
	// }
}
