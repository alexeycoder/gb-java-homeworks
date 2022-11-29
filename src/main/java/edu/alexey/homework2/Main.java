package edu.alexey.homework2;

import java.util.Arrays;

import edu.alexey.utils.Console;
import edu.alexey.utils.EscapeCode;

public class Main {
	public static void main(String[] args) {
		Integer[] intArr = ArrayUtils.populateRandomInteger(new Integer[11], -100, 100);
		String[] strArr = ArrayUtils.populateRandomWords(new String[10], 3, 10);

		System.out.println(Arrays.toString(strArr));

		// Arrays.stream(intArr).boxed().toArray(Integer[]::new)
		ArrayUtils.mergeSort(strArr);
		System.out.println("Sorted = ");
		System.out.println(Arrays.toString(strArr));

		System.out.println(EscapeCode.CLEAR.getCode());

		Console.activateAnsiEscSeqWinCmd(true);
		TaskMergeSort.execute();

	}
}
