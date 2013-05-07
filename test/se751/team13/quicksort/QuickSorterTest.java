package se751.team13.quicksort;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

import org.junit.Test;

public class QuickSorterTest<T extends Comparable<? super T>> {
	private final int SHORT = 100;
	private final int LARGE = 10000;
	private final int LOW_MAX = 1000;
	private final int HIGH_MAX = 100000;

	Sorter pqs = new ParallelQuicksort();

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
	public void testSmallRandList() throws InterruptedException,
			BrokenBarrierException {
		List<Integer> unsorted = generateRandomNumbers(10, 100);
		List<Integer> sorted = null;

		sorted = pqs.sort(unsorted);

		assertTrue(inOrder(sorted));
		assertTrue(sorted.size() == unsorted.size());
	}

	@Test
	public void testSmallOrderList() throws InterruptedException,
			BrokenBarrierException {
		List<Integer> unsorted = generateInOrderNumbers(10);
		List<Integer> sorted = null;

		sorted = pqs.sort(unsorted);

		assertTrue(inOrder(sorted));
		assertTrue(sorted.size() == unsorted.size());
	}

	@Test
	public void testRandOrderShortInteger() throws InterruptedException,
			BrokenBarrierException {
		List<Integer> unsorted = generateRandomNumbers(SHORT, LOW_MAX);
		List<Integer> sorted = null;

		sorted = pqs.sort(unsorted);

		assertTrue(inOrder(sorted));
		assertTrue(sorted.size() == unsorted.size());
	}

	@Test
	public void testInOrderShortInteger() throws InterruptedException,
			BrokenBarrierException {
		List<Integer> unsorted = generateInOrderNumbers(SHORT);
		List<Integer> sorted = null;

		sorted = pqs.sort(unsorted);

		assertTrue(inOrder(sorted));
		assertTrue(sorted.size() == unsorted.size());
	}

	@Test
	public void testRandOrderLargeInteger() throws InterruptedException,
			BrokenBarrierException {
		List<Integer> unsorted = generateRandomNumbers(LARGE, HIGH_MAX);
		List<Integer> sorted = null;

		sorted = pqs.sort(unsorted);

		assertTrue(inOrder(sorted));
		assertTrue(sorted.size() == unsorted.size());
	}

	@Test
	public void testInOrderLargeInteger() throws InterruptedException,
			BrokenBarrierException {
		List<Integer> unsorted = generateInOrderNumbers(LARGE);
		List<Integer> sorted = null;

		sorted = pqs.sort(unsorted);

		assertTrue(inOrder(sorted));
		assertTrue(sorted.size() == unsorted.size());
	}
}
