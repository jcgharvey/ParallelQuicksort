package Quicksort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class RunnableQuickSort extends SequentialQuickSort implements Runnable {
	CyclicBarrier barrier;

	public RunnableQuickSort(List<Integer> unsorted, CyclicBarrier barrier) {
		super(unsorted);
		this.barrier = barrier;
	}

	@Override
	public void run() {
		super.sortList();
		
		try {
			System.out.println("Num waiting on thread " + Thread.currentThread().getName() + ": " + barrier.getNumberWaiting());
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			return;
		}
	}

	public List<List<Integer>> getSections(List<Integer> points) {
		List<List<Integer>> sections = new ArrayList<List<Integer>>();

		int currentPointIndex = 0;
		int from = 0;
		int point = points.get(currentPointIndex);

		for (int i = 0; i < sortedList.size(); i++) {
			if (sortedList.get(i) > point) {
				sections.add(sortedList.subList(from, i));
				from = i;
				currentPointIndex += 1;
				if (currentPointIndex >= points.size()) {
					sections.add(sortedList.subList(from, sortedList.size() - 1));
					break;
				}

				point = points.get(currentPointIndex);
			}
		}

		return sections;
	}
}
