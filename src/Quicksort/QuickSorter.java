package Quicksort;

import java.util.ArrayList;
import java.util.List;

public class QuickSorter {
	public List<Integer> sort(List<Integer> list) {
		if (list.size() <= 1) {
			return list;
		}

		int pivot = extractPivot(list);
		List<Integer> less = new ArrayList<Integer>();
		List<Integer> greater = new ArrayList<Integer>();

		for (int i : list) {
			if (i <= pivot) {
				less.add(i);
			} else {
				greater.add(i);
			}
		}

		// concatenate the lists
		List<Integer> result = new ArrayList<Integer>();
		result.addAll(sort(less));
		result.add(pivot);
		result.addAll(sort(greater));

		return result;
	}

	private int extractPivot(List<Integer> list) {
		return list.remove(0);
	}
}
