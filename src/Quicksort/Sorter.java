package Quicksort;

import java.util.ArrayList;
import java.util.List;

public class Sorter extends SequentialQuickSort implements Runnable {

	private List<Integer> unsortedList;
	private List<Integer> sortedList;
	private List<Integer> samples;
	private List<List<Integer>> sections;
	private boolean sorted;

	public Sorter(List<Integer> unsorted) {
		this.unsortedList = unsorted;
		this.sorted = false;
	}

	// public Sorter(List<Integer> unsorted) {
	// this.unsortedList = unsorted;
	// this.sorted = false;
	// }

	@Override
	public void run() {
		sortedList = super.SortList(unsortedList);
		sorted = true;
	}

	public List<Integer> getSamples(int processors) {
		samples = new ArrayList<Integer>();
		for (int i = 0; i < processors; i++) {
			System.out.println("SL:" + sortedList.size());
			samples.add(sortedList.get(i * sortedList.size() / (processors)));
		}
		System.out.println("SA:" + samples.size());
		return samples;
	}

	public boolean isSorted() {
		return sorted;
	}

	public List<Integer> getSorted() {
		return sortedList;
	}

	private void findSections(List<Integer> points) {
		sections = new ArrayList<List<Integer>>();

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

		// this code is dicks
		// for (int i = sections.size(); i <= points.size() + 1; i++) {
		// sections.add(new ArrayList<Integer>());
		// }

		System.out.println("SS:" + sections.size());
	}

	public List<List<Integer>> getSections(List<Integer> points) {
		if (sections == null)
			findSections(points);
		return sections;
	}
}
