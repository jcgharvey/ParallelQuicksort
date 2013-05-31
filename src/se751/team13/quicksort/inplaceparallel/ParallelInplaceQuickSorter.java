package se751.team13.quicksort.inplaceparallel;

import java.util.ArrayList;
import java.util.List;

import se751.team13.quicksort.Sorter;

public class ParallelInplaceQuickSorter<T extends Comparable<? super T>>
		implements Sorter<T> {

	private int numThreads;

	public ParallelInplaceQuickSorter() {
		this(Runtime.getRuntime().availableProcessors());
	}

	public ParallelInplaceQuickSorter(int numThreads) {
		this.numThreads = numThreads;
	}

	@Override
	public List<T> sort(List<T> unsorted) {
		unsorted = new ArrayList<T>(unsorted); // don't override

		try {
			TaskManager<T> qs = new TaskManager<T>(numThreads);
			qs.addTask(new InplaceQuickSorterTask(unsorted, 0, unsorted
					.size() - 1, qs));
			qs.workWait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return unsorted;
	}

	private class InplaceQuickSorterTask implements Runnable {
		TaskManager<T> manager;
		private List<T> list;
		private int left;
		private int right;
		private int granularity;

		public InplaceQuickSorterTask(List<T> array, int left, int right,
				TaskManager<T> manager) {
			this.list = array;
			this.left = left;
			this.right = right;
			this.manager = manager;
			this.granularity = 250;
		}

		public void run() {
			qssort(left, right);
			manager.taskDone();
		}

		public void qssort(int leftIndex, int rightIndex) {
			if (rightIndex - leftIndex <= granularity) {
				insertion(list, leftIndex, rightIndex);
			} else if (leftIndex < rightIndex) {
				int pivotIndex = leftIndex + (rightIndex - leftIndex) / 2;
				int pivotNewIndex = partition(list, leftIndex, rightIndex,
						pivotIndex);
				manager.addTask(new InplaceQuickSorterTask(list, leftIndex,
						pivotNewIndex - 1, manager));
				qssort(pivotNewIndex + 1, rightIndex);
			}

		}

		private int partition(List<T> array, int leftIndex, int rightIndex,
				int pivotIndex) {

			T pivot = array.get(pivotIndex);
			swap(array, pivotIndex, rightIndex);
			int storeIndex = leftIndex;

			for (int i = leftIndex; i < rightIndex; i++) {
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

				while (hole > 0
						&& valueToInsert.compareTo(array.get(hole - 1)) < 0) {
					array.set(hole, array.get(hole - 1));
					hole--;
				}

				array.set(hole, valueToInsert);
			}
		}
	}
}
