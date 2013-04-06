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
	public void SortList(){
		sortedList = SortList(unsortedList);
	}
	
	/**
	 * Stateless SortList
	 * @param list
	 * @return
	 */
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
	

	public List<Integer> getSorted() {
		return sortedList;
	}
	
	public List<Integer> getSamples(int processors) {
		List<Integer> samples = new ArrayList<Integer>();
		for (int i = 0; i < processors; i++) {
			System.out.println("SL:" + sortedList.size());
			samples.add(sortedList.get(i * sortedList.size() / (processors)));
		}
		System.out.println("SA:" + samples.size());
		return samples;
	}
}
