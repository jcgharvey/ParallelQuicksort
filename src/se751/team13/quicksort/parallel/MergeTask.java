package se751.team13.quicksort.parallel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MergeTask<T extends Comparable<? super T>> implements Runnable {
	private final List<List<T>> unsorted;
	private List<T> sortedList = new ArrayList<T>();
	private CyclicBarrier barrier;

	public MergeTask(List<List<T>> l, CyclicBarrier barrier) {
		this.unsorted = l;
		this.barrier = barrier;
	}

	public Collection<? extends T> getSortedList() {
		// TODO Auto-generated method stub
		return sortedList;
	}

	@Override
	public void run() {
		List<List<T>> unsortedClean = new ArrayList<List<T>>();
		for(List<T> s : unsorted){
			if(!s.isEmpty()){
			unsortedClean.add(s);
			}
		}
		while (unsortedClean.size() > 1) {
			int count = 0;
			int minIndex = 0;

			T min = unsortedClean.get(0).get(0);
			for (List<T> current : unsortedClean) {

				if ((current.get(0)).compareTo(min) < 0) {
					min = current.get(0);
					minIndex = count;
				}

				count++;
			}

			sortedList.add(unsortedClean.get(minIndex).remove(0));
			if (unsortedClean.get(minIndex).size() == 0) {
				unsortedClean.remove(minIndex);
			}
		}
		sortedList.addAll(unsortedClean.get(0));
		if (barrier == null) {
			return;
		}

		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			return;
		}
		// TODO Auto-generated method stub

	}

}
