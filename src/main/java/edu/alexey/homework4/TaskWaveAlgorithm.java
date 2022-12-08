package edu.alexey.homework4;

import java.util.Scanner;

import edu.alexey.utils.BackColors;
import edu.alexey.utils.Console;
import edu.alexey.utils.ForeColors;
import edu.alexey.utils.Console.TextStyle;

public class TaskWaveAlgorithm {

	private static final int MAX_COLS = 80;
	private static final int MAX_ROWS = 80;

	private static final TextStyle INPUT_STYLE = new TextStyle(false, true, false, ForeColors.BRIGHT_WHITE,
			BackColors.UNSPECIFIED);

	public static void execute(Scanner scanner) {
		do {
			Console.clearScreen();
			Console.printTitle("Демонстрация работы волнового алгоритма поиска кратчайшего пути."
					+ "\n(Поиск пути ведётся из верхней правой в нижнюю левую ячейку лабиринта.)",
					ForeColors.BRIGHT_CYAN);

			int cols = Console.getUserInputIntRange(scanner,
					String.format("Задайте размер лабиринта по горизонтали (целое число от 2 до %d): ", MAX_COLS),
					2, MAX_COLS, INPUT_STYLE) + 1;
			int rows = Console.getUserInputIntRange(scanner,
					String.format("Задайте размер лабиринта по вертикали (целое число от 2 до %d): ", MAX_ROWS),
					2, MAX_ROWS, INPUT_STYLE) + 1;

			ThinwalledMaze labyrinth = new ThinwalledMaze(cols, rows);
			ThinwalledMazeGenerator generator = new ThinwalledMazeGenerator(labyrinth);
			generator.generate();
			generator.breakSomeWalls();
			ThinwalledMazePathfinder pathFinder = new ThinwalledMazePathfinder(labyrinth, 1, 1, labyrinth.cols() - 1,
					labyrinth.rows() - 1);
			pathFinder.evaluate();

			if (pathFinder.foundPathsCount() > 0) {
				ThinwalledMazeRenderer renderer = new ThinwalledMazeRenderer(labyrinth, pathFinder);
				renderer.plot();
			} else {
				System.out.println("В данном лабиринте из данной стартовой ячейки финишная ячейка не достижима.");
			}
		} while (Console.askYesNo(scanner, "\nЖелаете повторить (Y/n)? ", true));
	}

}
