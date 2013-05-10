package se751.team13.quicksort;

import java.util.ArrayList;
import java.util.List;

public class InplaceQuickSorter<T extends Comparable<? super T>> implements
		Sorter<T> {

	private List<T> data;
	private int granularity;

	public InplaceQuickSorter() {
		this(200);
	}

	public InplaceQuickSorter(int granularity) {
		this.granularity = granularity;
	}

	public List<T> sort(List<T> list) {
		data = new ArrayList<T>(list);
		quickSort(0, list.size()-1);
		return data;
	}

	private void quickSort(int left, int right) {

		if (right - left <= this.granularity) {
			insertion(data, left, right);
			return;
		} else if (left < right) {
			int pivotIndex = left + (right - left) / 2;
			int pivotNewIndex = partition(data, left, right, pivotIndex);
			quickSort(left, pivotNewIndex - 1);
			quickSort(pivotNewIndex + 1, right);
		}
		return;
	}

	private int partition(List<T> array, int leftIndex, int rightIndex,
			int pivotIndex) {

		T pivot = array.get(pivotIndex);
		swap(array, pivotIndex, rightIndex);
		int storeIndex = leftIndex;

		for (int i = leftIndex; i < rightIndex + 1; i++) {
			T t = array.get(i);

			if (t.compareTo(pivot) <= 0) {
				swap(array, i, storeIndex);
				storeIndex++;
			}
		}

		swap(array, storeIndex, rightIndex);
		return storeIndex;
	}

	private void swap(List<T> array, int leftIndex, int rightIndex) {
		T t = array.get(leftIndex);
		array.set(leftIndex, array.get(rightIndex));
		array.set(rightIndex, t);
	}

	private void insertion(List<T> array, int offset, int limit) {
		for (int i = offset; i < limit + 1; i++) {
			T valueToInsert = array.get(i);
			int hole = i;

			while (hole > 0 && valueToInsert.compareTo(array.get(hole - 1)) < 0) {
				array.set(hole, array.get(hole - 1));
				hole--;
			}

			array.set(hole, valueToInsert);
		}
	}
}
