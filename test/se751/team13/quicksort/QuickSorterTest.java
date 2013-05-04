package se751.team13.quicksort;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

import junit.framework.TestCase;

public class QuickSorterTest extends TestCase{
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
	
	public void testBad() throws InterruptedException, BrokenBarrierException {
		Sorter sorter = new ParallelQuicksort();
		List<Integer> unsorted = generateRandomNumbers(100, 50);
		List<Integer> sorted = sorter.sort(unsorted);
		for(Integer i : sorted){
			System.out.println(i);
		}
		assertTrue(inOrder(sorted));
		assertTrue(sorted.size() == unsorted.size());
	}

}
