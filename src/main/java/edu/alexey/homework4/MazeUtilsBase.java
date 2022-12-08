package edu.alexey.homework4;

import java.util.List;

public abstract class MazeUtilsBase {

	protected final MazeBaze labyrinth;
	protected final boolean[] visited;

	public static record Pos(int col, int row) {
	}

	public MazeUtilsBase(MazeBaze labyrinth) {
		this.labyrinth = labyrinth;
		this.visited = new boolean[labyrinth.cells()];
	}

	protected List<Pos> getReachable(Pos p) {
		var neighbors = getNeighbors(p);
		var reachable = neighbors.stream()
				.filter(pos -> !labyrinth.isThereWallBetween(p.col, p.row, pos.col, pos.row)).toList();
		return reachable;
	}

	protected List<Pos> getReachableUnvisited(Pos p) {
		var neighbors = getNeighbors(p);
		var reachable = neighbors.stream()
				.filter(pos -> !isVisited(pos) && !labyrinth.isThereWallBetween(p.col, p.row, pos.col, pos.row)).toList();
		return reachable;
	}

	protected List<Pos> getUnvisited(Pos p) {
		var neighbors = getNeighbors(p);
		var unvisited = neighbors.stream().filter(pos -> !isVisited(pos)).toList();
		return unvisited;
	}

	protected List<Pos> getNeighbors(Pos p) {
		int x = p.col;
		int y = p.row;
		var all = List.of(
				new Pos(x, y + 1),
				new Pos(x + 1, y),
				new Pos(x, y - 1),
				new Pos(x - 1, y));
		var valid = all.stream().filter(pos -> eligiblePos(pos)).toList();
		return valid;
	}

	protected void makeVisited(Pos p) {
		assert eligiblePos(p);
		int i = toIndex(p);
		visited[i] = true;
	}

	protected boolean isVisited(Pos p) {
		assert eligiblePos(p);
		int i = toIndex(p);
		return visited[i];
	}

	protected int toIndex(Pos p) {
		return labyrinth.cellIndex(p.col, p.row);
	}

	protected boolean eligiblePos(Pos p) {
		return p.col > 0 && p.row > 0 && labyrinth.validCoords(p.col, p.row);
	}
}
