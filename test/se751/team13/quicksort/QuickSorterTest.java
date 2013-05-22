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

import se751.team13.quicksort.inplaceparallel.InplaceListQuickSorter;
import se751.team13.quicksort.paratask.ParataskQuickSorter;
import se751.team13.quicksort.pyjama.PyjamaQuickSorter;

@RunWith(Parameterized.class)
public class QuickSorterTest<T extends Comparable<? super T>> {
	Sorter<Integer> sorter;
	List<Integer> unsorted;
	List<Integer> sorted;
	boolean random;

	@Parameters
	public static Collection<Object[]> parameters() {
		List<Integer> numbers = Util.generateRandomNumbers(1000000);

		return Arrays.asList(new Object[][] {
				// { new PSRSMergeQuickSorter<Integer>(),
				// Util.generateRandomNumbers(100000), false },
				// { new PSRSQuickSorter<Integer>(),
				// Util.generateRandomNumbers(100000), false },
				// { new InplaceArrayQuickSorter<Integer>(),
				// Util.generateRandomNumbers(100000), false },
				{ new InplaceListQuickSorter<Integer>(),
						new ArrayList<Integer>(numbers), false },
				// { new InplaceQuickSorter<Integer>(250),
				// Util.generateRandomNumbers(1000000), false }
				// { new InplaceQuickSorter<Integer>(),
				// Util.generateRandomNumbers(100000), false },
				// { new InsertionSorter<Integer>(),
				// Util.generateRandomNumbers(100000), false },
				// { new QuickSorter<Integer>(),
				// Util.generateRandomNumbers(100000), false },
				{ new ParataskQuickSorter<Integer>(),
						new ArrayList<Integer>(numbers), false },
				{ new InplaceQuickSorter<Integer>(250),
						new ArrayList<Integer>(numbers), false },
				{ new PyjamaQuickSorter<Integer>(),
						new ArrayList<Integer>(numbers), false } });
	}

	public QuickSorterTest(Sorter<Integer> sorter, List<Integer> unsorted,
			boolean random) {
		this.sorter = sorter;
		this.unsorted = unsorted;
		this.random = random;
	}

	@Before
	public void jitWarmup() {
		for (int i = 0; i < 1000; i++)
			sorter.sort(Util.generateRandomNumbers(1000));
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
				+ unsorted.size() + " >");

		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter("results.csv", true)));
			out.println(String.format("%s,%d,%b,%d", sorter.getClass().getSimpleName(), total, random, unsorted.size()));
			out.close();
		} catch (IOException e) {
		}
	}
}
