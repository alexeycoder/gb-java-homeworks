package edu.alexey.utils;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;

public class Console {

	// types

	public record TextStyle(Boolean bold, Boolean italic, Boolean underline,
			ForeColors foreColor, BackColors backColor) {
	}

	public record Border(
			String horiz, String vert,
			String topLeft, String topRight,
			String btmLeft, String btmRight) {
	}

	// consts

	private static final String EXIT_APP_CMD = "q";
	private static final String EXIT_APP_NOTE = "Вы завершили программу.";

	public static final String PLEASE_REPEAT = "Пожалуйста попробуйте снова.";
	private static final String ERR_NOT_INT = "Некорректный ввод: Требуется целое число. " + PLEASE_REPEAT;

	private static final String ERR_INT_MUST_BE_IN_RANGE = "Число должно быть в интервале от %d до %d! "
			+ PLEASE_REPEAT;
	private static final String ERR_INT_TOO_LOW = "Число не должно быть меньше %d! " + PLEASE_REPEAT;
	private static final String ERR_INT_TOO_HIGH = "Число не должно быть больше %d! " + PLEASE_REPEAT;

	private static final String ERR_ACTIVATE_ANSI_FAILS = "Не удалось активировать поддержку"
			+ " управляющих последовательностей для вашего Windows терминала."
			+ " Возможно некорректное отображение вывода программы.";

	private static final String PY_CODE_ENABLE_ANSI_WIN = ("import os"
			+ "\nif os.name == 'nt':"
			+ "\n    import ctypes"
			+ "\n    kernel32 = ctypes.windll.kernel32"
			+ "\n    kernel32.SetConsoleMode(kernel32.GetStdHandle(-11), 7)");

	private static final Border TITLE_BORDER = new Border("\u2550", "\u2551", "\u2554", "\u2557", "\u255a", "\u255d");

	// methods & related fields

	private static boolean activateAnsiInvoked = false;

	public static void activateAnsiEscSeqWinCmd(boolean notifyFail) {
		if (activateAnsiInvoked)
			return;
		activateAnsiInvoked = true;

		var osName = System.getProperty("os.name");
		if (!(osName.toLowerCase().startsWith("win") || osName.toLowerCase().startsWith("nt")))
			return;

		try {
			ProcessBuilder pb = new ProcessBuilder("python3", "-c", PY_CODE_ENABLE_ANSI_WIN);
			pb.start();
		} catch (Exception ex) {
			if (notifyFail)
				printError(ERR_ACTIVATE_ANSI_FAILS, ex);
		}
	}

	public static Integer getUserInputInt(
			Scanner inputScanner, String prompt,
			Function<Integer, Boolean> checkIfValid,
			String warnOutOfRange, TextStyle inputStyle) {

		boolean customStyle = inputStyle != null;
		boolean wrongType = false;
		boolean outOfRange = false;

		while (true) {
			if (wrongType) {
				wrongType = false;
				printError(ERR_NOT_INT, null);
			}
			if (outOfRange) {
				outOfRange = false;
				if (warnOutOfRange != null)
					printError(warnOutOfRange, null);
			}

			System.out.print(prompt);
			if (customStyle) {
				System.out.print(styleText(inputStyle, false, ""));
			}
			var rawInp = inputScanner.nextLine();
			if (customStyle) {
				resetStyle();
			}
			if (rawInp.toLowerCase().startsWith(EXIT_APP_CMD)) {
				forceExit();
			}
			var value = tryParseInt(rawInp);
			if (value != null) {
				if (checkIfValid == null || checkIfValid.apply(value)) {
					return value;
				}
				outOfRange = true;
			} else {
				wrongType = true;
			}
		}
	}

