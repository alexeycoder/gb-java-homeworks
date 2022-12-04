package edu.alexey.homework2;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

import edu.alexey.utils.Pair;

public class ArrayUtils {

	// methods

	public static <T extends Comparable<T>> void mergeSortBuffered(T[] array) {
		if (array == null || array.length < 2)
			return;

		@SuppressWarnings("unchecked")
		T[] buffer = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length);

		mergeSortBuffered(array, buffer, 0, array.length);
	}

	private static <T extends Comparable<T>> void mergeSortBuffered(T[] array, T[] buffer, int startInclusive,
			int endExclusive) {
		assert endExclusive <= array.length;
		assert endExclusive > startInclusive;

		int len = endExclusive - startInclusive;
		assert len > 0;
		if (len == 1) {
			return;
		}

		int rightStartIndex = startInclusive + len / 2; // для нечётных len - правая половина будет на элементик шире
		mergeSortBuffered(array, buffer, startInclusive, rightStartIndex);
		mergeSortBuffered(array, buffer, rightStartIndex, endExclusive);
		mergeSortedPartsBuffered(array, buffer, startInclusive, rightStartIndex, rightStartIndex, endExclusive);
	}

	private static <T extends Comparable<T>> void mergeSortedPartsBuffered(
			T[] array, T[] buffer, int leftStartIncl, int leftEndExcl, int rightStartIncl, int rightEndExcl) {
		assert leftEndExcl > leftStartIncl && rightEndExcl > rightStartIncl;
		int l = leftStartIncl;
		int r = rightStartIncl;
		int i = leftStartIncl;

		// слияние по одному элементу из array в buffer, пока какой-нибудь из массивов
		// не закончится:
		while (l < leftEndExcl && r < rightEndExcl) {
			var leftVal = array[l];
			var rightVal = array[r];
			if (rightVal == null || rightVal.compareTo(leftVal) < 0) {
				buffer[i] = rightVal;
				++r;

			} else {
				buffer[i] = leftVal;
				++l;
			}
			++i;
		}
		// добавляем в конец buffer'а оставшийся хвостик левого массива,
		// если таковой имеется:
		for (; l < leftEndExcl; ++l, ++i) {
			buffer[i] = array[l];
		}
		// возможный хвостик правого не имеет смысла копировать в buffer, поскольку
		// эти элементы в последствии также должны оказаться в исходном массиве,
		// где они итак уже имеются.

		// копируем сформированную 'рабочую' часть буфера, содержащую слитые элементы,
		// в исходный массив:
		for (--i; i >= leftStartIncl; --i) {
			array[i] = buffer[i];
		}
	}

	public static <T extends Comparable<T>> void mergeSort(T[] array) {
		int len = array.length;
		if (len <= 1) {
			return;
		}

		var pair = splitArray(array);

		mergeSort(pair.left());
		mergeSort(pair.right());
		mergeArrays(array, pair.left(), pair.right());
	}

	private static <T> Pair<T[]> splitArray(T[] array) {
		int len = array.length;
		assert len > 1;
		int rightStartIndex = len / 2;
		var left = Arrays.copyOfRange(array, 0, rightStartIndex);
		var right = Arrays.copyOfRange(array, rightStartIndex, len);
		return new Pair<T[]>(left, right);
	}

	private static <T extends Comparable<T>> void mergeArrays(T[] targetArray, T[] left, T[] right) {
		assert targetArray != null && left != null && right != null && targetArray.length == left.length + right.length;

		int leftIndex = 0;
		int rightIndex = 0;
		int i = 0;
		// слияние по одному элементу, пока какой-нибудь из массивов не закончится:
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
