package edu.alexey.homework2;

import edu.alexey.utils.Console;

public class Main {
	public static void main(String[] args) {

		Console.activateAnsiEscSeqWinCmd(true);
		TaskMergeSort.execute();
	}
}
