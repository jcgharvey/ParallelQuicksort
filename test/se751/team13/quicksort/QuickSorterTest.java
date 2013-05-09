package se751.team13.quicksort;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import se751.team13.quicksort.inplaceparallel.InplaceListQuickSorter;
import se751.team13.quicksort.psrs.PSRSMergeQuickSorter;

@RunWith(Parameterized.class)
public class QuickSorterTest<T extends Comparable<? super T>> {
	Sorter<Double> sorter;
	List<Double> unsorted;
	List<Double> sorted;
	boolean random;
	String message;
	
	@Parameters
	public static Collection<Object[]> parameters() {
		int largeNumber = 10000000;
		List<Double> randomDouble = Util.generateRandomDoubles(largeNumber);
		List<Double> inOrderDouble = Util.generateInOrderDoubles(largeNumber);
		
		return Arrays.asList(new Object[][] {
//			{ new QuickSorter<Double>(), randomMillionDouble, false, },
//			{ new PSRSQuickSorter<Double>(), randomMillionDouble, false },
//			{ new PSRSMergeQuickSorter<Double>(), randomMillionDouble, false },
			{ new InplaceListQuickSorter<Double>(), randomDouble, false },
			{ new InplaceListQuickSorter<Double>(1), randomDouble, false },
//			{ new QuickSorter<Double>(), inOrderMillion, true },
//			{ new PSRSQuickSorter<Double>(), inOrderMillion, true },
//			{ new PSRSMergeQuickSorter<Double>(), inOrderMillion, true },
			{ new InplaceListQuickSorter<Double>(), inOrderDouble, true },
			{ new InplaceListQuickSorter<Double>(1), inOrderDouble, true }
		});
	}

	public QuickSorterTest(Sorter<Double> sorter, List<Double> unsorted,
			boolean random) {
		this.sorter = sorter;
		this.unsorted = unsorted;
		this.random = random;
	}

	@Before
	public void jitWarmup() {
		sorter.sort(Util.generateRandomDoubles(10000));
	}

//	@Test
//	public void testSortedAndSize() {
//		sorted = sorter.sort(unsorted);
//		assertTrue(Util.inOrderDouble(sorted));
//		assertTrue(sorted.size() == unsorted.size());
//	}

//	@Test
//	public void testLength() {
//		sorted = sorter.sort(unsorted);
//		assertTrue(sorted.size() == unsorted.size());
//	}

	@Test
	public void testTime() {
		long begin, end, total;
		begin = System.currentTimeMillis();
		sorted = sorter.sort(unsorted);
		end = System.currentTimeMillis();

		total = end - begin;

		System.out.println(sorter.getClass().getSimpleName() + "," 
				+ total + "," + random + ","
				+ unsorted.size());
	}
}
