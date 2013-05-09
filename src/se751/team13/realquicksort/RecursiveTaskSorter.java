package se751.team13.realquicksort;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;

import se751.team13.quicksort.Sorter;

public class RecursiveTaskSorter<T extends Comparable<? super T>> implements
		Sorter<T> {

	private int processors;

	public RecursiveTaskSorter() {
		processors = Runtime.getRuntime().availableProcessors();
	}

	@Override
	public List<T> sort(List<T> unsorted) throws InterruptedException,
			BrokenBarrierException {
		// TODO Auto-generated method stub
		QuickSort<T> qs = new QuickSort<T>(processors);
		qs.add_task(unsorted, 0, unsorted.size() - 1);
		qs.work_wait();

		return unsorted;
	}

}
