package edu.alexey.utils;

/**
 * Для будущих реализаций.
 */
public enum BackColors implements Colors {
	UNSPECIFIED("");

	private String colorCode;

	private BackColors(String colorCode) {
		this.colorCode = colorCode;
	}

	@Override
	public String getCode() {
		return colorCode;
	}

}
