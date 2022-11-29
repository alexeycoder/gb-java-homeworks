package edu.alexey.homework2;

import java.util.Arrays;
import java.util.Random;

public class ArrayUtils {

	public static <T extends Comparable<T>> T[] mergeSort(T[] array) {
		int len = array.length;
		if (len <= 1) {
			return array;
		}

		int rightStartIndex = len / 2;
		var left = Arrays.copyOfRange(array, 0, rightStartIndex);
		var right = Arrays.copyOfRange(array, rightStartIndex, len);
		var leftSorted = mergeSort(left);
		var rightSorted = mergeSort(right);
		int leftIndex = 0;
		int rightIndex = 0;
		int i = 0;
		// сливаем по одному элементу, пока какой-нибудь из массивов не закончится:
		while (leftIndex < leftSorted.length && rightIndex < rightSorted.length) {
			var leftVal = leftSorted[leftIndex];
			var rightVal = rightSorted[rightIndex];
			if (leftVal == null || leftVal.compareTo(rightVal) < 0) {
				array[i] = leftVal;
				++leftIndex;
			} else {
				array[i] = rightVal;
				++rightIndex;
			}
			++i;
		}
		// добавляем в конец оставшийся хвостик одного из массивов,
		// если таковой имеется:
		for (; leftIndex < leftSorted.length; ++leftIndex, ++i) {
			array[i] = leftSorted[leftIndex];
		}
		for (; rightIndex < rightSorted.length; ++rightIndex, ++i) {
			array[i] = rightSorted[rightIndex];
		}

		return array;
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

	private static final char[] alphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя".toCharArray();

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
