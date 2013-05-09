package se751.team13.quicksort.inplaceparallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class QuickSortTaskManager<T extends Comparable<? super T>> {
	int task_count;
	ExecutorService exec;

	public QuickSortTaskManager() {
		this(Runtime.getRuntime().availableProcessors());
	}

	public QuickSortTaskManager(int n_threads) {
		task_count = 0;
		exec = Executors.newFixedThreadPool(n_threads);
	}

	public synchronized void add_task(Runnable task) {
		task_count++;
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
}