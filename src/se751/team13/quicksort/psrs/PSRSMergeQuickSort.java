package se751.team13.quicksort.psrs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import se751.team13.quicksort.Sorter;

public class PSRSMergeQuickSort<T extends Comparable<? super T>> implements
		Sorter<T> {

	private int processors;
	private CyclicBarrier barrier;
	private ExecutorService threads;
	List<PSRSQuickSorterTask<T>> sorters;
	List<MergeTask<T>> mergers;

	public PSRSMergeQuickSort() {
		processors = Runtime.getRuntime().availableProcessors();
		barrier = new CyclicBarrier(processors + 1);
		threads = Executors.newFixedThreadPool(processors);
	}

	/**
	 * Phase 1 - Each process sorts its share of the initial elements. (SEE 1.5)
	 * Each process gets a regular sample of its locally sorted block.
	 * 
	 * @throws BrokenBarrierException
	 * @throws InterruptedException
	 */
	private void sortSections(List<PSRSQuickSorterTask<T>> sorters, List<T> unsorted)
			throws InterruptedException, BrokenBarrierException {

		int elementsPerThread = unsorted.size() / processors;
		int from, to;
		for (int i = 1; i <= processors; i++) {
			from = elementsPerThread * (i - 1);
			to = elementsPerThread * i;
			if (i == processors) {
				to = unsorted.size();
			}
			PSRSQuickSorterTask<T> s = new PSRSQuickSorterTask<T>(new ArrayList<T>(
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
	private List<T> sampleSections(List<PSRSQuickSorterTask<T>> sorters) {
		List<T> samples = new ArrayList<T>();

		for (PSRSQuickSorterTask<T> s : sorters) {
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
	private List<T> getPivotsFromSamples(List<T> samples)
			throws InterruptedException {
		PSRSQuickSorterTask<T> seqQS = new PSRSQuickSorterTask<T>(samples);
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
	private void distributePartitions( List<MergeTask<T>> mergers, List<T> points)
			throws InterruptedException, BrokenBarrierException {
		
		List<List<List<T>>> sectionList = new ArrayList<List<List<T>>>();
		for (PSRSQuickSorterTask<T> s : sorters) {
			sectionList.add(s.getSections(points));
		}

		
		// merge the section sections at the same indices.
		sorters.clear();	

		
		for (int i = 0; i < processors; i++) {
			List<List<T>> l = new ArrayList<List<T>>();

			for (int j = 0; j < processors; j++) {
				if (sectionList.get(j).size() > i ) { // This check here may not be needed - above is the issue
					l.add(sectionList.get(j).get(i)); // IndexOutOfBoundsException
				}
			}
			MergeTask<T> s = new MergeTask<T>(l, barrier);
			// qs called
			threads.execute(s);

			mergers.add(s);
		}

		barrier.await();
		barrier.reset();
	}

	/**
	 * Phase 4 - Each process merges its p partitions into a single list.
	 * 
	 * @return
	 */
	private List<T> mergePartitions(
			List<MergeTask<T>> mergers) {
		List<T> sorted = new ArrayList<T>();
		for (MergeTask<T> m : mergers) {
			sorted.addAll(m.getSortedList());
		}
		return sorted;
	}


	public List<T> sort(List<T> unsorted) throws InterruptedException,
			BrokenBarrierException {

		threads = Executors.newFixedThreadPool(processors);
		sorters = new ArrayList<PSRSQuickSorterTask<T>>();
		mergers = new ArrayList<MergeTask<T>>();
		// PHASE ONE
		sortSections(sorters, unsorted);
		// PHASE ONE POINT FIVE
		List<T> samples = sampleSections(sorters);

		// PHASE TWO
		List<T> points = getPivotsFromSamples(samples);

		// PHASE THREE
		distributePartitions(mergers, points);

		// PHASE FOUR
		List<T> sorted = mergePartitions(mergers);

		threads.shutdown();

		return sorted;
	}
}
