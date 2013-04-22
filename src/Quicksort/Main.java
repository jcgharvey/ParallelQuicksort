package Quicksort;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	private static final int NUM_NUMBERS = 1000;
	private static final int MAX_NUMBER = 1000;
	private static final int PROCESSORS = Runtime.getRuntime()
			.availableProcessors();

	private static final CyclicBarrier barrier = new CyclicBarrier(
			PROCESSORS + 1);
	private static final ExecutorService threads = Executors
			.newFixedThreadPool(PROCESSORS);

	public static void main(String[] args) throws InterruptedException,
			BrokenBarrierException {
		List<Integer> unsortedNumbers = generateRandomNumbers(NUM_NUMBERS,
				MAX_NUMBER);

		long start = System.currentTimeMillis();

		List<List<Integer>> processorLists = new ArrayList<List<Integer>>();
		List<QuickSorterTask<Integer>> sorterList = new ArrayList<QuickSorterTask<Integer>>();

		int index = unsortedNumbers.size() / PROCESSORS;

		for (int i = 1; i <= PROCESSORS; i++) {
			List<Integer> l = new ArrayList<Integer>(unsortedNumbers.subList(
					index * (i - 1), index * i));

			processorLists.add(l);
			QuickSorterTask<Integer> s = new QuickSorterTask<Integer>(l,
					barrier);
			sorterList.add(s);
		}

		for (QuickSorterTask<Integer> s : sorterList) {
			threads.execute(s);
		}

		barrier.await();
		barrier.reset();

		List<Integer> samples = new ArrayList<Integer>();

		for (QuickSorterTask<Integer> s : sorterList) {
			samples.addAll(s.getSamples(PROCESSORS));
		}

		QuickSorterTask<Integer> seqQS = new QuickSorterTask<Integer>(samples);
		Thread runner = new Thread(seqQS);
		runner.start();
		runner.join();

		List<Integer> points = seqQS.getSamples(PROCESSORS);
		points.remove(0);

		// This is my favourite line
		List<List<List<Integer>>> sectionList = new ArrayList<List<List<Integer>>>();

		for (QuickSorterTask<Integer> s : sorterList) {
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

			QuickSorterTask<Integer> s = new QuickSorterTask<Integer>(l,
					barrier);
			threads.execute(s);

			sorterList.add(s);
		}

		barrier.await();
		barrier.reset();

		long end = System.currentTimeMillis();

		System.out.println("time (ms): " + (end - start));

		for (QuickSorterTask<Integer> s : sorterList) {
			printList(s.getSortedList());
			System.out.println("\n=============================");
		}

		threads.shutdown();
	}

	private static List<Integer> generateRandomNumbers(int amount, int max) {
		Random rand = new Random(System.currentTimeMillis());
		List<Integer> nums = new ArrayList<Integer>();

		for (int i = 0; i < amount; i++) {
			nums.add(rand.nextInt(max));
		}

		return nums;
	}

	private static void printList(List<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.print(String.format("%3d", list.get(i)) + ", ");
		}
	}
}
