package edu.alexey.homework2;

import java.util.Scanner;

import edu.alexey.utils.BackColors;
import edu.alexey.utils.Console;
import edu.alexey.utils.ForeColors;
import edu.alexey.utils.Console.TextStyle;

/**
 * Решение задачи с алгоритмом сортировки слиянием.
 */
public class TaskMergeSort {
	private static final int MAX_QTY = 9999;
	private static final TextStyle INPUT_STYLE = new TextStyle(false, true, false, ForeColors.BRIGHT_WHITE,
			BackColors.UNSPECIFIED);

	private static final TextStyle OUTPUT_STYLE = new TextStyle(true, false, false, ForeColors.YELLOW,
			BackColors.UNSPECIFIED);

	public static void execute() {

		Console.clearScreen();
		Console.printTitle("Сортировка слиянием \u2014 демонтрация работы алгоритма", ForeColors.BRIGHT_CYAN);
		Scanner scanner = new Scanner(System.in);
		do {
			System.out.println("\nГенерация исходного массива\n");
			System.out.println("Выберите тип исходного массива:");
			int choice = Console.getUserInputInt(scanner,
					"0 \u2014 числовой, 1 - строковый: ",
					val -> val == 0 || val == 1,
					"Некорректный ввод: Требуется 0 или 1. " + Console.PLEASE_REPEAT, INPUT_STYLE);
			System.out.println();

			int size = Console.getUserInputIntRange(scanner, "Задайте количество элементов массива: ",
					0, MAX_QTY, INPUT_STYLE);

			if (choice == 0) {

				int min = 0;
				int max = 0;
				if (size == 0) {
					System.out.println("Задан пустой массив!");
				} else {
					min = Console.getUserInputInt(scanner,
							"Введите нижний предел диапазона значений (целое число): ",
							null, null, INPUT_STYLE);
					max = Console.getUserInputIntRange(scanner,
							"Введите верхний предел диапазона значений (целое число): ",
							min, null, INPUT_STYLE);
				}

				Integer[] array = ArrayUtils.populateRandomInteger(new Integer[size], min, max);

				System.out.println("\nСгенерированный массив:\n");
				Console.printArray(OUTPUT_STYLE, array);

				System.out.println("\nРезультат сортировки:\n");
				ArrayUtils.mergeSort(array);

				Console.printArray(OUTPUT_STYLE, array);

			} else {
				int min = 0;
				int max = 0;
				if (size == 0) {
					System.out.println("Задан пустой массив!");
				} else {
					min = Console.getUserInputInt(scanner,
							"Введите минимальную длину слова: ",
							null, null, INPUT_STYLE);
					max = Console.getUserInputIntRange(scanner,
							"Введите максимальную длину слова: ",
							min, null, INPUT_STYLE);

					String[] array = ArrayUtils.populateRandomWords(new String[size], min, max);

					System.out.println("\nСгенерированный массив:\n");
					Console.printArray(OUTPUT_STYLE, array);

					System.out.println("\nРезультат сортировки:\n");
					ArrayUtils.mergeSort(array);

					Console.printArray(OUTPUT_STYLE, array);
				}

			}

		} while (Console.askYesNo(scanner, "\nЖелаете повторить (Y/n)? ", true));

		scanner.close();
	}
}
