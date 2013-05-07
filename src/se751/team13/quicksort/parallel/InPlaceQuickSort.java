package se751.team13.quicksort.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import se751.team13.quicksort.Sorter;

class QuickSorterInside<T extends Comparable<? super T>> implements Runnable {
	private class Parts {
		public final List<T> lt = new ArrayList<T>();
		public final List<T> gt = new ArrayList<T>();
	}

	InPlaceQuickSort<T> manager;
	List<T> unsorted;

	public QuickSorterInside(InPlaceQuickSort<T> manager, List<T> unsorted) {
		this.manager = manager;
		this.unsorted = unsorted;
	}

	public void run() {
		quickSort(new ArrayList<T>(this.unsorted));
	}

	private void quickSort(List<T> list) {
		if (list.size() <= 1) {
			return;
		}

		T pivot = extractPivot(list);
		Parts halves = split(list, pivot);

		List<T> result = new ArrayList<T>(list.size());

		manager.add_task(new QuickSorterInside<T>(this.manager, halves.lt));
		result.add(pivot);
		manager.add_task(new QuickSorterInside<T>(this.manager, halves.gt));
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

class QuickSortTask<T extends Comparable<? super T>> implements Runnable {
	List<T> data;
	int base;
	int n;
	InPlaceQuickSort<T> manager;

	public QuickSortTask(List<T> data, int base, int n,
			InPlaceQuickSort<T> manager) {
		this.data = data;
		this.base = base;
		this.n = n;
		this.manager = manager;
	}

	static final int SORT_DIRECT = 200;

	public void run() {
		int i, j;
		if (n <= SORT_DIRECT) {
			for (j = 1; j < n; j++) {
				T key = data.get(base + j);
				// for (i = j - 1; i >= 0 && key < data.get(base + i); i--)
				for (i = j - 1; i >= 0 && data.get(base + i).compareTo(key) > 0; i--)
					data.set(base + i + 1, data.get(base + i));
				data.set(base + i + 1, key);
			}
			manager.task_done();
			return;
		}
		i = 0;
		j = n - 1;

		while (true) {
			while (data.get(base + 1).compareTo(data.get(base + j)) < 0) {
				// while (data.get(base + i) < data.get(base + j))
				j--;
			}
			if (i >= j)
				break;
			{
				T t = data.get(base + i);
				data.set(base + i, data.get(base + j));
				data.set(base + j, t);
			} /* swap */
			i++;
			while (data.get(base + i).compareTo(data.get(base + j)) < 0) {
				// while (data.get(base + i) < data.get(base + j))
				i++;
			}
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

public class InPlaceQuickSort<T extends Comparable<? super T>> implements
		Sorter<T> {
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

	public synchronized void add_task(Runnable task) {
		task_count++;
		exec.execute(task);
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