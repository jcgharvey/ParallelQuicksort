package se751.team13.quicksort;

import java.util.ArrayList;
import java.util.List;


public class InplaceQuickSorter<T extends Comparable<? super T>> implements
Sorter<T> {
	
	private Integer granularity;
	private List<T> list;
	
	public InplaceQuickSorter(int granularity){
		this.granularity = granularity;
	}
	
	@Override
	public List<T> sort(List<T> unsorted) {
		list = new ArrayList<T>(unsorted); // don't override
		qssort(0,list.size()-1);
		return list;
	}
	
	private void qssort(int left, int right){
		if (right - left <= granularity) {
			insertion(left, right);
		} else if (left < right) {
			int pivotIndex = left + (right - left) / 2;
			int pivotNewIndex = partition(left, right, pivotIndex);

			qssort(left, pivotNewIndex - 1);
			qssort(pivotNewIndex + 1, right);
		}
	}
	
	private int partition(int leftIndex, int rightIndex,
			int pivotIndex) {

		T pivot = list.get(pivotIndex);
		swap(pivotIndex, rightIndex);
		int storeIndex = leftIndex;

		for (int i = leftIndex; i < rightIndex; i++) {
			T t = list.get(i);

			if (t.compareTo(pivot) <= 0) {
				swap(i, storeIndex);
				storeIndex++;
			}
		}

		swap(storeIndex, rightIndex);
		return storeIndex;
	}
	
	private void swap(int leftIndex, int rightIndex) {
		T t = list.get(leftIndex);
		list.set(leftIndex, list.get(rightIndex));
		list.set(rightIndex, t);
	}
	
	
	private void insertion(int offset, int limit) {
		for (int i = offset; i < limit + 1; i++) {
			T valueToInsert = list.get(i);
			int hole = i;

			while (hole > 0
					&& valueToInsert.compareTo(list.get(hole - 1)) < 0) {
				list.set(hole, list.get(hole - 1));
				hole--;
			}

			list.set(hole, valueToInsert);
		}
	}

}