package Quicksort;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	public static int PROCESSORS = 4;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService threads = Executors.newFixedThreadPool(PROCESSORS);
		// readfile("Resources/nums1.txt");

		List<Integer> toSort = new ArrayList<Integer>();
		Random rand = new Random();
		for (int i = 0; i < 100000; i++) {
			toSort.add(rand.nextInt(1000));
		}

		List<List<Integer>> processorLists = new ArrayList<List<Integer>>();
		List<Sorter> sorterList = new ArrayList<Sorter>();
		int index = toSort.size() / PROCESSORS;
		// threads.execute(new Sorter(l));
		for (int i = 1; i < PROCESSORS + 1; i++) {
			List<Integer> l = toSort.subList(index * (i - 1), index * i);
			processorLists.add(l);
			Sorter s = new Sorter(l);
			threads.execute(s);
			sorterList.add(s);
		}

		threads.shutdown();
		for (Sorter s : sorterList) {
			printList(s.getSorted());
			System.out
					.println("=====================================================");
		}

	}

	public static List<Integer> readfile(String filename) {
		List<Integer> list = new ArrayList<Integer>();
		Path path = Paths.get(filename);
		try {
			for (String line : Files.readAllLines(path, StandardCharsets.UTF_8)) {
				for (String number : line.split(",")) {
					list.add(Integer.parseInt(number));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void printList(List<Integer> list) {
		boolean first = true;
		for (Integer i : list) {
			if (!first) {
				System.out.print(",");
			}
			System.out.print(i);
			first = false;
		}
	}
}
