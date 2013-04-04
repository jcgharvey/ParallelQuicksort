package Quicksort;

import java.util.ArrayList;
import java.util.List;

public class Sorter implements Runnable {

	private List<Integer> unsortedList;
	private List<Integer> sortedList;
	private boolean sorted;

	public Sorter(List<Integer> unsorted) {
		this.unsortedList = unsorted;
		this.sorted = false;
	}

	@Override
	public void run() {
		sortedList = SortList(unsortedList);
		sorted = true;
	}

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

	public boolean isSorted() {
		return sorted;
	}
	
	public List<Integer> getSorted() {
		return sortedList;
	}
}
