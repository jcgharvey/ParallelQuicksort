package se751.team13.quicksort;

import java.util.ArrayList;
import java.util.List;

public class QuickSorter<T extends Comparable<? super T>> implements Sorter<T> {

	/**
	 * Implementation of sequential QuickSort
	 * 
	 * @param list
	 *            to sort
	 * @return sorted list
	 */
	public List<T> sort(List<T> list) {
		if (list.size() <= 1) {
			return list;
		}

		list = new ArrayList<T>(list); // don't mess with the original list

		T pivot = extractPivot(list);
		List<T> less = new ArrayList<T>();
		List<T> greater = new ArrayList<T>();

		for (T i : list) {
			if (i.compareTo(pivot) < 0) {
				less.add(i);
			} else {
				greater.add(i);
			}
		}

		// concatenate the lists
		List<T> result = new ArrayList<T>();
		result.addAll(sort(less));
		result.add(pivot);
		result.addAll(sort(greater));

		return result;
	}

	private T extractPivot(List<T> list) {
		return list.remove(0);
	}
}
