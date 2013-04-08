package Quicksort;

import java.util.ArrayList;
import java.util.List;

public class SequentialQuickSort {

	protected List<Integer> unsortedList;
	protected List<Integer> sortedList;
	protected List<List<Integer>> sections;

	public SequentialQuickSort(List<Integer> unsorted) {
		this.unsortedList = unsorted;
	}

	/**
	 * Stateful SortList
	 */
	public void sortList() {
		sortedList = sortList(unsortedList);
	}

	/**
	 * Stateless SortList
	 * 
	 * @param list
	 * @return
	 */
	public List<Integer> sortList(List<Integer> list) {
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
		result.addAll(sortList(less));
		result.add(pivot);
		result.addAll(sortList(greater));
		return result;
	}

	private int extractPivot(List<Integer> list) {
		return list.remove(0);
	}

	public List<Integer> getSorted() {
		return sortedList;
	}

	public List<Integer> getSamples(int processors) {
		List<Integer> samples = new ArrayList<Integer>();

		for (int i = 0; i < processors; i++) {
			samples.add(sortedList.get(i * sortedList.size() / (processors)));
		}

		return samples;
	}
}
