package se751.team13.quicksort.parallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class InternetDump implements Runnable {
	int data[];
	int base;
	int n;
	Manager manager;

	public InternetDump(int data[], int base, int n, Manager manager) {
		this.data = data;
		this.base = base;
		this.n = n;
		this.manager = manager;
	}

	public InternetDump (int data[]){
		this.data = data;
		this.base = 0;
		this.n = data.length;
		this.manager = new Manager(Runtime.getRuntime().availableProcessors());
	}
	
	public InternetDump (int data[], Manager manager){
		this.data = data;
		this.base = 0;
		this.n = data.length;
		this.manager = manager;
	}
	
	static final int SORT_DIRECT = 100;

	public void run() {
		int i, j;
		if (n <= SORT_DIRECT) {
			for (j = 1; j < n; j++) {
				int key = data[base + j];
				for (i = j - 1; i >= 0 && key < data[base + i]; i--)
					data[base + i + 1] = data[base + i];
				data[base + i + 1] = key;
			}
			manager.task_done();
			return;
		}
		i = 0;
		j = n - 1;
		while (true) {
			while (data[base + i] < data[base + j])
				j--;
			if (i >= j)
				break;
			{
				int t = data[base + i];
				data[base + i] = data[base + j];
				data[base + j] = t;
			} /* swap */
			i++;
			while (data[base + i] < data[base + j])
				i++;
			if (i >= j) {
				i = j;
				break;
			}
			{
				int t = data[base + i];
				data[base + i] = data[base + j];
				data[base + j] = t;
			} /* swap */
			j--;
		}
		manager.add_task(data, base, i);
		manager.add_task(data, base + i + 1, n - i - 1);
		manager.task_done();
	}

	public int[] getData(){
		return data;
	}
	
	public class Manager {
		int task_count;
		ExecutorService exec;

		public Manager(int n_threads) {
			task_count = 0;
			exec = Executors.newFixedThreadPool(n_threads);
		}
		public Manager() {
			task_count = 0;
			exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		}
		
		public Manager(ExecutorService exec) {
			task_count = 0;
			this.exec = exec;
		}

		public synchronized void add_task(int data[], int base, int n) {
			task_count++;
			System.out.println("Task Created");
			Runnable task = new InternetDump(data, base, n, this);
			exec.execute(task);
		}

		public synchronized void task_done() {
			task_count--;
			System.out.println("Task Complete");
			if (task_count <= 0)
				notifyAll();
		}

		public synchronized void work_wait()
				throws java.lang.InterruptedException {
			while (task_count > 0) {
				wait();
			}
			exec.shutdown();
		}
	}
}