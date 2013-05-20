package se751.team13.quicksort.inplaceparallel;

import java.util.ArrayList;
import java.util.List;

import se751.team13.quicksort.Sorter;

public class InplaceListQuickSorter<T extends Comparable<? super T>> implements
		Sorter<T> {
	@Override
	public List<T> sort(List<T> unsorted) {
		unsorted = new ArrayList<T>(unsorted); // don't override

		try {
			QuickSortTaskManager<T> qs = new QuickSortTaskManager<T>();
			qs.add_task(new InplaceListQuickSorterTask(unsorted, 0, unsorted
					.size() - 1, qs));
			qs.work_wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return unsorted;
	}

	private class InplaceListQuickSorterTask implements Runnable {
		QuickSortTaskManager<T> manager;
		private List<T> list;
		private int left;
		private int right;
		private int granularity;

		public InplaceListQuickSorterTask(List<T> array, int left, int right,
				QuickSortTaskManager<T> manager) {
			this.list = array;
			this.left = left;
			this.right = right;
			this.manager = manager;
			this.granularity = 20;
		}
		
		public InplaceListQuickSorterTask(List<T> array, int left, int right,
				QuickSortTaskManager<T> manager, int granularity) {
			this.list = array;
			this.left = left;
			this.right = right;
			this.manager = manager;
			this.granularity = granularity;
		}


		public void run() {
			qssort(left,right);
			manager.task_done();
		}
		
		public void qssort(int leftIndex, int rightIndex){
			if (rightIndex - leftIndex <= granularity) {
				insertion(list, leftIndex, rightIndex);
			} else if (leftIndex < rightIndex) {
				int pivotIndex = leftIndex + (rightIndex - leftIndex) / 2;
				int pivotNewIndex = partition(list, leftIndex, rightIndex, pivotIndex);
				manager.add_task(new InplaceListQuickSorterTask(list, leftIndex,
						pivotNewIndex - 1, manager));
				qssort(	pivotNewIndex + 1, rightIndex);
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
