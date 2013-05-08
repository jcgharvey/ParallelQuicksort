package se751.team13.quicksort;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;

import se751.team13.quicksort.parallel.InternetDump;
import se751.team13.quicksort.parallel.InternetDump.Manager;

public class SorterTest {

	private List<Integer> unsorted;
	private Sorter sorter;

	@Before
	public void setUp() throws Exception {
		unsorted = Arrays.asList(4, 5, 1, 7, 2, 9, 6, 1, 3);

	}

	@Test
	public void testInsertionSort() {
		sorter = new InsertionSorter();
		List<Integer> list;
		try {
			list = sorter.sort(unsorted);
			if (!Util.inOrder(list)) {
				fail();
			} else {
				assertTrue(true);
			}
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception");
		}
	}

	@Test
	public void testInplaceQuickSort() {
		sorter = new InplaceQuickSorter();
		List<Integer> list;
		try {
			list = sorter.sort(unsorted);
			if (!Util.inOrder(list)) {
				fail();
			} else {
				assertTrue(true);
			}
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception");
		}
	}

	@Test
	public void testInternetDump() {
		int[] data = new int[100000];

		int RANGE = 10000;

		Random rand = new Random();

		for (int i = 0; i < 100000; i++) {
			data[i] = rand.nextInt(RANGE);
		}
		long start,end;
		
		ExecutorService exec = Executors.newFixedThreadPool(Runtime
				.getRuntime().availableProcessors());
		Manager mgr = new Manager(exec);
		InternetDump qs = new InternetDump(data, mgr);
		start = System.currentTimeMillis();
		exec.execute(qs);
		try {
			mgr.work_wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		end = System.currentTimeMillis();
		System.out.println("time(ms):" + (end - start));
		data = qs.getData();
		int last = 0;
		for (int i = 0; i < data.length; i++) {
			if (data[i] < last) {
				fail("Not in order");
			}
			last = data[i];
		}
		assertTrue(true);
	}

}
