package Quicksort;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.sun.istack.internal.Pool;

public class Main {
	private static final int NUM_NUMBERS = 1000;
	private static final int MAX_NUMBER = 1000;
	private static final int PROCESSORS = Runtime.getRuntime()
			.availableProcessors();

	private static final CyclicBarrier barrier = new CyclicBarrier(
			PROCESSORS + 1);
	private static final ExecutorService threads = Executors
			.newFixedThreadPool(PROCESSORS);

	private static List<Integer> generateRandomNumbers(int amount, int max) {
		Random rand = new Random(System.currentTimeMillis());
		List<Integer> nums = new ArrayList<Integer>();

		for (int i = 0; i < amount; i++) {
			nums.add(rand.nextInt(max));
		}

		return nums;
	}

	/**
	 * @param args
	 * @throws BrokenBarrierException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException,
			BrokenBarrierException {
		List<Integer> unsortedNumbers = generateRandomNumbers(NUM_NUMBERS,
				MAX_NUMBER);

		long start = System.currentTimeMillis();

		List<List<Integer>> processorLists = new ArrayList<List<Integer>>();
		List<RunnableQuickSort> sorterList = new ArrayList<RunnableQuickSort>();

		int index = unsortedNumbers.size() / PROCESSORS;

		for (int i = 1; i <= PROCESSORS; i++) {
			List<Integer> l = new ArrayList<Integer>(unsortedNumbers.subList(
					index * (i - 1), index * i));

			processorLists.add(l);
			RunnableQuickSort s = new RunnableQuickSort(l, barrier);
			sorterList.add(s);
		}

		for (RunnableQuickSort s : sorterList) {
			threads.execute(s);
		}

		barrier.await();
		barrier.reset();

		System.out.println("Num procs: " + PROCESSORS);
		System.out.println("Num waiting after: " + barrier.getNumberWaiting());

		List<Integer> samples = new ArrayList<Integer>();

		for (RunnableQuickSort s : sorterList) {
			samples.addAll(s.getSamples(PROCESSORS));
		}

		SequentialQuickSort seqQS = new SequentialQuickSort(samples);
		seqQS.sortList();

		List<Integer> points = seqQS.getSamples(PROCESSORS);
		points.remove(0);

		// This is my favourite line
		List<List<List<Integer>>> sectionList = new ArrayList<List<List<Integer>>>();

		for (RunnableQuickSort s : sorterList) {
			sectionList.add(s.getSections(points));
		}

		// merge the section sections at the same indices.
		sorterList.clear();

		for (int i = 0; i < PROCESSORS; i++) {
			List<Integer> l = new ArrayList<Integer>();
			// heh i++ lulz
			for (int j = 0; j < PROCESSORS; j++) {
				l.addAll(sectionList.get(j).get(i));
			}

			RunnableQuickSort s = new RunnableQuickSort(l, barrier);
			threads.execute(s);

			sorterList.add(s);
		}

		barrier.await();
		barrier.reset();

		long end = System.currentTimeMillis();

		System.out.println("time (ms): " + (end - start));

		for (RunnableQuickSort s : sorterList) {
			printList(s.getSorted());
			System.out
					.println("\n=====================================================");
		}
		
		threads.shutdown();
	}

	private static void printList(List<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.print(String.format("%3d", list.get(i)) + ", ");
		}
	}
}
