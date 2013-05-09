package se751.team13.quicksort;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Util {
	private static final Random r = new Random(System.currentTimeMillis());

	public static final List<Integer> generateRandomNumbers(int amount) {
		List<Integer> nums = new ArrayList<Integer>(amount);

		for (int i = 0; i < amount; i++) {
			nums.add(r.nextInt(amount));
		}

		return nums;
	}

	public static final List<Double> generateRandomDoubles(int amount) {
		List<Double> nums = new ArrayList<Double>(amount);

		for (int i = 0; i < amount; i++) {
			nums.add(r.nextDouble() * amount);
		}

		return nums;
	}

	public static final List<Integer> generateInOrderNumbers(int amount) {
		List<Integer> nums = new ArrayList<Integer>(amount);

		for (int i = 0; i < amount; i++) {
			nums.add(i);
		}

		return nums;
	}

	public static final List<Double> generateInOrderDoubles(int amount) {
		List<Double> nums = new ArrayList<Double>(amount);

		for (double i = 0; i < amount; i++) {
			nums.add(i);
		}

		return nums;
	}

	public static final boolean inOrderDouble(List<Double> list) {
		Double previous, current;
		previous = list.get(0);

		for (int i = 1; i < list.size(); i++) {
			current = list.get(i);

			if (previous.compareTo(current) > 0)
				return false;

			previous = current;
		}

		return true;
	}
	
	public static final boolean inOrderInteger(List<Integer> list) {
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
