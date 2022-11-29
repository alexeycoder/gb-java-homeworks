package edu.alexey.homework2;

import java.util.Arrays;
import java.util.Random;

public class ArrayUtils {

	// types

	private record ArraysPair<T>(T[] left, T[] right) {
	}

	// methods

	public static <T extends Comparable<T>> T[] mergeSort(T[] array) {
		int len = array.length;
		if (len <= 1) {
			return array;
		}

		var pair = splitArray(array);

		var leftSorted = mergeSort(pair.left);
		var rightSorted = mergeSort(pair.right);

		mergeArrays(array, leftSorted, rightSorted);

		return array;
	}

	private static <T> ArraysPair<T> splitArray(T[] array) {
		int len = array.length;
		assert len > 1;
		int rightStartIndex = len / 2;
		var left = Arrays.copyOfRange(array, 0, rightStartIndex);
		var right = Arrays.copyOfRange(array, rightStartIndex, len);

		return new ArraysPair<T>(left, right);
	}

	private static <T extends Comparable<T>> void mergeArrays(T[] targetArray, T[] left, T[] right) {
		assert targetArray != null && left != null && right != null && targetArray.length == left.length + right.length;

		int leftIndex = 0;
		int rightIndex = 0;
		int i = 0;
		// сливаем по одному элементу, пока какой-нибудь из массивов не закончится:
		while (leftIndex < left.length && rightIndex < right.length) {
			var leftVal = left[leftIndex];
			var rightVal = right[rightIndex];
			if (leftVal == null || leftVal.compareTo(rightVal) < 0) {
				targetArray[i] = leftVal;
				++leftIndex;
			} else {
				targetArray[i] = rightVal;
				++rightIndex;
			}
			++i;
		}
		// добавляем в конец оставшийся хвостик одного из массивов,
		// если таковой имеется:
		for (; leftIndex < left.length; ++leftIndex, ++i) {
			targetArray[i] = left[leftIndex];
		}
		for (; rightIndex < right.length; ++rightIndex, ++i) {
			targetArray[i] = right[rightIndex];
		}
	}

	public static Integer[] populateRandomInteger(Integer[] targetArray, int min, int max) {
		if (min > max) {
			int tmp = min;
			min = max;
			max = tmp;
		}
		Random rnd = new Random();
		for (int i = 0; i < targetArray.length; ++i) {
			targetArray[i] = rnd.nextInt(min, max + 1);
		}
		return targetArray;
	}

	public static String[] populateRandomWords(String[] targetArray, int minLength, int maxLength) {
		if (minLength > maxLength) {
			int tmp = minLength;
			minLength = maxLength;
			maxLength = tmp;
		}
		Random rnd = new Random();
		for (int i = 0; i < targetArray.length; ++i) {
			targetArray[i] = getRandomWord(rnd, minLength, maxLength);
		}
		return targetArray;
	}

	// private static final char[] alphabet = ("abcdefghijklmnopqrstuvwxyz"
	// + "ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

	private static final char[] alphabet = "абвгдежзийклмнопрстуфхцчшщъыьэюя".toCharArray();

	public static String getRandomWord(Random rnd, int minLength, int maxLength) {
		if (minLength > maxLength) {
			int tmp = minLength;
			minLength = maxLength;
			maxLength = tmp;
		}
		int len = rnd.nextInt(minLength, maxLength + 1);
		int abcLen = alphabet.length;
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; ++i) {
			sb.append(alphabet[rnd.nextInt(abcLen)]);
		}
		return sb.toString();
	}

}
