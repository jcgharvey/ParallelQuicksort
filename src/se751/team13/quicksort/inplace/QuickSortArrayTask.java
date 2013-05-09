package se751.team13.quicksort.inplace;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuickSortArrayTask<T extends Comparable<? super T>> implements
		Runnable {

	QuickSortArray<T> manager;
	private T[] list;
	private int left;
	private int right;

	public QuickSortArrayTask(T[] array, int left, int right,
			QuickSortArray<T> manager) {
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
			manager.add_task(list, left, pivotNewIndex - 1);
			manager.add_task(list, pivotNewIndex + 1, right);
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

class QuickSortArray<T extends Comparable<? super T>> {
	int task_count;
	ExecutorService exec;

	public QuickSortArray(int n_threads) {
		task_count = 0;
		exec = Executors.newFixedThreadPool(n_threads);
	}

	public synchronized void add_task(T[] data, int base, int n) {
		task_count++;
		Runnable task = new QuickSortArrayTask<T>(data, base, n, this);
		exec.execute(task);
	}

	public synchronized void task_done() {
		task_count--;
		if (task_count <= 0)
			notify();
	}

	public synchronized void work_wait() throws java.lang.InterruptedException {
		while (task_count > 0) {
			wait();
		}
		exec.shutdown();
	}

	synchronized void print(double[] doubles) {
		if (doubles.length != 0) {
			double last = doubles[doubles.length - 1];
			for (int i = 0; i < doubles.length - 1; i++) {
				System.out.print((int) doubles[i] + ",");
			}
			System.out.println((int) last);
		} else {
			System.out.println("empty");
		}
	}
}
