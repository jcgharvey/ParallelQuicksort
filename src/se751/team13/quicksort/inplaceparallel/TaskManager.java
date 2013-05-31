package se751.team13.quicksort.inplaceparallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class TaskManager<T extends Comparable<? super T>> {
	int taskCount;
	ExecutorService exec;

	public TaskManager() {
		this(Runtime.getRuntime().availableProcessors());
	}

	public TaskManager(int numThreads) {
		taskCount = 0;
		exec = Executors.newFixedThreadPool(numThreads);
	}

	public synchronized void addTask(Runnable task) {
		taskCount++;
		exec.execute(task);
	}

	public synchronized void taskDone() {
		taskCount--;
		if (taskCount <= 0)
			notify();
	}

	public synchronized void workWait() throws java.lang.InterruptedException {
		while (taskCount > 0) {
			wait();
		}
		exec.shutdown();
	}
}