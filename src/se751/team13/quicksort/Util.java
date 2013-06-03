package se751.team13.quicksort;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Util {
	private static final Random r = new Random(System.currentTimeMillis());

	/**
	 * Generates random Integers
	 * @param amount
	 * @return
	 */
	public static final List<Integer> generateRandomNumbers(int amount) {
		List<Integer> nums = new ArrayList<Integer>(amount);

		for (int i = 0; i < amount; i++) {
			nums.add(r.nextInt(amount));
		}

		return nums;
	}

	/**
	 * Generates random Doubles
	 * @param amount
	 * @return
	 */
	public static final List<Double> generateRandomDoubles(int amount) {
		List<Double> nums = new ArrayList<Double>(amount);

		for (int i = 0; i < amount; i++) {
			nums.add(r.nextDouble() * amount);
		}

		return nums;
	}

	/**
	 * Generates Inorder Integers
	 * @param amount
	 * @return
	 */
	public static final List<Integer> generateInOrderNumbers(int amount) {
		List<Integer> nums = new ArrayList<Integer>(amount);

		for (int i = 0; i < amount; i++) {
			nums.add(i);
		}

		return nums;
	}

	/**
	 * Return true if a given list is inorder
	 * @param list
	 * @return
	 */
	public static final boolean inOrder(List<Integer> list) {
		int previous, current;
		previous = list.get(0);

		for (int i = 1; i < list.size(); i++) {
			current = list.get(i);

			if (previous > current)
				return false;

			previous = current;
		}

		return true;
	}
}
