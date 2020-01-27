import edu.princeton.cs.algs4.*;
import java.util.Arrays;

public class GenerateAndTestNM {

	private static <T extends Comparable<T>> boolean isSorted(T[] a, int lo, int hi) {
		for (int i = lo + 1; i < hi; i++)
			if (a[i - 1].compareTo(a[i]) > 0)
				return false;
		return true;
	}

	private static <T extends Comparable<T>> boolean isSorted(T[] a) {
		return isSorted(a, 0, a.length);
	}

	private static void timeMergeSort(int[] a, int n, int m) {
		Integer[] aInt = new Integer[n];
		for (int i = 0; i < n; i++)
			aInt[i] = a[i];
		Stopwatch sw = new Stopwatch();
		Merge.sort(aInt);
		double elapsed = sw.elapsedTime();
		StdOut.printf("Merge     sort : %d, %d, %7.3f\n", n, m, elapsed);
	}

	private static void timeInsertionSortNM(int[] a, int n, int m) {
		Integer[] aInt = new Integer[n];
		for (int i = 0; i < n; i++)
			aInt[i] = a[i];
		Stopwatch sw = new Stopwatch();
		StdSort.insertionSortNM(aInt, n, m);
		if (!isSorted(aInt))
			StdOut.println("Sort failure!\n");
		double elapsed = sw.elapsedTime();
		StdOut.printf("Insertion sort : %d, %d, %7.3f\n", n, m, elapsed);
	}

//	private static void timeMergeSortNM(int[] a, int n, int m) {
//		Integer[] aInt = new Integer[n];
//		for (int i = 0; i < n; i++)
//			aInt[i] = a[i];
//		Stopwatch sw = new Stopwatch();
//		Sort.MergeSortNM(aInt, n, m);
//		if (!isSorted(aInt))
//			StdOut.println("Sort failure!\n");
//		double elapsed = sw.elapsedTime();
//		StdOut.printf("MergesortNM    : %d, %d, %7.3f\n", n, m, elapsed);
//	}

	public static void main(String[] args) {
		
		// Get n and m from the command line, and allocate the array.
		int n = Integer.parseInt(args[0]);
		int m = Integer.parseInt(args[1]);
		int[] a = new int[n];
		
		// Generate a random array.  Note that we set a seed so that
		// we get predictable values each time.
		StdRandom.setSeed(0);
		for (int i = 0; i < n; i++)
			a[i] = StdRandom.uniform(-n, n);
		
		// Sort the first n-m elements.
		Arrays.sort(a, 0, n-m);
		
		// Test the sorts.
		timeMergeSort(a, n, m);
		timeInsertionSortNM(a, n, m);
//		timeMergeSortNM(a, n, m);
	}
}
