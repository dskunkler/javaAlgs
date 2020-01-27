import edu.princeton.cs.algs4.StdOut;

public class makeGen {
	
	public static int accumulator(int[] a, int initialValue) {
		for(int i = 0; i < a.length; i++) {
			initialValue += a[i];
		}
		return initialValue;
	}

	public static void main(String[] args) {
		int[] arr = { 1, 3, 2 ,6, 8};
		int x = 0;
		x = accumulator(arr,x);
		StdOut.println(x);

	}

}
