package se751.team13.realquicksort;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuickSortTask implements Runnable {

	QuickSort manager;
	private double[] array;
	private int left;
	private int right;

	public QuickSortTask(double[] array, int left, int right, QuickSort manager) {
		this.array = array;
		this.left = left;
		this.right = right;
		this.manager = manager;
	}

	public void run() {
		if (right - left <= 20) {
			insertion(array, left,right);
		} else if (left < right) {
			int pivotIndex = left + (right - left) / 2;
			int pivotNewIndex = partition(array, left, right, pivotIndex);
			manager.add_task(array, left, pivotNewIndex - 1);
			manager.add_task(array, pivotNewIndex + 1, right);
		}
		manager.task_done();
	}

	private int partition(double[] array, int leftIndex, int rightIndex,
			int pivotIndex) {

		double pivot = array[pivotIndex];
		swap(array, pivotIndex, rightIndex);
		int storeIndex = leftIndex;
		for (int i = leftIndex; i < rightIndex; i++) {
			if (array[i] <= pivot) {
				swap(array, i, storeIndex);
				storeIndex++;
			}
		}
		swap(array, storeIndex, rightIndex);
		return storeIndex;
	}

	private void swap(double[] array, int leftIndex, int rightIndex) {
		double t = array[leftIndex];
		array[leftIndex] = array[rightIndex];
		array[rightIndex] = t;
	}

	private void insertion(double[] array, int offset, int limit) {
		for (int i = offset; i < limit+1; i++) {
			double valueToInsert = array[i];
			int hole = i;
			while (hole > 0 && valueToInsert < array[hole - 1]) {
				array[hole] = array[hole - 1];
				hole--;
			}
			array[hole] = valueToInsert;
		}
	}

	private void print(double[] doubles) {
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

class QuickSort {
	int task_count;
	ExecutorService exec;

	public QuickSort(int n_threads) {
		task_count = 0;
		exec = Executors.newFixedThreadPool(n_threads);
	}

	public synchronized void add_task(double[] data, int base, int n) {
		task_count++;
		Runnable task = new QuickSortTask(data, base, n, this);
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
