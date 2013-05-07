package se751.team13.quicksort;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Util {
	public static final List<Integer> generateRandomNumbers(int amount) {
		Random rand = new Random(System.currentTimeMillis());
		List<Integer> nums = new ArrayList<Integer>();

		for (int i = 0; i < amount; i++) {
			nums.add(rand.nextInt(amount));
		}

		return nums;
	}

	public static final List<Integer> generateInOrderNumbers(int amount) {
		List<Integer> nums = new ArrayList<Integer>();

		for (int i = 0; i < amount; i++) {
			nums.add(i);
		}

		return nums;
	}

	public static final boolean inOrder(List<Integer> list) {
		Integer previous, current;
		previous = list.get(0);

		for (int i = 1; i < list.size(); i++) {
			current = list.get(i);

			if (previous.compareTo(current) > 0)
				return false;

			previous = current;
		}

		return true;
	}
}
