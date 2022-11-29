package edu.alexey.utils;

public enum ForeColors implements Colors {
	UNSPECIFIED(""),

	BLACK(EscapeCode.FG_BLACK.getCode()),
	RED(EscapeCode.FG_RED.getCode()),
	GREEN(EscapeCode.FG_GREEN.getCode()),
	YELLOW(EscapeCode.FG_YELLOW.getCode()),
	BLUE(EscapeCode.FG_BLUE.getCode()),
	MAGENTA(EscapeCode.FG_MAGENTA.getCode()),
	CYAN(EscapeCode.FG_CYAN.getCode()),
	WHITE(EscapeCode.FG_WHITE.getCode()),
	GRAY(EscapeCode.FG_GRAY.getCode()),
	BRIGHT_RED(EscapeCode.FG_BRIGHT_RED.getCode()),
	BRIGHT_GREEN(EscapeCode.FG_BRIGHT_GREEN.getCode()),
	BRIGHT_YELLOW(EscapeCode.FG_BRIGHT_YELLOW.getCode()),
	BRIGHT_BLUE(EscapeCode.FG_BRIGHT_BLUE.getCode()),
	BRIGHT_MAGENTA(EscapeCode.FG_BRIGHT_MAGENTA.getCode()),
	BRIGHT_CYAN(EscapeCode.FG_BRIGHT_CYAN.getCode()),
	BRIGHT_WHITE(EscapeCode.FG_BRIGHT_WHITE.getCode());

	private String colorCode;

	private ForeColors(String colorCode) {
		this.colorCode = colorCode;
	}

	@Override
	public String getCode() {
		return colorCode;
	}
}
