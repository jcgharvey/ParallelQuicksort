package se751.team13.quicksort;

import java.util.ArrayList;
import java.util.List;

public class InplaceQuickSorter<T extends Comparable<? super T>> implements
		Sorter<T> {

	private ArrayList<T> data;

	public List<T> sort(List<T> list) {
		data = new ArrayList<T>(list);
		quickSort(0, list.size());
		return data;
	}

	private void quickSort(int base, int n) {
		int i, j;
		i = 0;
		j = n - 1;
		while (true) {
			while (data.get(base + i).compareTo(data.get(base + j)) < 0) {
				j--;
			}
			//printData();
			if (i >= j) {
				break;
			}
			swap(base, i, j);
			printData();
			i++;
			while (data.get(base + i).compareTo(data.get(base + j)) < 0) {
				i++;
			}
			//printData();
			if (i >= j) {
				i = j;
				break;
			}
			swap(base, i, j);
			printData();
			j--;
		}
		// this stuff here needs fixing.
		quickSort(base, i);
		quickSort(base + i + 1, n - i - 1);
		return;
	}

	private void swap(int base, int i, int j) {
		T t = data.get(base + i);
		data.set(base + i, data.get(base + j));
		data.set(base + j, t);
	}
	
	private void printData(){
		for (int i = 0;i<data.size() - 1;i++){
			System.out.print(data.get(i)+", ");
		}
		System.out.print(data.get(data.size()-1)+"\n");
	}
}
