package se751.team13.quicksort.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import se751.team13.quicksort.Sorter;

class QuickSortTask<T extends Comparable<? super T>> implements Runnable {
	List<T> data;
	int base;
	int n;
	InPlaceQuickSort<T> manager;

	public QuickSortTask(List<T> data, int base, int n, InPlaceQuickSort<T> manager) {
		this.data = data;
		this.base = base;
		this.n = n;
		this.manager = manager;
	}

	public void run() {
		int i, j;
		i = 0;
		j = n - 1;
		while (true) {
			while (data.get(base + 1).compareTo(data.get(base + j)) < 0)
				// while (data.get(base + i) < data.get(base + j))
				j--;
			if (i >= j)
				break;
			{
				T t = data.get(base + i);
				data.set(base + i, data.get(base + j));
				data.set(base + j, t);
			} /* swap */
			i++;
			while (data.get(base + i).compareTo(data.get(base + j)) < 0)
				// while (data.get(base + i) < data.get(base + j))
				i++;
			if (i >= j) {
				i = j;
				break;
			}
			{
				T t = data.get(base + i);
				data.set(base + i, data.get(base + j));
				data.set(base + j, t);
			} /* swap */
			j--;
		}
		manager.add_task(data, base, i);
		manager.add_task(data, base + i + 1, n - i - 1);
		manager.task_done();
	}
}

public class InPlaceQuickSort<T extends Comparable<? super T>> implements Sorter<T> {
	int task_count;
	ExecutorService exec;

	public InPlaceQuickSort(int n_threads) {
		task_count = 0;
		exec = Executors.newFixedThreadPool(n_threads);
	}

	public InPlaceQuickSort() {
		this(Runtime.getRuntime().availableProcessors());
	}

	@Override
	public List<T> sort(List<T> unsorted) throws InterruptedException,
			BrokenBarrierException {

		ArrayList<T> data = new ArrayList<T>(unsorted);

		add_task(data, 0, data.size());
		work_wait();

		return data;
	}

	public synchronized void add_task(List<T> data, int base, int n) {
		task_count++;
		Runnable task = new QuickSortTask<T>(data, base, n, this);
		exec.execute(task);
	}

	public synchronized void task_done() {
		task_count--;
		if (task_count <= 0)
			notify();
	}

	public synchronized void work_wait() throws InterruptedException {
		while (task_count > 0) {
			wait();
		}
		exec.shutdown();
	}
}