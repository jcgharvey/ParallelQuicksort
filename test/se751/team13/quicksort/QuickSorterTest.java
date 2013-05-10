package se751.team13.quicksort;

import static org.junit.Assert.assertTrue;

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

@RunWith(Parameterized.class)
public class QuickSorterTest<T extends Comparable<? super T>> {
	Sorter<Double> sorter;
	List<Double> unsorted;
	List<Double> sorted;
	boolean random;
	String message;
	
	@Parameters
	public static Collection<Object[]> parameters() {
		int largeNumber = 1000000;
		List<Integer> created = Util.generateRandomNumbers(20);

		
		List<Double> randomDouble0 = Util.generateRandomDoubles(largeNumber);
		List<Double> randomDouble1 = Util.generateRandomDoubles(largeNumber);
		List<Double> randomDouble2 = Util.generateRandomDoubles(largeNumber);
		List<Double> randomDouble3 = Util.generateRandomDoubles(largeNumber);
		List<Double> randomDouble4 = Util.generateRandomDoubles(largeNumber);
		List<Double> inOrderDouble0 = Util.generateInOrderDoubles(largeNumber);
		List<Double> inOrderDouble1 = Util.generateInOrderDoubles(largeNumber);
		List<Double> inOrderDouble2 = Util.generateInOrderDoubles(largeNumber);
		List<Double> inOrderDouble3 = Util.generateInOrderDoubles(largeNumber);
		List<Double> inOrderDouble4 = Util.generateInOrderDoubles(largeNumber);
		
		return Arrays.asList(new Object[][] {

			{ new InplaceListQuickSorter<Double>(), randomDouble0, false },
			{ new InplaceListQuickSorter<Double>(1), randomDouble0, false },
			{ new InplaceListQuickSorter<Double>(), randomDouble2, false },
			{ new InplaceListQuickSorter<Double>(1), randomDouble2, false },
			{ new InplaceListQuickSorter<Double>(), randomDouble2, false },
			{ new InplaceListQuickSorter<Double>(1), randomDouble2, false },
			{ new InplaceListQuickSorter<Double>(), inOrderDouble0, true },
			{ new InplaceListQuickSorter<Double>(1), inOrderDouble0, true },
			{ new InplaceListQuickSorter<Double>(), inOrderDouble1, true },
			{ new InplaceListQuickSorter<Double>(1), inOrderDouble1, true },
			{ new InplaceListQuickSorter<Double>(), inOrderDouble2, true },
			{ new InplaceListQuickSorter<Double>(), inOrderDouble2, true }

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
//		Integer last = sorted.remove(sorted.size()-1);
//		for (Integer d : sorted){
//			System.out.print(d + ",");
//		}
//		System.out.println(last);
//		assertTrue(Util.inOrderInteger(sorted));
//		assertTrue(sorted.size() == unsorted.size());
//
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

		System.out.println(sorter.getClass().getSimpleName() + ", Time:" 
				+ total + "(ms), Random:" + random + ", Size:"
				+ unsorted.size());
	}
}
