package se751.team13.realquicksort;

import java.util.ArrayList;
import java.util.List;

/*
 * QuicksortDemo.java -- run and measure execution time of Quicksort. 
 */

class QuickSortDemo {
	public static void main(String argv[])
			throws java.lang.InterruptedException {
		int matrix_size = 10000000;
		int nthreads = Runtime.getRuntime().availableProcessors();
		run_quicksort(matrix_size, nthreads);
		System.exit(0);
	}

	static void run_quicksort(int matrix_size, int nthreads)
			throws java.lang.InterruptedException {
		double[] data = new double[matrix_size];//{7.0,8.0,5.0,0.0,2.0,9.0,1.0,4.0,6.0,3.0};//
		data_fill(data, matrix_size);
		dataInOrder(data);
		long start = System.currentTimeMillis();
		QuickSort qs = new QuickSort(nthreads);
		qs.add_task(data, 0, data.length - 1);
		qs.work_wait();
		long end = System.currentTimeMillis();
		double diff = (double) (end - start) / 1000.0;
		System.out.println("Time: " + diff + "s");
		dataInOrder(data);
		//data_print(data);
		System.out.println("DONE");
	}

	static void dataInOrder(double data[]){
		double last = data[0];
		for (int i = 0; i < data.length; i++) {
			if (data[i] < last) {
				System.out.println("NOT IN ORDER");
				break;
			}
			last = data[i];
		}
	}
	
	static void data_fill(double data[], int size) {
		int i;
		java.util.Random r = new java.util.Random();
		for (i = 0; i < size; i++) {
			data[i] = r.nextDouble();
		}
	}

	static void data_print(double data[]) {
		int i;
		for (i = 0; i < data.length; i++) {
			System.out.printf("%4.2f ", data[i]);
		}
		System.out.printf("\n");
	}
}