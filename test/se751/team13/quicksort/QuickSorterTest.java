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

import se751.team13.quicksort.parallel.InPlaceQuickSort;
import se751.team13.quicksort.parallel.ParallelQuicksort;
import se751.team13.quicksort.parallel.ParallelQuicksortWithMerge;
import se751.team13.quicksort.sequential.SequentialQuicksort;

@RunWith(Parameterized.class)
public class QuickSorterTest<T extends Comparable<? super T>> {
	Sorter<Integer> sorter;
	List<Integer> unsorted;
	List<Integer> sorted;
	boolean random;

	@Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
				// { new ParallelQuicksort<Integer>(),
				// Util.generateRandomNumbers(100), true },
				// { new ParallelQuicksort<Integer>(),
				// Util.generateRandomNumbers(1000), true },
				// { new ParallelQuicksort<Integer>(),
				// Util.generateRandomNumbers(10000), true },
				// { new ParallelQuicksort<Integer>(),
				// Util.generateRandomNumbers(100000), true },

				// { new ParallelQuicksort<Integer>(),
				// Util.generateInOrderNumbers(100), false },
				// { new ParallelQuicksort<Integer>(),
				// Util.generateInOrderNumbers(1000), false },
				// { new ParallelQuicksort<Integer>(),
				// Util.generateInOrderNumbers(10000), false },
				// { new ParallelQuicksort<Integer>(),
				// Util.generateInOrderNumbers(100000), false },

				// { new SequentialQuicksort<Integer>(),
				// Util.generateRandomNumbers(100), true },
				// { new SequentialQuicksort<Integer>(),
				// Util.generateRandomNumbers(1000), true },
				// { new SequentialQuicksort<Integer>(),
				// Util.generateRandomNumbers(10000), true },
				// { new SequentialQuicksort<Integer>(),
				// Util.generateRandomNumbers(100000), true },

				// { new SequentialQuicksort<Integer>(),
				// Util.generateInOrderNumbers(100), false },
				// { new SequentialQuicksort<Integer>(),
				// Util.generateInOrderNumbers(1000), false },
				// { new SequentialQuicksort<Integer>(),
				// Util.generateInOrderNumbers(10000), false },
				// { new SequentialQuicksort<Integer>(),
				// Util.generateInOrderNumbers(100000), false },

				// { new ParallelQuicksortWithMerge<Integer>(),
				// Util.generateRandomNumbers(100000), true },

				// { new ParallelQuicksort<Integer>(),
				// Util.generateInOrderNumbers(100), false },
				// { new ParallelQuicksort<Integer>(),
				// Util.generateInOrderNumbers(1000), false },
				// { new ParallelQuicksort<Integer>(),
				// Util.generateInOrderNumbers(10000), false },
				// { new ParallelQuicksortWithMerge<Integer>(),
				// Util.generateInOrderNumbers(100000), false }

				{ new InPlaceQuickSort<Integer>(),
						Util.generateInOrderNumbers(1000), false },
				{ new InPlaceQuickSort<Integer>(),
						Util.generateRandomNumbers(1000), true }

		});
	}

	public QuickSorterTest(Sorter<Integer> sorter, List<Integer> unsorted,
			boolean random) {
		this.sorter = sorter;
		this.unsorted = unsorted;
		this.random = random;
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
		assertTrue(sorted.size() == unsorted.size());
	}

	@Test
	public void testTime() throws InterruptedException, BrokenBarrierException {
		long begin, end, total;
		begin = System.currentTimeMillis();
		sorted = sorter.sort(unsorted);
		end = System.currentTimeMillis();

		total = end - begin;

		System.out.println(sorter.getClass().getSimpleName() + "\t< time: "
				+ total + " (ms)  \trandom: " + random + "\tlength: "
				+ unsorted.size() + " >");
	}
}
