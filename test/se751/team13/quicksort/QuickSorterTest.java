package se751.team13.quicksort;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class QuickSorterTest<T extends Comparable<? super T>> {
	Sorter sorter;
	List<Integer> unsorted;
	List<Integer> sorted;

	@Parameters
	public static Collection<Object[]> primeNumbers() {
		return Arrays
				.asList(new Object[][] {
						{ new ParallelQuicksort(),
								generateRandomNumbers(100, 50) },
						{ new ParallelQuicksort(),
								generateRandomNumbers(1000, 500) },
						{ new ParallelQuicksort(),
								generateRandomNumbers(10000, 50000) },
						{ new ParallelQuicksort(), generateInOrderNumbers(100) },
						{ new ParallelQuicksort(), generateInOrderNumbers(1000) },
						{ new ParallelQuicksort(),
								generateInOrderNumbers(10000) },
						{ new QuickSorter(), generateRandomNumbers(100, 50) },
						{ new QuickSorter(), generateRandomNumbers(1000, 500) },
						{ new QuickSorter(),
								generateRandomNumbers(10000, 50000) },
						{ new QuickSorter(), generateInOrderNumbers(100) },
						{ new QuickSorter(), generateInOrderNumbers(1000) },
						{ new QuickSorter(), generateInOrderNumbers(10000) } });
	}

	public QuickSorterTest(Sorter sorter, List<Integer> unsorted) {
		this.sorter = sorter;
		this.unsorted = unsorted;
	}

	private static List<Integer> generateRandomNumbers(int amount, int max) {
		Random rand = new Random(System.currentTimeMillis());
		List<Integer> nums = new ArrayList<Integer>();

		for (int i = 0; i < amount; i++) {
			nums.add(rand.nextInt(max));
		}
		return nums;
	}

	private static List<Integer> generateInOrderNumbers(int amount) {
		List<Integer> nums = new ArrayList<Integer>();

		for (int i = 0; i < amount; i++) {
			nums.add(i);
		}
		return nums;
	}

	private boolean inOrder(List<Integer> list) {

		Integer previous, current;
		previous = list.get(0);

		for (int i = 1; i < list.size(); i++) {
			current = list.get(i);

			if (previous.compareTo(current) > 0)
				return false;

			previous = current;
		}

		return true;
	}

	@Test
	public void testSorted() throws InterruptedException,
			BrokenBarrierException {
		sorted = sorter.sort(unsorted);
		assertTrue(inOrder(sorted));
	}

	@Test
	public void testLength() throws InterruptedException,
			BrokenBarrierException {
		sorted = sorter.sort(unsorted);
		assertTrue(sorted.size() == unsorted.size());
	}
}
