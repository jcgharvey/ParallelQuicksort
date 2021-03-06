package se751.team13.quicksort.psrs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import se751.team13.quicksort.InplaceQuickSorter;
import se751.team13.quicksort.NotSortedException;
import se751.team13.quicksort.QuickSorter;
import se751.team13.quicksort.Sorter;

public class PSRSQuickSorterTask<T extends Comparable<? super T>> implements
		Runnable {
	private final List<T> unsorted;
	private List<T> sortedList;
	private CyclicBarrier barrier;
	private Sorter<T> sorter;

	public PSRSQuickSorterTask(List<T> unsorted) {
		this.unsorted = unsorted;
		this.sorter = new InplaceQuickSorter<T>();
	}

	public PSRSQuickSorterTask(List<T> unsorted, CyclicBarrier barrier) {
		this(unsorted);
		this.barrier = barrier;
	}

	@Override
	public void run() {
		sortedList = sorter.sort(unsorted);

		if (barrier == null)
			return;

		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			return;
		}
	}

	public int getSize() {
		return sortedList.size();
	}

	public List<T> getSamples(int processors) {
		if (sortedList == null) {
			throw new NotSortedException("Not sorted");
		}

		List<T> samples = new ArrayList<T>();

		for (int i = 0; i < processors; i++) {
			samples.add(sortedList.get(i * sortedList.size() / (processors)));
		}

		return samples;
	}

	public List<List<T>> getSections(List<T> points) {
		if (sortedList == null) {
			throw new NotSortedException("Not sorted");
		}

		List<List<T>> sections = new ArrayList<List<T>>();

		int currentPointIndex = 0;
		int from = 0;
		T point = points.get(currentPointIndex);

		for (int i = 0; i < sortedList.size(); i++) {
			if (sortedList.get(i).compareTo(point) == 1) {
				sections.add(new ArrayList<T>(sortedList.subList(from, i)));
				from = i;
				currentPointIndex += 1;

				if (currentPointIndex >= points.size()) {
					break;
				}

				point = points.get(currentPointIndex);
			}
		}

		sections.add(new ArrayList<T>(sortedList.subList(from,
				sortedList.size())));
		return sections;
	}

	public List<T> getSortedList() {
		return sortedList;
	}
}
