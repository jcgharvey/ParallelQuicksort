package se751.team13.quicksort;

import java.util.List;

public interface Sorter<T extends Comparable<? super T>> {
	public List<T> sort(List<T> unsorted);
}
