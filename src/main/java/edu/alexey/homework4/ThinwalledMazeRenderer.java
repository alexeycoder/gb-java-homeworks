package edu.alexey.homework4;

import edu.alexey.utils.BackColors;
import edu.alexey.utils.Console;
import edu.alexey.utils.Console.TextStyle;
import edu.alexey.utils.ForeColors;
import edu.alexey.utils.StringUtils;

public class ThinwalledMazeRenderer {
	private final TextStyle[] PATH_STYLES = new TextStyle[] {
			new TextStyle(true, false, false, ForeColors.UNSPECIFIED, BackColors.BRIGHT_YELLOW),
			new TextStyle(true, false, false, ForeColors.UNSPECIFIED, BackColors.BRIGHT_GREEN),
			new TextStyle(true, false, false, ForeColors.UNSPECIFIED, BackColors.BRIGHT_BLUE),
			new TextStyle(true, false, false, ForeColors.UNSPECIFIED, BackColors.BRIGHT_MAGENTA),
			new TextStyle(true, false, false, ForeColors.UNSPECIFIED, BackColors.BRIGHT_CYAN),
			new TextStyle(true, false, false, ForeColors.UNSPECIFIED, BackColors.BRIGHT_RED),
	};
	private final TextStyle START_STYLE = new TextStyle(true, true, false, ForeColors.BLACK, BackColors.BRIGHT_WHITE);
	private final TextStyle FINISH_STYLE = new TextStyle(true, true, false, ForeColors.BLACK, BackColors.BRIGHT_WHITE);
	private final ThinwalledMaze labyrinth;
	private final ThinwalledMazePathfinder pathFinder;

	public ThinwalledMazeRenderer(ThinwalledMaze labyrinth, ThinwalledMazePathfinder pathFinder) {
		this.labyrinth = labyrinth;
		this.pathFinder = pathFinder;
	}

	public void plot() {
		final String vertWall = "┃";
		final String horizWall = "━━━";
		final String vertSpace = " ";
		final String horizSpace = "   ";

		StringBuilder sbLabyrinth = new StringBuilder();

		for (int row = 0; row < labyrinth.rows(); ++row) {
			StringBuilder midLine = new StringBuilder();
			StringBuilder btmLine = new StringBuilder();
			for (int col = 0; col < labyrinth.cols(); ++col) {
				var walls = labyrinth.getWalls(col, row);
				assert walls != null;

				String cellContent = getCellContent(col, row);// horizSpace;
				cellContent = Console.styleText(pickStyle(col, row), true, cellContent);
				midLine.append(cellContent)
						.append(walls.contains(Direction.RIGHT) ? vertWall : vertSpace);
				btmLine.append(walls.contains(Direction.BOTTOM) ? horizWall : horizSpace)
						.append(getBottomRightCorner(col, row));
			}
			sbLabyrinth.append(midLine).append('\n').append(btmLine).append('\n');
		}

		System.out.println(sbLabyrinth.toString());
	}

	private TextStyle pickStyle(int col, int row) {
		if (pathFinder.isStart(col, row)) {
			return START_STYLE;
		}
		if (pathFinder.isFinish(col, row)) {
			return FINISH_STYLE;
		}
		int pathId = pathFinder.belongsToPath(col, row);
		if (pathId >= 0) {
			return PATH_STYLES[pathId % PATH_STYLES.length];
		}
		return null;
	}

	private String getCellContent(int col, int row) {
		assert labyrinth.validCoords(col, row);
		int num = pathFinder.getWaveNum(col, row);
		if (num == 0) {
			return "   ";
		}
		return StringUtils.padCenter(Integer.toString(num), " ", 3);
	}

	private char getBottomRightCorner(int x, int y) {
		assert labyrinth.validCoords(x, y);

		var wallsAt = labyrinth.getWalls(x, y);
		boolean horizIn = wallsAt.contains(Direction.BOTTOM);
		boolean vertIn = wallsAt.contains(Direction.RIGHT);

		var wallsRighthand = labyrinth.getWalls(x + 1, y);
		boolean horizOut = wallsRighthand != null && wallsRighthand.contains(Direction.BOTTOM);

		var wallsBelow = labyrinth.getWalls(x, y + 1);
		boolean vertOut = wallsBelow != null && wallsBelow.contains(Direction.RIGHT);

		if (horizIn && vertIn) {
			if (horizOut && vertOut) {
				return '╋';
			}
			if (horizOut) {
				return '┻';
			}
			if (vertOut) {
				return '┫';
			}
			return '┛';
		}

		if (horizIn && !vertIn) {
			if (horizOut && vertOut) {
				return '┳';
			}
			if (horizOut) {
				return '━';
			}
			if (vertOut) {
				return '┓';
			}
			return '╸';
		}

		if (!horizIn && vertIn) {
			if (horizOut && vertOut) {
				return '┣';
			}
			if (horizOut) {
				return '┗';
			}
			if (vertOut) {
				return '┃';
			}
			return '╹';
		}

		if (horizOut && vertOut) {
			return '┏';
		}
		if (horizOut) {
			return '╺';
		}
		if (vertOut) {
			return '╻';
		}
		return ' ';
	}
}
