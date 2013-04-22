package se751.team13.quicksort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelQuicksort implements Sorter {

	private int processors;

	public ParallelQuicksort() {
		processors = Runtime.getRuntime().availableProcessors();
	}

	public ParallelQuicksort(int processors) {
		this.processors = processors;
	}

	public <T extends Comparable<? super T>> List<T> sort(List<T> unsorted)
			throws InterruptedException, BrokenBarrierException {

		CyclicBarrier barrier = new CyclicBarrier(processors + 1);
		ExecutorService threads = Executors.newFixedThreadPool(processors);

		List<QuickSorterTask<T>> sorters = new ArrayList<QuickSorterTask<T>>();

		int index = unsorted.size() / processors;

		for (int i = 1; i <= processors; i++) {
			QuickSorterTask<T> s = new QuickSorterTask<T>(new ArrayList<T>(
					unsorted.subList(index * (i - 1), index * i)), barrier);

			sorters.add(s);
			threads.execute(s);
		}

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

			for (int j = 0; j < processors; j++) {
				l.addAll(sectionList.get(j).get(i));
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
