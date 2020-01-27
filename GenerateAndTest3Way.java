
import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class GenerateAndTest3Way {
	private static <T extends Comparable<T>> boolean isSorted(T[] a, int lo, int hi) {
		for (int i = lo + 1; i < hi; i++)
			if (a[i - 1].compareTo(a[i]) > 0)
				return false;
		return true;
	}

	private static <T extends Comparable<T>> boolean isSorted(T[] a) {
		return isSorted(a, 0, a.length);
	}

	private static void timeQuicksort(int[] a, int n, int m) {
		Integer[] aInt = new Integer[n];
		for (int i = 0; i < n; i++)
			aInt[i] = a[i];
		Stopwatch sw = new Stopwatch();
		Quick.sort(aInt);
		double elapsed = sw.elapsedTime();
		StdOut.printf("Quicksort     : %d, %d, %7.3f\n", n, m, elapsed);
	}

	private static void timeQuicksort3Way(int[] a, int n, int m) {
		Integer[] aInt = new Integer[n];
		for (int i = 0; i < n; i++)
			aInt[i] = a[i];
		Stopwatch sw = new Stopwatch();
		StdSort.quickSort3Way(aInt);
		if (!isSorted(aInt))
			StdOut.println("Sort failure!\n");
		double elapsed = sw.elapsedTime();
		StdOut.printf("Quicksort3way : %d, %d, %7.3f\n", n, m, elapsed);
	}
	
	public static void main(String[] args) {
		
		// Get n and m from the command line, and allocate the array.
		int n = Integer.parseInt(args[0]);
		int m = Integer.parseInt(args[1]);
		int[] a = new int[n];
		
		// Generate a random array.  Note that we set a seed so that
		// we get predictable values each time.
		StdRandom.setSeed(0);
		for (int i = 0; i < n; i++)
			a[i] = StdRandom.uniform(0, m);
		
		// Test the sorts.
		timeQuicksort(a, n, m);
		timeQuicksort3Way(a, n, m);
		StdOut.println("Done");
	}
}
