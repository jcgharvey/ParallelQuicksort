package se751.team13.quicksort;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.junit.Before;
import org.junit.Test;

import se751.team13.quicksort.parallel.QuickSorterTask;

public class InsertionTest {

	private List<Integer> unsorted;
	private Sorter insertion;

	@Before
	public void setUp() throws Exception {
		unsorted = Arrays.asList(4, 5, 1, 7, 2, 9, 6, 1, 3);
		insertion = new InsertionSorter();
	}

	@Test
	public void testSimpleSort() {
		List<Integer> list;
		try {
			list = insertion.sort(unsorted);
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

}
