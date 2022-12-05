package edu.alexey.homework3;

import edu.alexey.utils.Console;

public class Main {
	public static void main(String[] args) {
		Console.clearScreen();
		Console.activateAnsiEscSeqWinCmd(true);
		TaskCombinations.execute(null);
	}
}
