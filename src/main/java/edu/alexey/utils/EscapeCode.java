package edu.alexey.utils;

public enum EscapeCode {
	CLEAR(EscapeCode.csi + "1;1H" + EscapeCode.csi + "2J"),
	RESET(EscapeCode.csi + "0m"),
	BOLD(EscapeCode.csi + "1m"),
	ITALIC(EscapeCode.csi + "3m"),
	UNDERLINE(EscapeCode.csi + "4m"),
	BLINK(EscapeCode.csi + "5m"),

	FG_BLACK(EscapeCode.csi + "30m"),
	FG_RED(EscapeCode.csi + "31m"),
	FG_GREEN(EscapeCode.csi + "32m"),
	FG_YELLOW(EscapeCode.csi + "33m"),
	FG_BLUE(EscapeCode.csi + "34m"),
	FG_MAGENTA(EscapeCode.csi + "35m"),
	FG_CYAN(EscapeCode.csi + "36m"),
	FG_WHITE(EscapeCode.csi + "37m"),
	FG_GRAY(EscapeCode.csi + "90m"),
	FG_BRIGHT_RED(EscapeCode.csi + "91m"),
	FG_BRIGHT_GREEN(EscapeCode.csi + "92m"),
	FG_BRIGHT_YELLOW(EscapeCode.csi + "93m"),
	FG_BRIGHT_BLUE(EscapeCode.csi + "94m"),
	FG_BRIGHT_MAGENTA(EscapeCode.csi + "95m"),
	FG_BRIGHT_CYAN(EscapeCode.csi + "96m"),
	FG_BRIGHT_WHITE(EscapeCode.csi + "97m"),

	BG_BRIGHT_RED(EscapeCode.csi + "101m"),
	BG_BRIGHT_GREEN(EscapeCode.csi + "102m"),
	BG_BRIGHT_YELLOW(EscapeCode.csi + "103m"),
	BG_BRIGHT_BLUE(EscapeCode.csi + "104m"),
	BG_BRIGHT_MAGENTA(EscapeCode.csi + "105m"),
	BG_BRIGHT_CYAN(EscapeCode.csi + "106m"),
	BG_BRIGHT_WHITE(EscapeCode.csi + "107m");

	private final String code;
	private static final String csi = "\u001b[";

	private EscapeCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
