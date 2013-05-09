//package se751.team13.quicksort.parallel;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class QSManager {
//	int task_count;
//	ExecutorService exec;
//
//	public QSManager(int n_threads) {
//		task_count = 0;
//		exec = Executors.newFixedThreadPool(n_threads);
//	}
//	public QSManager() {
//		task_count = 0;
//		exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//	}
//	
//	public QSManager(ExecutorService exec) {
//		task_count = 0;
//		this.exec = exec;
//	}
//
//	public synchronized void add_task(int data[], int base, int n) {
//		task_count++;
//		System.out.println("Task Created");
//		Runnable task = new InternetDump(data, base, n, this);
//		exec.execute(task);
//	}
//
//	public synchronized void task_done() {
//		task_count--;
//		System.out.println("Task Complete");
//		if (task_count <= 0)
//			notifyAll();
//	}
//
//	public synchronized void work_wait()
//			throws java.lang.InterruptedException {
//		while (task_count > 0) {
//			wait();
//		}
//		exec.shutdown();
//	}
//}
