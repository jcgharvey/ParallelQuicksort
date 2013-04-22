package se751.team13.quicksort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelQuicksort implements Sorter {

	private int processors;
	private CyclicBarrier barrier;
	private ExecutorService threads;

	public ParallelQuicksort() {
		processors = Runtime.getRuntime().availableProcessors();
		barrier = new CyclicBarrier(processors + 1);
		threads = Executors.newFixedThreadPool(processors);
	}

	public ParallelQuicksort(int processors) {
		this.processors = processors;
	}

	/**
	 * Phase 1 - Each process sorts its share of the initial elements. Each
	 * process gets a regular sample of its locally sorted block.
	 */
	private <T extends Comparable<? super T>> void sortSections(
			List<QuickSorterTask<T>> sorters, List<T> unsorted) {
		int numElements = unsorted.size();

		for (int i = 1; i <= processors; i++) {
			QuickSorterTask<T> s = new QuickSorterTask<T>(new ArrayList<T>(
					unsorted.subList(numElements * (i - 1), numElements * i)),
					barrier);

			sorters.add(s);
			threads.execute(s);
		}
	}

	/**
	 * Phase 2 - One process gathers and sorts the local regular samples. It
	 * selects p - 1 pivot values from the sorted list of regular samples. Each
	 * process partitions its sorted sublist into p disjoint pieces, using the
	 * pivot values as separators between the pieces.
	 */
	private void gatherSortLocalSamples() {
	}

	/**
	 * Phase 3 - Each process i keeps its ith partition and sends the jth
	 * partition to process j, for all j != i.
	 */
	private void distributePartitions() {
	}

	/**
	 * Phase 4 - Each process merges its p partitions into a single list.
	 */
	private void mergePartitions() {
	}

	public <T extends Comparable<? super T>> List<T> sort(List<T> unsorted)
			throws InterruptedException, BrokenBarrierException {

		List<QuickSorterTask<T>> sorters = new ArrayList<QuickSorterTask<T>>();
		sortSections(sorters, unsorted);

		barrier.await();
		barrier.reset();

		List<T> samples = new ArrayList<T>();

		for (QuickSorterTask<T> s : sorters) {
			samples.addAll(s.getSamples(processors));
		}

		QuickSorterTask<T> seqQS = new QuickSorterTask<T>(samples);
		Thread runner = new Thread(seqQS);
		runner.start();
		runner.join();

		List<T> points = seqQS.getSamples(processors);
		points.remove(0);

		// This is my favourite line
		List<List<List<T>>> sectionList = new ArrayList<List<List<T>>>();

		for (QuickSorterTask<T> s : sorters) {
			sectionList.add(s.getSections(points));
		}

		// merge the section sections at the same indices.
		sorters.clear();

		for (int i = 0; i < processors; i++) {
			List<T> l = new ArrayList<T>();

			System.out.println(sectionList.size());
			System.out.println(sectionList);

			for (int j = 0; j < processors; j++) {
				l.addAll(sectionList.get(j).get(i)); // IndexOutOfBoundsException
			}

			QuickSorterTask<T> s = new QuickSorterTask<T>(l, barrier);
			threads.execute(s);

			sorters.add(s);
		}

		barrier.await();
		barrier.reset();

		threads.shutdown();

		List<T> sorted = new ArrayList<T>();
		for (QuickSorterTask<T> s : sorters) {
			sorted.addAll(s.getSortedList());
		}

		return sorted;
	}
}