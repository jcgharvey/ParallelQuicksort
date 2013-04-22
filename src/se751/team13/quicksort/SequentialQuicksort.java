package se751.team13.quicksort;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;

public class SequentialQuicksort implements Sorter {
	@Override
	public <T extends Comparable<? super T>> List<T> sort(List<T> unsorted)
			throws InterruptedException, BrokenBarrierException {
		return new QuickSorter().sort(unsorted);
	}
}
