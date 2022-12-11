package edu.alexey.homework5;

import java.util.Scanner;

import edu.alexey.utils.Console;

public class Main {
	public static void main(String[] args) {
		Console.activateAnsiEscSeqWinCmd(true);
		try (Scanner scanner = new Scanner(System.in)) {
			TaskTriangularNumber.execute(scanner);
		}
	}
}
