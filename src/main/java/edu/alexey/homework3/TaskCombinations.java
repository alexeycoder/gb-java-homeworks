package edu.alexey.homework3;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Scanner;

import edu.alexey.utils.BackColors;
import edu.alexey.utils.Console;
import edu.alexey.utils.Console.TextStyle;
import edu.alexey.utils.ForeColors;

public class TaskCombinations {
	private static final TextStyle HEADER_STYLE = new TextStyle(true, true, false, ForeColors.BRIGHT_WHITE,
			BackColors.UNSPECIFIED);
	private static final TextStyle EM_STYLE = new TextStyle(true, false, false, ForeColors.YELLOW,
			BackColors.UNSPECIFIED);

	record Params(int a, int b, int c, int d) {
	}

	private static void doTask(Params params) {
		var cc = new CombinationsCounter(params.a, params.b, params.c, params.d);
		Instant startTime = Instant.now();
		cc.count();
		Duration elapsedTime = Duration.between(startTime, Instant.now());

		printSeparator();
		printParameters(params);
		printResult(cc.getWaysTotal(), cc.getMinSteps(), cc.getOptimumWays(), elapsedTime);
	}

	private static void printParameters(Params params) {
		Console.printlnStyled(HEADER_STYLE, "Дано:");
		System.out.println(String.format("\ta = %d; b = %d; c = %d; d = %d\n",
				params.a, params.b, params.c, params.d));
	}

	private static void printResult(long waysTotal, int minSteps, List<String> optimumWays, Duration elapsedTime) {
		Console.printlnStyled(HEADER_STYLE, "Получено:");
		if (waysTotal == 0) {
			System.out.printf("\tНет решения! затрачено %d мс\n", elapsedTime.toMillis());
			return;
		}

		StringBuilder results = new StringBuilder();
		results.append("\tВсего комбинаций: ")
				.append(Console.styleText(EM_STYLE, true, Long.toString(waysTotal)))
				.append(" (кратчайших : ")
				.append(Console.styleText(EM_STYLE, true, Integer.toString(optimumWays.size()))).append(")")
				.append("\tшагов минимум: ")
				.append(Console.styleText(EM_STYLE, true, Integer.toString(minSteps)))
				.append("\tзатрачено: ").append(elapsedTime.toMillis()).append(" мс");
		for (String way : optimumWays) {
			results.append("\n\t").append(way);
		}
		System.out.println(results.toString());
	}

	private static void printSeparator() {
		System.out.println("\u2508".repeat(120));
	}

	public static void execute(Scanner scanner) {
		Console.printTitle("Определение общего количества возможных преобразований из a в b,\n"
				+ "с помощью заданных команд \u00d7с +d, и, кратчайшей последовательности команд,\n"
				+ "позволяющей число a превратить в число b", ForeColors.BRIGHT_CYAN);

		Params[] parameters = {
				new Params(2, 7, 2, 1),
				new Params(3, 27, 3, 2),
				new Params(30, 345, 5, 6),
				new Params(30, 345, 2, 1),
				new Params(22, 333, 3, 1),
				new Params(55, 555, 5, 2),
				new Params(22, 2022, 11, 56),
				new Params(22, 2022, 11, 10),
				new Params(22, 2022, 3, 1),
				new Params(22, 20220, 3, 1),
				new Params(1, 1111, 2, 1),
				new Params(1, 11111, 2, 1),
		};

		for (var params : parameters) {
			doTask(params);
		}
	}
}
