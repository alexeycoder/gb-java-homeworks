package edu.alexey.homework4;

import java.util.ArrayList;
import java.util.List;

public class ThinwalledMazePathfinder extends MazeUtilsBase {
	private final int[] waveNums;
	private final Pos start;
	private final Pos target;
	// private final Deque<List<Pos>> stack;

	private final List<List<Pos>> paths;

	public ThinwalledMazePathfinder(ThinwalledMaze labyrinth, int colStart, int rowStart, int colTarget,
			int rowTarget) {
		super(labyrinth);
		this.waveNums = new int[labyrinth.cells()];
		this.start = new Pos(colStart, rowStart);
		this.target = new Pos(colTarget, rowTarget);
		this.paths = new ArrayList<>();
		// this.stack = new ArrayDeque<>();
	}

	public int foundPathsCount() {
		return paths.size();
	}

	public int getWaveNum(int col, int row) {
		if (labyrinth.validCoords(col, row)) {
			int i = labyrinth.cellIndex(col, row);
			return waveNums[i];
		}
		return 0;
	}

	public int getReachStep() {
		if (eligiblePos(target)) {
			return 0;
		}
		return waveNums[toIndex(target)];
	}

	public int belongsToPath(int col, int row) {
		Pos p = new Pos(col, row);
		for (var posList : paths) {
			int i = 0;
			for (Pos pos : posList) {
				if (p.equals(pos)) {
					return i;
				}
				++i;
			}
		}
		return -1;
	}

	public boolean isStart(int col, int row) {
		return col == start.col() && row == start.row();
	}

	public boolean isFinish(int col, int row) {
		return col == target.col() && row == target.row();
	}

	public void evaluate() {
		List<Pos> currentWave = List.of(start);
		int waveNum = 0;

		while (currentWave.size() > 0) {

			++waveNum;
			markSteps(currentWave, waveNum);
			makeVisited(currentWave);
			// stack.push(currentWave);

			if (currentWave.contains(target)) {
				break;
			}

			ArrayList<Pos> nextWave = new ArrayList<Pos>();
			for (Pos wavePos : currentWave) {
				List<Pos> unvisitedNeighbors = getReachableUnvisited(wavePos);
				if (unvisitedNeighbors.size() > 0) {
					nextWave.addAll(unvisitedNeighbors);
				}
			}
			currentWave = nextWave;
		}

		List<Pos> steps = List.of(target);

		while (waveNum > 1 && steps.size() > 0) {
			this.paths.add(steps);
			--waveNum;
			ArrayList<Pos> allPriorSteps = new ArrayList<>();
			for (Pos step : steps) {
				var priorSteps = getPriorSteps(step);
				if (priorSteps.size() > 0) {
					allPriorSteps.addAll(priorSteps);
					// break;
				}
			}
			steps = allPriorSteps;
		}

	}

	// public void printBack() {
	// stack.pop();
	// int waveNum = getReachStep();
	// while (stack.size() > 0) {
	// var wave = stack.pop();
	// --waveNum;
	// System.out.println("=== " + waveNum + " ===");
	// for (Pos pos : wave) {
	// System.out.printf("(%d,%d): %d ", pos.x(), pos.y(), waveNums[toIndex(pos)]);
	// }
	// System.out.println();
	// }
	// }

	private List<Pos> getPriorSteps(Pos p) {
		int priorWaveNum = waveNums[toIndex(p)] - 1;
		var priors = getReachable(p);
		return priors.stream().filter(pos -> waveMatches(pos, priorWaveNum)).toList();
	}

	private boolean waveMatches(Pos p, int waveNum) {
		return waveNums[toIndex(p)] == waveNum;
	}

	private void makeVisited(List<Pos> stepPositions) {
		for (Pos pos : stepPositions) {
			makeVisited(pos);
		}
	}

	private void markSteps(List<Pos> stepPositions, int waveNum) {
		for (Pos pos : stepPositions) {
			int i = toIndex(pos);
			waveNums[i] = waveNum;
		}
	}
}
