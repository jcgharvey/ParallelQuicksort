package se751.team13.quicksort.inplaceparallel;

public class InplaceArrayQuickSorter<T extends Comparable<? super T>> {
	public T[] sort(T[] unsorted) {
		try {
			QuickSortTaskManager<T> qs = new QuickSortTaskManager<T>();
			qs.add_task(new InplaceArrayQuickSorterTask(unsorted, 0,
					unsorted.length - 1, qs));
			qs.work_wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return unsorted;
	}

	private class InplaceArrayQuickSorterTask implements Runnable {

		QuickSortTaskManager<T> manager;
		private T[] list;
		private int left;
		private int right;

		public InplaceArrayQuickSorterTask(T[] array, int left, int right,
				QuickSortTaskManager<T> manager) {
			this.list = array;
			this.left = left;
			this.right = right;
			this.manager = manager;
		}

		public void run() {
			if (right - left <= 20) {
				insertion(list, left, right);
			} else if (left < right) {
				int pivotIndex = left + (right - left) / 2;
				int pivotNewIndex = partition(list, left, right, pivotIndex);
				manager.add_task(new InplaceArrayQuickSorterTask(list, left,
						pivotNewIndex - 1, manager));
				manager.add_task(new InplaceArrayQuickSorterTask(list,
						pivotNewIndex + 1, right, manager));
			}

			manager.task_done();
		}

		private int partition(T[] array, int leftIndex, int rightIndex,
				int pivotIndex) {

			T pivot = array[pivotIndex];
			swap(array, pivotIndex, rightIndex);
			int storeIndex = leftIndex;
			for (int i = leftIndex; i < rightIndex; i++) {
				T t = array[i];
				if (t.compareTo(pivot) <= 0) {
					swap(array, i, storeIndex);
					storeIndex++;
				}
			}
			swap(array, storeIndex, rightIndex);
			return storeIndex;
		}

		private void swap(T[] array, int leftIndex, int rightIndex) {
			T t = array[leftIndex];
			array[leftIndex] = array[rightIndex];
			array[rightIndex] = t;
		}

		private void insertion(T[] array, int offset, int limit) {
			for (int i = offset; i < limit + 1; i++) {
				T valueToInsert = array[i];
				int hole = i;
				while (hole > 0 && valueToInsert.compareTo(array[hole - 1]) < 0) {
					array[hole] = array[hole - 1];
					hole--;
				}
				array[hole] = valueToInsert;
			}
		}
	}
}
