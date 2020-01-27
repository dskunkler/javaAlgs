import edu.princeton.cs.algs4.*;

public class StdSort {

	public static <T extends Comparable<T>> void insertionSortNM(T[] a, int n, int m) {
	   int l = n-m;
      for (int i = l; i < n; i++) {
         int idx = StdArray.upperBound(a, 0, i, a[i]);
         StdArray.rotateRight(a,idx,i+1);       
      }		
	}

  // I've included insertionSort for reference.
	public static <T extends Comparable<T>> void insertionSort(T[] a, int lo, int hi) {
		for (int i = lo+1; i < hi; i++) {
			// invariant: a[lo,i) is sorted
			int idx = StdArray.upperBound(a, lo, i, a[i]);
			StdArray.rotateRight(a, idx, i+1);
		}
	}

 	private static <T extends Comparable<T>> boolean less(T a, T b) {
		return a.compareTo(b) < 0;
	}
 	
 	public static <T extends Comparable<T>> void quickSort3Way(T[] a, int lo, int hi) {
		if (hi <= lo)
			return;

		T p = a[lo];
		int i = lo; // see hint 1
		int j = i+1;
		int k = hi;

		while (k-j >= 1) { // see hint 2
			int c = a[j].compareTo(p);
			if (c < 0)
				StdArray.swap(a,i++,j++);
				// see hint 3
			else if (c > 0)
				StdArray.swap(a,j,--k);
			else
				j++;
		}

		quickSort3Way(a,lo,i); // see hint 4
		quickSort3Way(a,j,hi);
	}


	public static <T extends Comparable<T>> void quickSort3Way(T[] a) {
		StdRandom.shuffle(a);
		quickSort3Way(a, 0, a.length);
	}
}
