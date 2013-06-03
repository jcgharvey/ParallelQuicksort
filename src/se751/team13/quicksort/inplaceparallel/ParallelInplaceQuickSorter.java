package se751.team13.quicksort.inplaceparallel;

import java.util.ArrayList;
import java.util.List;

import se751.team13.quicksort.Sorter;

public class ParallelInplaceQuickSorter<T extends Comparable<? super T>>
		implements Sorter<T> {

	private int numThreads;
	private int granularity;

	/**
	 * Default constructor, sets the number of threads used to the number of
	 * cores available.
	 */
	public ParallelInplaceQuickSorter() {
		this(Runtime.getRuntime().availableProcessors());
	}

	/**
	 * Constructor that takes the number of threads to use. Sets the granularity
	 * to default 250
	 * 
	 * @param numThreads
	 */
	public ParallelInplaceQuickSorter(int numThreads) {
		this(numThreads, 250);
	}

	/**
	 * Full Constructor that sets both the number of threads to use and the
	 * granularity level to go down to before performing sequential insertion
	 * sort.
	 * 
	 * @param numThreads
	 * @param granularity
	 */
	public ParallelInplaceQuickSorter(int numThreads, int granularity) {
		this.numThreads = numThreads;
		this.granularity = granularity;
	}

	/**
	 * Method to sort an unsorted list using inplace quicksort Starts off the
	 * initial task to start parallel sections.
	 * 
	 * @param unsorted
	 */
	@Override
	public List<T> sort(List<T> unsorted) {
		unsorted = new ArrayList<T>(unsorted); // don't override
		try {
			TaskManager<T> qs = new TaskManager<T>(numThreads);
			// add a task for the entire list
			qs.addTask(new InplaceQuickSorterTask(unsorted, 0,
					unsorted.size() - 1, qs));
			qs.workWait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return unsorted;
	}

	/**
	 * Private class that acts as runnable for quicksort task
	 * 
	 */
	private class InplaceQuickSorterTask implements Runnable {
		TaskManager<T> manager;
		private List<T> list;
		private int left;
		private int right;

		public InplaceQuickSorterTask(List<T> array, int left, int right,
				TaskManager<T> manager) {
			this.list = array;
			this.left = left;
			this.right = right;
			this.manager = manager;
		}

		/**
		 * Each task is run by completing quicksort therefore making a single
		 * new task.
		 */
		public void run() {
			qssort(left, right);
			manager.taskDone();
		}

		/**
		 * Quicksort implementation, takes in the indices of the section of the
		 * array it is allowed to act on when the two sections are partitioned,
		 * the left section is passed to a new task while the right section is
		 * continued.
		 * 
		 * @param leftIndex
		 * @param rightIndex
		 */
		public void qssort(int leftIndex, int rightIndex) {
			if (rightIndex - leftIndex <= granularity) {
				insertion(list, leftIndex, rightIndex);
			} else if (leftIndex < rightIndex) {
				int pivotIndex = leftIndex + (rightIndex - leftIndex) / 2;
				int pivotNewIndex = partition(list, leftIndex, rightIndex,
						pivotIndex);
				// create a new task for one section of the list
				manager.addTask(new InplaceQuickSorterTask(list, leftIndex,
						pivotNewIndex - 1, manager));
				qssort(pivotNewIndex + 1, rightIndex);
			}

		}

		/**
		 * Partition the array around a specified elements Swapping until all
		 * elements greater than the pivot are one side of the pivot and all
		 * elements smaller than the pivot are on the other side. Takes in a
		 * left and right index to indicate what section of the array it is
		 * working on.
		 * 
		 * @param array
		 * @param leftIndex
		 * @param rightIndex
		 * @param pivotIndex
		 * @return
		 */
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

		/**
		 * Swaps the elements at two positions
		 * 
		 * @param array
		 * @param leftIndex
		 * @param rightIndex
		 */
		private void swap(List<T> array, int leftIndex, int rightIndex) {
			T t = array.get(leftIndex);
			array.set(leftIndex, array.get(rightIndex));
			array.set(rightIndex, t);
		}

		/**
		 * Insertion sort a section
		 * 
		 * @param array
		 * @param offset
		 * @param limit
		 */
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
