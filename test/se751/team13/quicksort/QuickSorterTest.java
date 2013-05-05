package se751.team13.quicksort;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class QuickSorterTest<T extends Comparable<? super T>> {
	Sorter<Integer> sorter;
	List<Integer> unsorted;
	List<Integer> sorted;

	@Parameters
	public static Collection<Object[]> primeNumbers() {
		return Arrays
				.asList(new Object[][] {
						{ new ParallelQuicksort<Integer>(),
								Util.generateRandomNumbers(100, 50) },
						{ new ParallelQuicksort<Integer>(),
								Util.generateRandomNumbers(1000, 500) },
						{ new ParallelQuicksort<Integer>(),
								Util.generateRandomNumbers(10000, 50000) },
						{ new ParallelQuicksort<Integer>(),
								Util.generateInOrderNumbers(100) },
						{ new ParallelQuicksort<Integer>(),
								Util.generateInOrderNumbers(1000) },
						{ new ParallelQuicksort<Integer>(),
								Util.generateInOrderNumbers(10000) },
						{ new QuickSorter<Integer>(),
								Util.generateRandomNumbers(100, 50) },
						{ new QuickSorter<Integer>(),
								Util.generateRandomNumbers(1000, 500) },
						{ new QuickSorter<Integer>(),
								Util.generateRandomNumbers(10000, 50000) },
						{ new QuickSorter<Integer>(),
								Util.generateInOrderNumbers(100) },
						{ new QuickSorter<Integer>(),
								Util.generateInOrderNumbers(1000) },
						{ new QuickSorter<Integer>(),
								Util.generateInOrderNumbers(10000) } });
	}

	public QuickSorterTest(Sorter<Integer> sorter, List<Integer> unsorted) {
		this.sorter = sorter;
		this.unsorted = unsorted;
	}

	@Test
	public void testSorted() throws InterruptedException,
			BrokenBarrierException {
		sorted = sorter.sort(unsorted);
		assertTrue(Util.inOrder(sorted));
	}

	@Test
	public void testLength() throws InterruptedException,
			BrokenBarrierException {
		sorted = sorter.sort(unsorted);
		System.out.println("len(sorted): " + sorted.size() + " len(unsorted): "
				+ unsorted.size());
		assertTrue(sorted.size() == unsorted.size());
	}
}
