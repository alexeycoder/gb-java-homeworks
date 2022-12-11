package edu.alexey.homework5;

import java.util.Scanner;

import edu.alexey.utils.BackColors;
import edu.alexey.utils.Console;
import edu.alexey.utils.Console.TextStyle;
import edu.alexey.utils.ForeColors;

public class TaskTriangularNumber {
	private static final String SYMBOL = "\u25CF ";
	private static final TextStyle INPUT_STYLE = new TextStyle(true, false, false, ForeColors.BRIGHT_BLUE,
			BackColors.UNSPECIFIED);
	private static final TextStyle OUTPUT_STYLE = new TextStyle(true, false, false, ForeColors.YELLOW,
			BackColors.UNSPECIFIED);

	private static int calcTriangularRecursively(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}
		if (n == 0) {
			return 0; // 0th triangular number
		}
		return n + calcTriangularRecursively(n - 1);
	}

	private static int calcTriangularAnalytically(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}
		return n * (n + 1) / 2;
	}

	private static void renderTriangle(int n) {
		for (int i = 1; i <= n; ++i) {
			int shift = n - i;
			System.out.print(" ".repeat(shift));
			System.out.println(SYMBOL.repeat(i));
		}
	}

	public static void execute(Scanner scanner) {

		do {
			Console.clearScreen();
			Console.printTitle("Вычисление n-ого треугольного числа",
					ForeColors.BRIGHT_CYAN);

			int n = Console.getUserInputInt(scanner,
					"Введите неотрицательное целое число: ", v -> v >= 0,
					"Некорректный ввод: Требуется неотрицательное целое число. " + Console.PLEASE_REPEAT,
					INPUT_STYLE);

			System.out.printf("\n%d-е треугольное число\n", n);

			int triangularA = calcTriangularAnalytically(n);
			System.out.print(
					"\tвычисленное с использованием аналитической формулы T\u2099 = \u00BD\u00B7n(n + 1): ");
			Console.printlnStyled(OUTPUT_STYLE, Integer.toString(triangularA));

			if (n <= 10000) {
				int triangularR = calcTriangularRecursively(n);
				System.out.print(
						"\tвычисленное с использованием рекуррентного соотношения T\u2099 = T\u2099\u208B\u2081 + n: ");
				Console.printlnStyled(OUTPUT_STYLE, Integer.toString(triangularR));
			}

			if (n > 1 && n < 31) {
				renderTriangle(n);
			}

		} while (Console.askYesNo(scanner, "\nЖелаете повторить (Y/n)? ", true));
	}
}
