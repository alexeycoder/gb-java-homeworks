package edu.alexey.utils;

public interface Colors {
	String getCode();

	static boolean isSpecified(Colors color) {
		return color != null && !color.getCode().isBlank();
	}
}
