package edu.alexey.utils;

/**
 * Для будущих реализаций.
 */
public enum BackColors implements Colors {
	UNSPECIFIED(""),

	BRIGHT_RED(EscapeCode.BG_BRIGHT_RED.getCode()),
	BRIGHT_GREEN(EscapeCode.BG_BRIGHT_GREEN.getCode()),
	BRIGHT_YELLOW(EscapeCode.BG_BRIGHT_YELLOW.getCode()),
	BRIGHT_BLUE(EscapeCode.BG_BRIGHT_BLUE.getCode()),
	BRIGHT_MAGENTA(EscapeCode.BG_BRIGHT_MAGENTA.getCode()),
	BRIGHT_CYAN(EscapeCode.BG_BRIGHT_CYAN.getCode()),
	BRIGHT_WHITE(EscapeCode.BG_BRIGHT_WHITE.getCode());

	private final String colorCode;

	private BackColors(String colorCode) {
		this.colorCode = colorCode;
	}

	@Override
	public String getCode() {
		return colorCode;
	}

}
