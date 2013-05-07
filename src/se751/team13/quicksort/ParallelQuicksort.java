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
	 * Phase 1 - Each process sorts its share of the initial elements. (SEE 1.5)
	 * Each process gets a regular sample of its locally sorted block.
	 * 
	 * @throws BrokenBarrierException
	 * @throws InterruptedException
	 */
	private <T extends Comparable<? super T>> void sortSections(
			List<QuickSorterTask<T>> sorters, List<T> unsorted)
			throws InterruptedException, BrokenBarrierException {

		int elementsPerThread = unsorted.size() / processors;
		int from, to;
		for (int i = 1; i <= processors; i++) {
			from = elementsPerThread * (i - 1);
			to = elementsPerThread * i;
			if (i == processors) {
				to = unsorted.size();
			}
			QuickSorterTask<T> s = new QuickSorterTask<T>(new ArrayList<T>(
					unsorted.subList(from, to)), barrier);

			sorters.add(s);
			// qs start
			threads.execute(s);
		}

		barrier.await();
		barrier.reset();
	}

	/**
	 * Phase 1.5 - Each process gets a regular sample of its locally sorted
	 * block.
	 * 
	 * @param sorters
	 * @return
	 */
	private <T extends Comparable<? super T>> List<T> sampleSections(
			List<QuickSorterTask<T>> sorters) {
		List<T> samples = new ArrayList<T>();

		for (QuickSorterTask<T> s : sorters) {
			samples.addAll(s.getSamples(processors));
		}

		return samples;
	}

	/**
	 * Phase 2 - One process gathers and sorts the local regular samples. It
	 * selects p - 1 pivot values from the sorted list of regular samples. Each
	 * process partitions its sorted sublist into p disjoint pieces, using the
	 * pivot values as separators between the pieces.
	 * 
	 * @throws InterruptedException
	 */
	private <T extends Comparable<? super T>> List<T> getPivotsFromSamples(
			List<T> samples) throws InterruptedException {
		QuickSorterTask<T> seqQS = new QuickSorterTask<T>(samples);
		Thread runner = new Thread(seqQS);
		runner.start();
		runner.join();
		List<T> pivots = seqQS.getSamples(processors);
		return pivots.subList(pivots.size() - (processors - 1), pivots.size());
	}

	/**
	 * Phase 3 - Each process i keeps its ith partition and sends the jth
	 * partition to process j, for all j != i.
	 * 
	 * @throws BrokenBarrierException
	 * @throws InterruptedException
	 */
	private <T extends Comparable<? super T>> void distributePartitions(
			List<QuickSorterTask<T>> sorters, List<T> points)
			throws InterruptedException, BrokenBarrierException {
		
		List<List<List<T>>> sectionList = new ArrayList<List<List<T>>>();
		for (QuickSorterTask<T> s : sorters) {
			sectionList.add(s.getSections(points));
		}

		// merge the section sections at the same indices.
		sorters.clear();		
		
		for (int i = 0; i < processors; i++) {
			List<T> l = new ArrayList<T>();

			for (int j = 0; j < processors; j++) {
				if (sectionList.get(j).size() > i ) { // This check here may not be needed - above is the issue
					l.addAll(sectionList.get(j).get(i)); // IndexOutOfBoundsException
				}
			}
			QuickSorterTask<T> s = new QuickSorterTask<T>(l, barrier);
			// qs called
			threads.execute(s);

			sorters.add(s);
		}

		barrier.await();
		barrier.reset();
	}

	/**
	 * Phase 4 - Each process merges its p partitions into a single list.
	 * 
	 * @return
	 */
	private <T extends Comparable<? super T>> List<T> mergePartitions(
			List<QuickSorterTask<T>> sorters) {
		List<T> sorted = new ArrayList<T>();
		for (QuickSorterTask<T> s : sorters) {
			sorted.addAll(s.getSortedList());
		}
		return sorted;
	}

	public <T extends Comparable<? super T>> List<T> sort(List<T> unsorted)
			throws InterruptedException, BrokenBarrierException {
		
		threads = Executors.newFixedThreadPool(processors);

		List<QuickSorterTask<T>> sorters = new ArrayList<QuickSorterTask<T>>();

		// PHASE ONE
		sortSections(sorters, unsorted);
		// PHASE ONE POINT FIVE
		List<T> samples = sampleSections(sorters);

		// PHASE TWO
		List<T> points = getPivotsFromSamples(samples);

		// PHASE THREE
		distributePartitions(sorters, points);

		// PHASE FOUR
		List<T> sorted = mergePartitions(sorters);

		threads.shutdown();

		return sorted;
	}
}
