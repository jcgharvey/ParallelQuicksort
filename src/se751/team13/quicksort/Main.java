package se751.team13.quicksort;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;

public class Main {

	private static final int NUM_NUMBERS = 10000;
	private static final int MAX_NUMBER = 1000;

	public static void main(String[] args) throws InterruptedException,
			BrokenBarrierException {

		List<Integer> unsortedNumbers = Util.generateRandomNumbers(NUM_NUMBERS,
				MAX_NUMBER);
		long start;
		long end;

		start = System.currentTimeMillis();
		Sorter<Integer> sqs = new SequentialQuicksort<Integer>();
		sqs.sort(unsortedNumbers);
		end = System.currentTimeMillis();

		System.out.println("time seq (ms): " + (end - start));

		start = System.currentTimeMillis();
		Sorter<Integer> pqs = new ParallelQuicksort<Integer>();
		pqs.sort(unsortedNumbers);
		end = System.currentTimeMillis();

		System.out.println("time par (ms): " + (end - start));
	}

}
