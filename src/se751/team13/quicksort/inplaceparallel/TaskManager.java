package se751.team13.quicksort.inplaceparallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class TaskManager<T extends Comparable<? super T>> {
	int taskCount;
	ExecutorService exec;

	/**
	 * Default constructor Sets number of threads to number of cores available.
	 */
	public TaskManager() {
		this(Runtime.getRuntime().availableProcessors());
	}

	/**
	 * Default constructor Sets number of threads.
	 * 
	 * @param numThreads
	 */
	public TaskManager(int numThreads) {
		taskCount = 0;
		exec = Executors.newFixedThreadPool(numThreads);
	}

	/**
	 * Add a task to the Executor Service
	 * @param task
	 */
	public synchronized void addTask(Runnable task) {
		taskCount++;
		exec.execute(task);
	}

	/**
	 * Sets a task as down by reducing the task count
	 * and notifying if there are no more tasks left
	 */
	public synchronized void taskDone() {
		taskCount--;
		if (taskCount <= 0)
			notify();
	}

	/**
	 * Wait until the tasks are all done.
	 * @throws java.lang.InterruptedException
	 */
	public synchronized void workWait() throws java.lang.InterruptedException {
		while (taskCount > 0) {
			wait();
		}
		exec.shutdown();
	}
}