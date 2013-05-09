package se751.team13.quicksort;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import se751.team13.quicksort.inplaceparallel.InplaceListQuickSorter;
import se751.team13.quicksort.psrs.PSRSQuickSorter;

@RunWith(Parameterized.class)
public class QuickSorterTest<T extends Comparable<? super T>> {
	Sorter<Integer> sorter;
	List<Integer> unsorted;
	List<Integer> sorted;
	boolean random;

	@Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
				// { new PSRSMergeQuickSorter<Integer>(),
				// Util.generateRandomNumbers(100000), false },
				{ new PSRSQuickSorter<Integer>(),
						Util.generateRandomNumbers(100000), false },
				// { new InplaceArrayQuickSorter<Integer>(),
				// Util.generateRandomNumbers(100000), false },
				{ new InplaceListQuickSorter<Integer>(),
						Util.generateRandomNumbers(100000), false },
		// { new InplaceQuickSorter<Integer>(),
		// Util.generateRandomNumbers(100000), false },
		// { new InsertionSorter<Integer>(), Util.generateRandomNumbers(100000),
		// false },
		// { new QuickSorter<Integer>(), Util.generateRandomNumbers(100000),
		// false },
				});
	}

	public QuickSorterTest(Sorter<Integer> sorter, List<Integer> unsorted,
			boolean random) {
		this.sorter = sorter;
		this.unsorted = unsorted;
		this.random = random;
	}

	@Before
	public void jitWarmup() {
		sorter.sort(Util.generateRandomNumbers(10000));
	}

	@Test
	public void testSorted() {
		sorted = sorter.sort(unsorted);
		assertTrue(Util.inOrder(sorted));
	}

	@Test
	public void testLength() {
		sorted = sorter.sort(unsorted);
		assertTrue(sorted.size() == unsorted.size());
	}

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
	}
}
