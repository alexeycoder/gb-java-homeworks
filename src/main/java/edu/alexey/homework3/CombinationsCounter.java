package edu.alexey.homework3;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CombinationsCounter {
	private final Integer a;
	private final Integer b;
	private final Integer c;
	private final Integer d;

	private long waysTotal;
	private int minimumSteps;
	private List<String> optimumWays;

	public CombinationsCounter(int a, int b, int c, int d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.waysTotal = 0L;
	}

	public long getWaysTotal() {
		return waysTotal;
	}

	public int getMinSteps() {
		return minimumSteps;
	}

	public List<String> getOptimumWays() {
		if (optimumWays == null) {
			optimumWays = List.of();
		}
		return optimumWays;
	}

	private static record Bag(long combinations, int minSteps, List<Long> ways) {
	}

	public void count() {
		final Bag zeroBag = new Bag(0L, Integer.MAX_VALUE, List.of());
		final long cCmdFlag = 0L;
		final long dCmdFlag = 1L;

		HashMap<Integer, Bag> countersByValue = new HashMap<>();
		countersByValue.put(a, new Bag(1L, 0, List.of(1L))); // List.of(1L) - служебный mrb

		for (Integer value = a + 1; value <= b; ++value) {

			// reverse '*c' operation:
			var cBag = zeroBag;
			if (value % c == 0) {
				Integer ci = value / c;
				cBag = countersByValue.getOrDefault(ci, zeroBag);
			}

			// reverse '+d' operation:
			Integer di = value - d;
			var dBag = countersByValue.getOrDefault(di, zeroBag);

			// count if anything:
			if (cBag != zeroBag || dBag != zeroBag) {
				int minSteps;
				List<Long> ways;
				if (cBag.minSteps <= dBag.minSteps) {
					minSteps = cBag.minSteps;
					ways = cBag.ways.stream().map(w -> (w << 1) + cCmdFlag).toList();
					if (cBag.minSteps == dBag.minSteps) {
						List<Long> cWays = ways;
						List<Long> dWays = dBag.ways.stream().map(w -> (w << 1) + dCmdFlag).toList();
						ways = Stream.concat(cWays.stream(), dWays.stream()).collect(Collectors.toList());
					}
				} else {
					minSteps = dBag.minSteps;
					ways = dBag.ways.stream().map(w -> (w << 1) + dCmdFlag).toList();
				}

				countersByValue.put(value, new Bag(cBag.combinations + dBag.combinations, minSteps + 1, ways));
			}
		}

		var lastBag = countersByValue.get(b);
		if (lastBag != null) {
			this.waysTotal = lastBag.combinations;
			this.minimumSteps = lastBag.minSteps;
			if (lastBag.ways != null) {
				this.optimumWays = lastBag.ways.stream().map(l -> wayAsString(l)).toList();
			}
		}
	}

	private String wayAsString(long way) {
		String seqStr = Long.toBinaryString(way).replaceFirst("1", "").replace('0', 'c').replace('1', 'd');
		seqStr = seqStr.replace("c", " \u00d7" + c).replace("d", " +" + d);
		seqStr = String.format("%d%s = %d", a, seqStr, b);
		return seqStr;
	}
}
