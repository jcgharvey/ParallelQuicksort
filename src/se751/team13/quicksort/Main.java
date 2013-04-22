package se751.team13.quicksort;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	private static final int NUM_NUMBERS = 100000;
	private static final int MAX_NUMBER = 1000;
	private static final int PROCESSORS = Runtime.getRuntime()
			.availableProcessors();

	public static void main(String[] args) throws InterruptedException,
			BrokenBarrierException {

		List<Integer> unsortedNumbers = generateRandomNumbers(NUM_NUMBERS,
				MAX_NUMBER);
		long start;
		long end;

		start = System.currentTimeMillis();
		sequentialQuickSort(unsortedNumbers);
		end = System.currentTimeMillis();
		System.out.println("time seq (ms): " + (end - start));

		start = System.currentTimeMillis();
		parallelQuickSort(unsortedNumbers);
		end = System.currentTimeMillis();

		System.out.println("time par (ms): " + (end - start));
	}

	private static <T extends Comparable<? super T>> void sequentialQuickSort(
			List<T> unsorted) {
		new QuickSorter().sort(unsorted);
	}

	private static <T extends Comparable<? super T>> void parallelQuickSort(
			List<T> unsorted) throws InterruptedException,
			BrokenBarrierException {
		CyclicBarrier barrier = new CyclicBarrier(PROCESSORS + 1);
		ExecutorService threads = Executors.newFixedThreadPool(PROCESSORS);

		List<QuickSorterTask<T>> sorters = new ArrayList<QuickSorterTask<T>>();

		int index = unsorted.size() / PROCESSORS;

		for (int i = 1; i <= PROCESSORS; i++) {
			QuickSorterTask<T> s = new QuickSorterTask<T>(new ArrayList<T>(
					unsorted.subList(index * (i - 1), index * i)), barrier);

			sorters.add(s);
			threads.execute(s);
		}

		barrier.await();
		barrier.reset();

		List<T> samples = new ArrayList<T>();

		for (QuickSorterTask<T> s : sorters) {
			samples.addAll(s.getSamples(PROCESSORS));
		}

		QuickSorterTask<T> seqQS = new QuickSorterTask<T>(samples);
		Thread runner = new Thread(seqQS);
		runner.start();
		runner.join();

		List<T> points = seqQS.getSamples(PROCESSORS);
		points.remove(0);

		// This is my favourite line
		List<List<List<T>>> sectionList = new ArrayList<List<List<T>>>();

		for (QuickSorterTask<T> s : sorters) {
			sectionList.add(s.getSections(points));
		}

		// merge the section sections at the same indices.
		sorters.clear();

		for (int i = 0; i < PROCESSORS; i++) {
			List<T> l = new ArrayList<T>();

			for (int j = 0; j < PROCESSORS; j++) {
				l.addAll(sectionList.get(j).get(i));
			}

			QuickSorterTask<T> s = new QuickSorterTask<T>(l, barrier);
			threads.execute(s);

			sorters.add(s);
		}

		barrier.await();
		barrier.reset();

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

	// private static void printList(List<Integer> list) {
	// for (int i = 0; i < list.size(); i++) {
	// System.out.print(String.format("%3d", list.get(i)) + ", ");
	// }
	// }
}
