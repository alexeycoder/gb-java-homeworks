package edu.alexey.homework4;

import java.util.List;
import java.util.stream.Stream;

public enum Direction {
	NONE(0),
	BOTTOM(0b0001),
	RIGHT(0b0010),
	TOP(0b0100),
	LEFT(0b1000);

	public final int flag;

	private Direction(int flag) {
		this.flag = flag;
	}

	public boolean isFlagged(int dirs) {
		return (dirs & flag) == flag;
	}

	public static List<Direction> getDirectionsList(int flags) {
		var list = Stream.of(Direction.values()).filter(df -> df.isFlagged(flags)).toList();
		return list;
	}
}
