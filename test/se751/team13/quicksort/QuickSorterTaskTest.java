package se751.team13.quicksort;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.junit.Before;
import org.junit.Test;

import se751.team13.quicksort.parallel.QuickSorterTask;

public class QuickSorterTaskTest {
	private QuickSorterTask<Integer> task;
	private List<Integer> unsorted;
	private CyclicBarrier barrier;

	@Before
	public void setUp() throws Exception {
		barrier = new CyclicBarrier(1);
		unsorted = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

		task = new QuickSorterTask<Integer>(unsorted, barrier);
		task.run();
	}

	@Test
	public void testGetSamples() {
		List<Integer> samples = task.getSamples(2);
		assertArrayEquals(new Object[] { 1, 5 }, samples.toArray());
	}

	@Test
	public void testGetSections() throws InterruptedException,
			BrokenBarrierException {
		List<List<Integer>> sections = task.getSections(Arrays.asList(1, 5));
		
		System.out.println(sections);
	}
}
