package se751.team13.quicksort;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;

public interface Sorter {
	public <T extends Comparable<? super T>> List<T> sort(List<T> unsorted)
			throws InterruptedException, BrokenBarrierException;
}
