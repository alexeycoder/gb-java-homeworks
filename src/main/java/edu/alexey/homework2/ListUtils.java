package edu.alexey.homework2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import edu.alexey.utils.Pair;

public class ListUtils {

	// types

	/**
	 * Быстрый генератор подпоследовательностей заданной длины,
	 * использующий в основе работы генерирование неповотояющихся
	 * комбинаций индексов с последующим отображением в список.
	 */
	public static class FastSubsequensesGenerator<T> {
		private final List<T> sequence;
		private final int length;
		private final int subseqLength;
		private final int[] subseqIndices;

		public FastSubsequensesGenerator(List<T> sequence, int subsequencesLength) {
			this.sequence = sequence;
			this.length = sequence.size();
			this.subseqLength = subsequencesLength;
			subseqIndices = new int[subsequencesLength];
			for (int i = 0; i < subsequencesLength; ++i) {
				subseqIndices[i] = i;
			}
			--subseqIndices[subsequencesLength - 1]; // чтобы первый вызов moveNext установил массив индексов в
														// правильную начальную комбинацию
		}

		public List<T> getNext() {
			if (moveNext()) {
				return mapIndicesToValues(sequence, subseqIndices);
			}
			return null;
		}

		private boolean moveNext() {
			if (subseqIndices.length <= 0)
				return false;

			while (subseqIndices[0] < length - subseqLength) {

				int pos = subseqLength - 1;
				int posMaxIndex = length - 1; // length - subLength + pos;
				if (subseqIndices[pos] < posMaxIndex) {
					++subseqIndices[pos];
					return true;
				}

				do {
					--pos;
					--posMaxIndex;
				} while (pos >= 0 && subseqIndices[pos] >= posMaxIndex);

				if (pos >= 0) {
					int i = ++subseqIndices[pos];
					while (++pos < subseqLength) {
						subseqIndices[pos] = ++i;
					}
					return true;
				} else {
					return false;
				}
			}
			return false;
		}

		private static <T> List<T> mapIndicesToValues(List<T> source, int[] indices) {
			return Arrays.stream(indices).boxed().map(i -> source.get(i)).toList();
		}
	}

	// methods

	public static <T> List<List<T>> findLongestCommonSubsequences(List<T> first, List<T> second) {
		ArrayList<List<T>> lcsList = new ArrayList<>();
		if (first == null || second == null)
			return lcsList;

		var pair = discardCrossExclusives(first, second);

		List<T> shorter = pair.left();
		List<T> longer = pair.right();
		if (shorter.size() > longer.size()) {
			var tmp = shorter;
			shorter = longer;
			longer = tmp;
		}

		if (isSubsequence(shorter, longer)) {
			lcsList.add(shorter);
			return lcsList;
		}

		int subseqLengthLevel = shorter.size() - 1;

		while (subseqLengthLevel > 0) {

			FastSubsequensesGenerator<T> gen = new FastSubsequensesGenerator<T>(shorter, subseqLengthLevel);
			List<T> subsequence;
			while ((subsequence = gen.getNext()) != null) {
				if (isSubsequence(subsequence, longer)) {
					lcsList.add(subsequence);
				}
			}

			if (lcsList.size() > 0) {
				return lcsList;
			}

			--subseqLengthLevel;
		}
		return lcsList;
	}

	// public static class TopLevelSubsequensesGenerator<T> {
	// private final List<T> list;
	// private final int lastIndex;
	// private int pos;

	// public TopLevelSubsequensesGenerator(List<T> list) {
	// this.list = list;
	// this.lastIndex = list.size() - 1;
	// this.pos = 0;
	// }

	// public List<T> getNext() {
	// if (pos > lastIndex) {
	// return null;
	// }
	// var subseq = new ArrayList<T>(list);
	// subseq.remove(pos++);
	// return subseq;
	// }
	// }

	// public static <T> List<List<T>> findLongestCommonSubsequences(List<T> first,
	// List<T> second) {
	// var pair = discardCrossExclusives(first, second);

