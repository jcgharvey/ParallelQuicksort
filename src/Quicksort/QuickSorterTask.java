package Quicksort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class QuickSorterTask <T extends Comparable<? super T>> extends QuickSorter implements Runnable {
	private final List<T> unsorted;
	private List<T> sortedList;
	private CyclicBarrier barrier;

	public QuickSorterTask(List<T> unsorted) {
		this.unsorted = unsorted;
	}

	public QuickSorterTask(List<T> unsorted, CyclicBarrier barrier) {
		this.unsorted = unsorted;
		this.barrier = barrier;
	}

	@Override
	public void run() {
		sortedList = super.sort(unsorted);

		if (barrier == null)
			return;

		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			return;
		}
	}

	public List<T> getSamples(int processors) {
		if (getSortedList() == null) {
			throw new NotSortedException("Not sorted");
		}

		List<T> samples = new ArrayList<T>();

		for (int i = 0; i < processors; i++) {
			samples.add(getSortedList().get(
					i * getSortedList().size() / (processors)));
		}

		return samples;
	}

	public List<List<T>> getSections(List<T> points) {
		if (getSortedList() == null) {
			throw new NotSortedException("Not sorted");
		}

		List<List<T>> sections = new ArrayList<List<T>>();

		int currentPointIndex = 0;
		int from = 0;
		T point = points.get(currentPointIndex);

		for (int i = 0; i < getSortedList().size(); i++) {
			if (getSortedList().get(i).compareTo(point) == 1) {
				sections.add(getSortedList().subList(from, i));
				from = i;
				currentPointIndex += 1;
				if (currentPointIndex >= points.size()) {
					sections.add(getSortedList().subList(from,
							getSortedList().size() - 1));
					break;
				}

				point = points.get(currentPointIndex);
			}
		}

		return sections;
	}

	public List<T> getSortedList() {
		return sortedList;
	}
}
