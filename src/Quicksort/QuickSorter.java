package Quicksort;

import java.util.ArrayList;
import java.util.List;

public class QuickSorter {
	public <T extends Comparable<? super T>> List<T> sort(List<T> list) {
		if (list.size() <= 1) {
			return list;
		}

		T pivot = extractPivot(list);
		List<T> less = new ArrayList<T>();
		List<T> greater = new ArrayList<T>();

		for (T i : list) {
			if (i.compareTo(pivot) == -1) {
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

	private <T extends Comparable<? super T>> T extractPivot(List<T> list) {
		return list.remove(0);
	}
}
