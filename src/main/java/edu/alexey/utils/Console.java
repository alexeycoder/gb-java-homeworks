package edu.alexey.utils;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;

public class Console {

	// types

	public record TextStyle(Boolean bold, Boolean italic, Boolean underline,
			ForeColors foreColor, BackColors backColor) {
	}

	// consts

	private static final String TITLE_BORDER_SYMBOL = "\u2550";

	private static final String PLEASE_REPEAT = "Пожалуйста попробуйте снова.";
	private static final String ERR_NOT_INT = "Некорректный ввод: Требуется целое число. " + PLEASE_REPEAT;

	private static final String ERR_ACTIVATE_ANSI_FAILS = "Не удалось активировать поддержку"
			+ " управляющих последовательностей для вашего Windows терминала."
			+ " Возможно некорректное отображение вывода программы.";

	private static final String PY_CODE_ENABLE_ANSI_WIN = ("import os"
			+ "\nif os.name == 'nt':"
			+ "\n    import ctypes"
			+ "\n    kernel32 = ctypes.windll.kernel32"
			+ "\n    kernel32.SetConsoleMode(kernel32.GetStdHandle(-11), 7)");

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
			Scanner inputStream, String prompt,
			Function<Integer, Boolean> checkIfValid,
			String warnOutOfRange, TextStyle inputStyle) {

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
			System.out.print(prompt + (inputStyle != null ? styleText(inputStyle, false, "") : ""));
			var value = tryParseInt(inputStream.nextLine());
			if (inputStyle != null) {
				resetStyle();
			}

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
		var style = new TextStyle(true, null, null, foreColor, null);
		var border = TITLE_BORDER_SYMBOL.repeat(maxLen);
		printfStyled(style, "%s\n%s\n%s\n", border, title, border);
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
