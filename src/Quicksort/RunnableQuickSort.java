package Quicksort;

import java.util.ArrayList;
import java.util.List;

public class RunnableQuickSort extends SequentialQuickSort implements Runnable {

	public RunnableQuickSort(List<Integer> unsorted) {
		super(unsorted);
	}

	@Override
	public void run() {
		super.SortList();
	}

	public List<List<Integer>> getSections(List<Integer> points) {
		List<List<Integer>> sections = new ArrayList<List<Integer>>();

		int currentPointIndex = 0;
		int from = 0;
		int point = points.get(currentPointIndex);

		for (int i = 0; i < sortedList.size(); i++) {
			if (sortedList.get(i) > point) {
				sections.add(sortedList.subList(from, i));
				from = i;
				currentPointIndex += 1;
				if (currentPointIndex >= points.size()) {
					sections.add(sortedList.subList(from, sortedList.size() - 1));
					break;
				}

				point = points.get(currentPointIndex);
			}
		}

		System.out.println("SS:" + sections.size());
		return sections;
	}
}
