package se751.team13.quicksort;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import se751.team13.quicksort.inplaceparallel.ParallelInplaceQuickSorter;
import se751.team13.quicksort.paratask.ParataskQuickSorter;
import se751.team13.quicksort.pyjama.PyjamaQuickSorter;

@RunWith(Parameterized.class)
public class QuickSorterTest<T extends Comparable<? super T>> {
	private static final int listSize = 10000000;
	private static final int numThreads = 4;
	// private static final int numThreads = Runtime.getRuntime()
	// .availableProcessors();

	private static final List<Integer> warmupList = Util
			.generateRandomNumbers(1000);

	private Sorter<Integer> sorter;
	private List<Integer> unsorted;
	private List<Integer> sorted;
	private boolean random;

	@Parameters
	public static Collection<Object[]> parameters() {
		List<Integer> numbers = Util.generateRandomNumbers(listSize);

		return Arrays.asList(new Object[][] {
				{ new ParallelInplaceQuickSorter<Integer>(numThreads),
						new ArrayList<Integer>(numbers), true },
				{ new ParataskQuickSorter<Integer>(numThreads),
						new ArrayList<Integer>(numbers), true },
				{ new PyjamaQuickSorter<Integer>(numThreads),
						new ArrayList<Integer>(numbers), true } });
	}

	public QuickSorterTest(Sorter<Integer> sorter, List<Integer> unsorted,
			boolean random) {
		this.sorter = sorter;
		this.unsorted = unsorted;
		this.random = random;
	}

	@Before
	public void jitWarmup() {
		for (int i = 0; i < 100; i++)
			sorter.sort(warmupList);
	}

	// @Test
	// public void testSorted() {
	// sorted = sorter.sort(unsorted);
	// assertTrue(Util.inOrder(sorted));
	// }
	//
	// @Test
	// public void testLength() {
	// sorted = sorter.sort(unsorted);
	// assertTrue(sorted.size() == unsorted.size());
	// }

	@Test
	public void testTime() {
		long begin, end, total;

		begin = System.currentTimeMillis();
		sorted = sorter.sort(unsorted);
		end = System.currentTimeMillis();

		total = end - begin;

		System.out.println(sorter.getClass().getSimpleName() + "\t\t< time: "
				+ total + " (ms)  \trandom: " + random + "\tlength: "
				+ listSize + "\tnumThreads: " + numThreads + " >");

		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter("results.csv", true)));

			out.println(String.format("%s,%d,%b,%d,%d", sorter.getClass()
					.getSimpleName(), total, random, listSize, numThreads));

			out.close();
		} catch (IOException e) {
		}
	}
}
