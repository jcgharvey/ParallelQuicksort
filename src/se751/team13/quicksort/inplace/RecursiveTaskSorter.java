package se751.team13.quicksort.inplace;

import java.util.List;

import se751.team13.quicksort.Sorter;

public class RecursiveTaskSorter<T extends Comparable<? super T>> implements
		Sorter<T> {

	private int processors;

	public RecursiveTaskSorter() {
		processors = Runtime.getRuntime().availableProcessors();
	}

	@Override
	public List<T> sort(List<T> unsorted) {
		try {
			QuickSort<T> qs = new QuickSort<T>(processors);
			qs.add_task(unsorted, 0, unsorted.size() - 1);
			qs.work_wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return unsorted;
	}

}
