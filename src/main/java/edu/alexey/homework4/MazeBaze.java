package edu.alexey.homework4;

import java.util.List;

public abstract class MazeBaze {
	// int cells();
	// int cols();
	// int rows();
	// boolean validCoords(int col, int row);
	// int cellIndex(int col, int row);
	protected final int[] field;
	protected final int cols;
	protected final int rows;

	public MazeBaze(int cols, int rows) {
		if (cols < 0 || rows < 0) {
			throw new IllegalArgumentException();
		}
		this.cols = cols;
		this.rows = rows;
		this.field = new int[cols * rows];
		// this.cols = 5;
		// this.rows = 5;
		// this.field = new int[] {
		// 0, 1, 1, 1, 0,
		// 2, 2, 0, 2, 0,
		// 2, 2, 1, 2, 0,
		// 2, 1, 3, 3, 0,
		// 0, 0, 0, 0, 0
		// };
	}

	public int cells() {
		return field.length;
	}

	public int cols() {
		return cols;
	}

	public int rows() {
		return rows;
	}

	public boolean validCoords(int col, int row) {
		return col >= 0 && row >= 0 && col < cols && row < rows;
	}

	public int cellIndex(int col, int row) {
		if (validCoords(col, row)) {
			return col + row * cols;
		}
		return -1;
	}

	protected int getCellValue(int col, int row) {
		int i = cellIndex(col, row);
		if (i < 0) {
			return -1;
		}
		return field[i];
	}

	abstract void populateWalledCells();

	abstract List<Direction> getWalls(int col, int row);

	abstract void removeWallBetween(int col, int row, int col2, int row2);

	abstract boolean isThereWallBetween(int col, int row, int col2, int row2);

	abstract void removeWall(int col, int row, Direction wall);
}
