package se751.team13.quicksort.sequential;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;

import se751.team13.quicksort.QuickSorter;
import se751.team13.quicksort.Sorter;

public class SequentialQuicksort<T extends Comparable<? super T>> implements
		Sorter<T> {
	@Override
	public List<T> sort(List<T> unsorted) throws InterruptedException,
			BrokenBarrierException {
		return new QuickSorter<T>().sort(unsorted);
	}
}
