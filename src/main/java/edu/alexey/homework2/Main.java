package edu.alexey.homework2;

import java.util.Locale;
import java.util.Scanner;

import edu.alexey.utils.Console;
import edu.alexey.utils.ForeColors;

public class Main {
	public static void main(String[] args) {
		Locale.setDefault(Locale.forLanguageTag("ru_RU"));
		Scanner scanner = new Scanner(System.in);

		do {
			Console.clearScreen();
			Console.activateAnsiEscSeqWinCmd(true);
			Console.printTitle("Решения задач", ForeColors.BRIGHT_CYAN);

			System.out.println("Выберите задачу для демонстрации:");
			int choice = Console.getUserInputInt(scanner,
					"1 \u2014 Сортировка слиянием,\n2 \u2014 Наибольшие общие подпоследовательности: ",
					val -> val == 1 || val == 2,
					"Некорректный ввод: Требуется 1 или 2. " + Console.PLEASE_REPEAT, null);
			System.out.println();

			if (choice == 1) {
				TaskMergeSort.execute(scanner);
			} else {
				TaskSubsequences.execute(scanner);
			}

		} while (Console.askYesNo(scanner, "\nПовторить (Y) или завершить (n)? ", true));

		scanner.close();
	}
}
