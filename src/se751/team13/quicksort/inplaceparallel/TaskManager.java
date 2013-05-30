package se751.team13.quicksort.inplaceparallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class TaskManager<T extends Comparable<? super T>> {
	int task_count;
	ExecutorService exec;

	public TaskManager() {
		this(Runtime.getRuntime().availableProcessors());
	}

	public TaskManager(int numThreads) {
		task_count = 0;
		exec = Executors.newFixedThreadPool(numThreads);
	}

	public synchronized void addTask(Runnable task) {
		task_count++;
		exec.execute(task);
	}

	public synchronized void taskDone() {
		task_count--;
		if (task_count <= 0)
			notify();
	}

	public synchronized void workWait() throws java.lang.InterruptedException {
		while (task_count > 0) {
			wait();
		}
		exec.shutdown();
	}
}