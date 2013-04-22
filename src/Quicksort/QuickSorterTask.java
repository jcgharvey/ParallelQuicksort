package Quicksort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class QuickSorterTask extends QuickSorter implements Runnable {
	private final List<Integer> unsorted;
	private List<Integer> sortedList;
	private CyclicBarrier barrier;

	public QuickSorterTask(List<Integer> unsorted) {
		this.unsorted = unsorted;
	}

	public QuickSorterTask(List<Integer> unsorted, CyclicBarrier barrier) {
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

	public List<Integer> getSamples(int processors) {
		if (getSortedList() == null) {
			throw new NotSortedException("Not sorted");
		}

		List<Integer> samples = new ArrayList<Integer>();

		for (int i = 0; i < processors; i++) {
			samples.add(getSortedList().get(
					i * getSortedList().size() / (processors)));
		}

		return samples;
	}

	public List<List<Integer>> getSections(List<Integer> points) {
		if (getSortedList() == null) {
			throw new NotSortedException("Not sorted");
		}

		List<List<Integer>> sections = new ArrayList<List<Integer>>();

		int currentPointIndex = 0;
		int from = 0;
		int point = points.get(currentPointIndex);

		for (int i = 0; i < getSortedList().size(); i++) {
			if (getSortedList().get(i) > point) {
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

	public List<Integer> getSortedList() {
		return sortedList;
	}
}