	public static Integer getUserInputIntRange(
			Scanner inputScanner, String prompt,
			Integer min, Integer max,
			TextStyle inputStyle) {

		boolean isMinSet = min != null && !min.equals(Integer.MIN_VALUE);
		boolean isMaxSet = max != null && !max.equals(Integer.MAX_VALUE);
		boolean customStyle = inputStyle != null;

		boolean wrongType = false;
		boolean outOfRange = false;

		while (true) {
			if (wrongType) {
				wrongType = false;
				printError(ERR_NOT_INT, null);
			}
			if (outOfRange) {
				outOfRange = false;
				String errOutOfRange;
				if (isMinSet && isMaxSet) {
					errOutOfRange = String.format(ERR_INT_MUST_BE_IN_RANGE, min, max);
				} else if (isMinSet) {
					errOutOfRange = String.format(ERR_INT_TOO_LOW, min);
				} else {
					errOutOfRange = String.format(ERR_INT_TOO_HIGH, max);
				}
				printError(errOutOfRange, null);
			}

			System.out.print(prompt);
			if (customStyle) {
				System.out.print(styleText(inputStyle, false, ""));
			}
			var rawInp = inputScanner.nextLine();
			if (customStyle) {
				resetStyle();
			}
			if (rawInp.toLowerCase().startsWith(EXIT_APP_CMD)) {
				forceExit();
			}
			var value = tryParseInt(rawInp);
			if (value == null) {
				wrongType = true;
			} else {
				if (!(outOfRange = isOutOfRange(value, min, max))) {
					return value;
				}
			}
		}
	}

	private static boolean isOutOfRange(Integer value, Integer min, Integer max) {
		return (min != null && value < min) || (max != null && value > max);
	}

	public static boolean askYesNo(Scanner inputScanner, String prompt, boolean isYesDefault) {
		System.out.print(prompt);
		var answer = inputScanner.nextLine();

		if (answer.isBlank()) {
			return isYesDefault;
		}

		answer = answer.toLowerCase();
		if (answer.startsWith(EXIT_APP_CMD)) {
			forceExit();
		}

		if (isYesDefault) {
			return answer.startsWith("y") || answer.startsWith("д");
		}
		return answer.startsWith("n") || answer.startsWith("н");
	}

	public static void forceExit() {
		System.out.println();
		System.out.println(EXIT_APP_NOTE);
		System.exit(0);
	}

	public static void clearScreen() {
		System.out.printf(EscapeCode.CLEAR.getCode());
	}

	private static void resetStyle() {
		System.out.print(EscapeCode.RESET.getCode());
	}

	public static void printError(String customMessage, Exception ex) {
		TextStyle errStyle = new TextStyle(null, null, null, ForeColors.RED, null);
		if (ex != null) {
			customMessage += "\n" + ex.getMessage();
		}
		System.err.println(styleText(errStyle, true, customMessage));
	}

	public static void printTitle(String title, ForeColors foreColor) {
		var lines = title.split("\n");
		int maxLen = Arrays.stream(lines).mapToInt(String::length).max().getAsInt();
		int horizPadding = 1;
		var style = new TextStyle(true, null, null, foreColor, null);
		StringBuilder sb = new StringBuilder();
		var horizLine = TITLE_BORDER.horiz.repeat(maxLen + horizPadding * 2);
		sb.append(TITLE_BORDER.topLeft).append(horizLine).append(TITLE_BORDER.topRight);
		for (var line : lines) {
			line = StringUtils.padCenter(line, " ", maxLen);
			sb.append("\n").append(TITLE_BORDER.vert).append(" ").append(line).append(" ").append(TITLE_BORDER.vert);
		}
		sb.append("\n").append(TITLE_BORDER.btmLeft).append(horizLine).append(TITLE_BORDER.btmRight);
		printlnStyled(style, sb.toString());
	}

	public static <T> void printArray(TextStyle style, T[] array) {
		printlnStyled(style, Arrays.toString(array));
	}

	public static void printlnStyled(TextStyle style, String text) {
		var styledText = styleText(style, true, text);
		System.out.println(styledText);
	}

	public static void printfStyled(TextStyle style, String format, Object... args) {
		var styledText = styleText(style, true, format);
		System.out.printf(styledText, args);
	}

	public static String styleText(TextStyle style, boolean resetAtEnd, String text) {
		boolean any = false;
		StringBuilder sb = new StringBuilder();
		if (style.bold != null && style.bold) {
			any = true;
			sb.append(EscapeCode.BOLD.getCode());
		}
		if (style.italic != null && style.italic) {
			any = true;
			sb.append(EscapeCode.ITALIC.getCode());
		}
		if (style.underline != null && style.underline) {
			any = true;
			sb.append(EscapeCode.UNDERLINE.getCode());
		}
		if (Colors.isSpecified(style.foreColor)) {
			any = true;
			sb.append(style.foreColor.getCode());
		}
		if (Colors.isSpecified(style.backColor)) {
			any = true;
			sb.append(style.backColor.getCode());
		}
		sb.append(text);
		if (resetAtEnd && any) {
			sb.append(EscapeCode.RESET.getCode());
		}
		return sb.toString();
	}

	// aux
	private static Integer tryParseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
