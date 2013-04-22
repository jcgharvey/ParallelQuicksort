package Quicksort;

import java.util.ArrayList;
import java.util.List;

public class SequentialQuickSort {
	
	public List<Integer> SortList(List<Integer> list) {
		if (list.size() <= 1) {
			return list;
		}
		int pivot = list.remove(0);
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
		result.addAll(SortList(less));
		result.add(pivot);
		result.addAll(SortList(greater));
		return result;
	}
}
