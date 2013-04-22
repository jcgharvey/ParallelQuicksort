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
import java.util.concurrent.TimeUnit;

import javax.transaction.xa.Xid;

public class Main {
	private static int PROCESSORS = 4;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService threads = Executors.newFixedThreadPool(PROCESSORS);

		// readfile("Resources/nums1.txt");

		List<Integer> toSort = new ArrayList<Integer>();
		Random rand = new Random();
		for (int i = 0; i < 1000000; i++) {
			toSort.add(rand.nextInt(1000));
		}

		long start = System.currentTimeMillis();
		List<List<Integer>> processorLists = new ArrayList<List<Integer>>();
		List<Sorter> sorterList = new ArrayList<Sorter>();
		int index = toSort.size() / PROCESSORS;
		// threads.execute(new Sorter(l));
		for (int i = 1; i < PROCESSORS + 1; i++) {
			List<Integer> l = new ArrayList<Integer>(toSort.subList(index
					* (i - 1), index * i));
			processorLists.add(l);
			Sorter s = new Sorter(l);
			sorterList.add(s);
		}

		for (Sorter s : sorterList) {
			threads.execute(s);
		}

		threads.shutdown();
		try {
			threads.awaitTermination(10000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		threads = Executors.newFixedThreadPool(1);

		List<Integer> samples = new ArrayList<Integer>();

		for (Sorter s : sorterList) {
			System.out.println(s);
			samples.addAll(s.getSamples(PROCESSORS));
		}

		Sorter seqQS = new Sorter(samples);
		threads.execute(seqQS);
		
		threads.shutdown();
		try {
			threads.awaitTermination(60000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		threads = Executors.newFixedThreadPool(PROCESSORS);

		List<Integer> points = seqQS.getSamples(PROCESSORS);
		points.remove(0);

		// This is my favourite line
		List<List<List<Integer>>> sectionList = new ArrayList<List<List<Integer>>>();

		for (Sorter s : sorterList) {
			sectionList.add(s.getSections(points));
		}

		// merge the section sections at the same indices.
		sorterList.clear();

		for (int i = 0; i < PROCESSORS; i++) {
			List<Integer> l = new ArrayList<Integer>();
			for (int j = 0; j < PROCESSORS; j++) {
				l.addAll(sectionList.get(j).get(i));
			}
			Sorter s = new Sorter(l);
			threads.execute(s);
			sorterList.add(s);
		}

		threads.shutdown();
		try {
			threads.awaitTermination(10000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		long end = System.currentTimeMillis();
		
		System.out.println("time (ms): " + (end-start));
		
//		for (Sorter s : sorterList) {
//			printList(s.getSorted());
//			// System.out.println("\n=====================================================");
//		}
	}

	private static List<Integer> readfile(String filename) {
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

	private static void printList(List<Integer> list) {
		boolean first = true;
		for (Integer i : list) {
			if (!first) {
				System.out.print(",");
			}
			System.out.print(String.format("%3d", i));
			first = false;
		}
	}
}
