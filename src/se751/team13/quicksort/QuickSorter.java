package se751.team13.quicksort;

import java.util.ArrayList;
import java.util.List;

public class QuickSorter<T extends Comparable<? super T>> implements Sorter<T> {
	private class Parts {
		public final List<T> lt = new ArrayList<T>();
		public final List<T> gt = new ArrayList<T>();
	}

	public List<T> sort(List<T> list) {
		return quickSort(new ArrayList<T>(list));
	}
	
	private List<T> quickSort(List<T> list) {
		if (list.size() <= 1) {
			return list;
		}

		T pivot = extractPivot(list);
		Parts halves = split(list, pivot);

		List<T> result = new ArrayList<T>(list.size());
		result.addAll(quickSort(halves.lt));
		result.add(pivot);
		result.addAll(quickSort(halves.gt));

		return result;
	}

	private Parts split(List<T> list, T pivot) {
		Parts p = new Parts();

		for (T x : list) {
			if (x.compareTo(pivot) < 0) {
				p.lt.add(x);
			} else {
				p.gt.add(x);
			}
		}

		return p;
	}

	private T extractPivot(List<T> list) {
		return list.remove(list.size() / 2);
	}
}
