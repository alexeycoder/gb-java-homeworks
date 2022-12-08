package edu.alexey.homework4;

import java.util.List;

public class ThinwalledMaze extends MazeBaze {
	

	public ThinwalledMaze(int cols, int rows) {
		super(cols, rows);
	}

	@Override
	public void populateWalledCells() {
		int flags = Direction.BOTTOM.flag | Direction.RIGHT.flag;
		for (int i = 0; i < field.length; ++i) {
			field[i] = flags;
		}
		field[0] = 0;
		for (int col = 1; col < cols; ++col) {
			field[col] = Direction.BOTTOM.flag;
		}
		for (int row = 1; row < rows; ++row) {
			field[row * cols] = Direction.RIGHT.flag;
		}
	}

	@Override
	public List<Direction> getWalls(int col, int row) {
		int cell = getCellValue(col, row);
		if (cell < 0) {
			return null;
		}

		int above = getCellValue(col, row - 1);
		if (above < 0 || Direction.BOTTOM.isFlagged(above)) {
			above = Direction.TOP.flag;
		} else {
			above = 0;
		}

		int leftward = getCellValue(col - 1, row);
		if (leftward < 0 || Direction.RIGHT.isFlagged(leftward)) {
			leftward = Direction.LEFT.flag;
		} else {
			leftward = 0;
		}

		return Direction.getDirectionsList(cell | above | leftward);
	}

	@Override
	public void removeWallBetween(int col, int row, int col2, int row2) {
		if (col == col2 && row == row2) {
			int i = cellIndex(col, row);
			if (i >= 0) {
				field[i] = 0;
			}
		} else if (col == col2) {
			if (row > row2) {
				removeWall(col, row, Direction.TOP);
			} else {
				removeWall(col, row, Direction.BOTTOM);
			}
		} else if (row == row2) {
			if (col > col2) {
				removeWall(col, row, Direction.LEFT);
			} else {
				removeWall(col, row, Direction.RIGHT);
			}
		} else {
			throw new AssertionError();
		}
	}

	@Override
	public boolean isThereWallBetween(int col, int row, int col2, int row2) {
		assert validCoords(col, row) && validCoords(col2, row2);

		if (col == col2 && row == row2) {
			return false;
		} else if (col == col2) {
			if (row > row2) { // above
				int iAbove = cellIndex(col2, row2);
				return Direction.BOTTOM.isFlagged(field[iAbove]);
			} else {
				int i = cellIndex(col, row);
				return Direction.BOTTOM.isFlagged(field[i]);
			}
		} else if (row == row2) {
			if (col > col2) {
				int iLeftward = cellIndex(col2, row2);
				return Direction.RIGHT.isFlagged(field[iLeftward]);
			} else {
				int i = cellIndex(col, row);
				return Direction.RIGHT.isFlagged(field[i]);
			}
		} else {
			throw new AssertionError();
		}
	}

	@Override
	public void removeWall(int col, int row, Direction wall) {
		if (wall.equals(Direction.NONE)) {
			return;
		} else if (wall.equals(Direction.BOTTOM) || wall.equals(Direction.RIGHT)) {
			int i = cellIndex(col, row);
			if (i >= 0) {
				field[i] &= ~wall.flag;
			}
		} else if (wall.equals(Direction.TOP)) {
			int iAbove = cellIndex(col, row - 1);
			if (iAbove >= 0) {
				field[iAbove] &= ~Direction.BOTTOM.flag;
			}
		} else if (wall.equals(Direction.LEFT)) {
			int iLeftward = cellIndex(col - 1, row);
			if (iLeftward >= 0) {
				field[iLeftward] &= ~Direction.RIGHT.flag;
			}
		}
	}

	

	// public void setWalls(int x, int y, List<Direction> walls) {
	// int i = toIndex(x, y);
	// if (i < 0) {
	// return;
	// }

	// field[i] = 0;
	// if (walls.contains(Direction.BOTTOM)) {
	// field[i] += Direction.BOTTOM.flag;
	// }
	// if (walls.contains(Direction.RIGHT)) {
	// field[i] += Direction.RIGHT.flag;
	// }

	// int iAbove = toIndex(x, y - 1);
	// if (iAbove >= 0) {
	// if (walls.contains(Direction.TOP)) {
	// field[iAbove] |= Direction.BOTTOM.flag; // set
	// } else {
	// field[iAbove] &= ~Direction.BOTTOM.flag; // unset
	// }
	// }

	// int iLeftward = toIndex(x - 1, y);
	// if (iLeftward >= 0) {
	// if (walls.contains(Direction.LEFT)) {
	// field[iLeftward] |= Direction.RIGHT.flag;
	// } else {
	// field[iLeftward] &= ~Direction.RIGHT.flag;
	// }
	// }
	// // flags = walls.stream().map(d->d.flag).reduce(Integer::sum).get();
	// }
}