	// List<T> shorter = pair.left();
	// List<T> longer = pair.right();
	// if (shorter.size() > longer.size()) {
	// var tmp = shorter;
	// shorter = longer;
	// longer = tmp;
	// }

	// ArrayList<List<T>> sameLengthSubsequences = new ArrayList<>();
	// sameLengthSubsequences.add(shorter);

	// if (isSubsequence(shorter, longer)) {
	// return sameLengthSubsequences;
	// }

	// ArrayList<List<T>> lcsList = new ArrayList<>();

	// while (sameLengthSubsequences != null) {

	// for (List<T> list : sameLengthSubsequences) {
	// System.out.println(" > " + list.toString());
	// }

	// ArrayList<List<T>> nextLengthSubsequences = new ArrayList<>();

	// for (List<T> checkSubsequence : sameLengthSubsequences) {

	// TopLevelSubsequensesGenerator<T> gen = new
	// TopLevelSubsequensesGenerator<T>(checkSubsequence);
	// List<T> nextLengthSubsequence;
	// while ((nextLengthSubsequence = gen.getNext()) != null) {
	// if (isSubsequence(nextLengthSubsequence, longer)) {
	// lcsList.add(nextLengthSubsequence);
	// } else {
	// // если на данном уровне длины исследуемых генерируемых
	// подпоследовательностей
	// // обнаружена хотя бы одна общая, то нет смысла копить
	// nextLengthSubsequences,
	// // иначе копим:
	// nextLengthSubsequences.add(nextLengthSubsequence);
	// }
	// }
	// }
	// if (lcsList.size() > 0) {
	// return lcsList;
	// }
	// // если lcsList пуст, знячит ещё не нашлось общих подпоследовательностей
	// // текущей длины, а значит мы не зря копили nextLengthSubsequences -
	// // (подпоследовательности данной длины), так как теперь будем генерировать
	// // из каждой из них новые подпоследовательности на единицу меньшей длины
	// // и искать среди них общие подпоследовательности:
	// sameLengthSubsequences = nextLengthSubsequences;
	// }
	// return lcsList;
	// }

	private static <T> Pair<List<T>> discardCrossExclusives(List<T> first, List<T> second) {
		assert first != null && second != null;
		var firstPure = discardExclusives(first, second);
		var secondPure = discardExclusives(second, firstPure);
		return new Pair<List<T>>(firstPure, secondPure);
	}

	private static <T> List<T> discardExclusives(List<T> source, List<T> samples) {
		assert source != null && samples != null;
		var samplesSet = new HashSet<T>(samples);
		var result = source.stream().filter(item -> samplesSet.contains(item)).toList(); // сперва пробуем immutable
		// если потребуется-> .collect(Collectors.toList());
		return result;
	}

	private static <T> boolean isSubsequence(List<T> maybeSubsequence, List<T> sequence) {
		assert maybeSubsequence != null;
		if (sequence == null || sequence.size() < maybeSubsequence.size()) {
			return false;
		}

		int pos = -1;
		for (T item : maybeSubsequence) {
			pos = nextIndexOf(item, sequence, pos + 1);
			if (pos < 0) {
				return false;
			}
		}
		return true;
	}

	private static <T> int nextIndexOf(T item, List<T> list, int fromIndex) {
		assert item != null && list != null;
		assert fromIndex >= 0;

		int lastIndex = list.size() - 1;
		for (int i = fromIndex; i <= lastIndex; ++i) {
			if (item.equals(list.get(i)))
				return i;
		}
		return -1;
	}

	// private static <T> Pair<List<T>> arrangeShorterLonger(Pair<List<T>> pair) {
	// var shorter = pair.left();
	// var longer = pair.right();
	// if (shorter.size() > longer.size()) {
	// var tmp = shorter;
	// shorter = longer;
	// longer = tmp;
	// }
	// return new Pair<List<T>>(shorter, longer);
	// }
}
