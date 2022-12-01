package edu.alexey.homework2;

import java.util.Arrays;
import java.util.List;

import edu.alexey.homework2.ListUtils.FastSubsequensesGenerator;

/**
 * Демо для задачи 2 - поиск длины наибольшей общей подпоследовательности двух
 * последовательностей
 */
public class TaskSubsequences {

	private static void generateIndices(int length, int subLength) {
		int[] indices = new int[subLength];
		for (int i = 0; i < subLength; ++i) {
			indices[i] = i;
		}
		System.out.println(Arrays.toString(indices));

		int pos = subLength - 1;
		while (moveNext(indices, length, subLength)) {
			System.out.println(Arrays.toString(indices));
		}

	}

	private static boolean moveNext(int[] indices, int length, int subLength) {
		if (indices.length <= 0)
			return false;

		while (indices[0] < length - subLength) {

			int pos = subLength - 1;
			int posMaxIndex = length - 1; // length - subLength + pos;
			if (indices[pos] < posMaxIndex) {
				++indices[pos];
				return true;
			}

			do {
				--pos;
				--posMaxIndex;
			} while (pos >= 0 && indices[pos] >= posMaxIndex);

			if (pos >= 0) {
				int i = ++indices[pos];
				while (++pos < subLength) {
					indices[pos] = ++i;
				}
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		// String[] seqFirst = "abcdefghklmabcdbadc".split("");
		// String[] seqSecond = "afbegfgfgbmfgbcbfgc".split("");

		String[] seqFirst = "abcabcaa".split("");
		String[] seqSecond = "acbacba".split("");

		// String[] seqFirst = "abgcjdekf".split("");
		// String[] seqSecond = "156abcdfk5314".split("");

		// String[] seqFirst = "fdghftdhdfg".split("");
		// String[] seqSecond = "".split("");

		// System.out.println(Arrays.toString(seqFirst));
		// System.out.println(Arrays.toString(seqSecond));

		// var pair = discardCrossExclusives(Arrays.asList(seqFirst),
		// Arrays.asList(seqSecond));
		// System.out.println(pair.left().toString());
		// System.out.println(pair.right().toString());

		// List<String> shorter = pair.left().size() < pair.right().size() ? pair.left()
		// : pair.right();
		// TopLevelSubsequensesGenerator<String> gen = new
		// TopLevelSubsequensesGenerator<String>(shorter);
		// List<String> subSeq;
		// while ((subSeq = gen.getNext()) != null) {
		// System.out.println(subSeq.toString());
		// }

		List<List<String>> lcsList = ListUtils.findLongestCommonSubsequences(
				Arrays.asList(seqFirst),
				Arrays.asList(seqSecond));

		for (List<String> list : lcsList) {
			System.out.println(list.toString());
		}

		// var first = Arrays.asList("abgcjdekf".split(""));
		// var second = Arrays.asList("156abcdfk5314".split(""));

		generateIndices(6, 3);
		System.out.println("==============");
		var sample = Arrays.asList("012345".split(""));
		var gen = new FastSubsequensesGenerator<String>(sample, 3);
		List<String> ss;
		while ((ss = gen.getNext()) != null) {
			System.out.println(ss.toString());
		}
	}
}
