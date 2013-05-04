package se751.team13.quicksort;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

public class Main {

	private static final int NUM_NUMBERS = 100;
	private static final int MAX_NUMBER = 1000;

	public static void main(String[] args) throws InterruptedException,
			BrokenBarrierException {

		List<Integer> unsortedNumbers = generateRandomNumbers(NUM_NUMBERS,
				MAX_NUMBER);
		long start;
		long end;

		start = System.currentTimeMillis();
		Sorter sqs = new SequentialQuicksort();
		sqs.sort(unsortedNumbers);
		end = System.currentTimeMillis();
		System.out.println("time seq (ms): " + (end - start));

		start = System.currentTimeMillis();
		Sorter pqs = new ParallelQuicksort();
		pqs.sort(unsortedNumbers);
		end = System.currentTimeMillis();

		System.out.println("time par (ms): " + (end - start));
	}

	private static List<Integer> generateRandomNumbers(int amount, int max) {
		Random rand = new Random(System.currentTimeMillis());
		List<Integer> nums = new ArrayList<Integer>();

		for (int i = 0; i < amount; i++) {
			nums.add(rand.nextInt(max));
		}
		
		return nums;
	}
}
