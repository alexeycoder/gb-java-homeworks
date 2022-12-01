package edu.alexey.homework2;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import edu.alexey.utils.BackColors;
import edu.alexey.utils.Console;
import edu.alexey.utils.Console.TextStyle;
import edu.alexey.utils.ForeColors;

/**
 * Демо для задачи 2 - поиск (длины) наибольшей общей подпоследовательности(ей)
 * двух
 * последовательностей.
 * 
 * (варианты тестовых последовательностей:
 * a b c a b c a a / a c b a c b a
 * a b g c j d e k f / 1 5 6 a b c d f k 5 3 1 4 )
 * a b c d e f g h i j k l m n o p q r s t u v w x y z /
 * l o r e m i p s u m d o l o r s i t a m e t c o n s e c t e t u e r
 */
public class TaskSubsequences {
	private static final TextStyle INPUT_STYLE_1 = new TextStyle(true, false, false, ForeColors.BRIGHT_GREEN,
			BackColors.UNSPECIFIED);
	private static final TextStyle INPUT_STYLE_2 = new TextStyle(true, false, false, ForeColors.BRIGHT_BLUE,
			BackColors.UNSPECIFIED);

	private static final TextStyle OUTPUT_STYLE = new TextStyle(true, false, false, ForeColors.YELLOW,
			BackColors.UNSPECIFIED);

	private static <T> void printResult(List<List<T>> lcsList, Duration elapsed) {

		int len = 0;
		if (lcsList.size() == 0 || (len = lcsList.get(0).size()) == 0) {
			Console.printlnStyled(OUTPUT_STYLE, "Не найдено ни одной общей подпоследовательности (кроме пустой).");
			return;
		}

		if (len == 1) {
			Console.printfStyled(OUTPUT_STYLE,
					"Найдено %d общих подпоследовательностей единичной длины (не выводятся).", lcsList.size());
			return;
		}

		System.out.printf("Длина наибольшей общей подпоследовательности: ");
		Console.printlnStyled(OUTPUT_STYLE, Integer.toString(len));

		System.out.printf("\nНаибольшие общие подпоследовательности (%d шт.):\n", lcsList.size());
		for (List<T> lcs : lcsList) {
			Console.printlnStyled(OUTPUT_STYLE, lcs.toString());
		}

		System.out.printf("\nЗатрачено времени: %d мс\n", elapsed.toMillis());
	}

	public static void execute(Scanner scanner) {
		do {
			Console.clearScreen();
			Console.printTitle("Поиск наибольших общих подпоследовательностей двух последовательностей",
					ForeColors.BRIGHT_CYAN);
			System.out.println("(Программу можно завершить в любой момент, введя q в ответ на любой запрос)");

			String[] firstSequence = Console.getUserInputStringArray(
					scanner,
					"\nВведите первую последовательность символов или слов, разделённых пробелом:\n\n",
					false, INPUT_STYLE_1);

			String[] secondSequence = Console.getUserInputStringArray(
					scanner,
					"\nВведите вторую последовательность символов или слов, разделённых пробелом:\n\n",
					false, INPUT_STYLE_2);
			System.out.println();

			Instant start = Instant.now();

			var longestCommonSubsequences = ListUtils.findLongestCommonSubsequences(
					Arrays.asList(firstSequence),
					Arrays.asList(secondSequence));

			Duration elapsed = Duration.between(start, Instant.now());

			printResult(longestCommonSubsequences, elapsed);

		} while (Console.askYesNo(scanner, "\nЖелаете повторить (Y/n)? ", true));
	}
}
