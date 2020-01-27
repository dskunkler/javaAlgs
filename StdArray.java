
import java.util.Comparator;
import java.util.Iterator;

public class StdArray {

	// TODO: incorporate comments and general versions of rotate
	// from fall 2017 ClassDemos.

	// NOTE: In preconditions, I use a shortcut syntax to refer to
	// everything in a half-open range of an array.  The notation
	// a[lo,hi) refers to every element of array a in the range [lo,hi).
	// For example
	//
	//		a[lo,hi) < 0
	//
	// is a shortcut for "every element a[i] with lo <= i < hi
	// is less than 0".

	// checkArguments() is used to check for preconditions; it checks to
	// make sure the array is not null.
	private static <T> boolean checkArguments(T[] a, boolean allowEmpty) {
		assert (a != null);
		assert (allowEmpty || a.length > 0);
		return true;
	}

	// checkArguments() is used to check for preconditions; it checks to
	// make sure the array is not null, and that 'lo' and 'hi' are within
	// the bounds of the array.
	private static <T> boolean checkArguments(T[] a, int lo, int hi, boolean allowEmpty) {
		assert (a != null);
		assert ((lo >= 0) && (lo <= a.length));
		assert ((hi >= 0) && (hi <= a.length));
		assert (allowEmpty || hi - lo > 0);
		return true;
	}

	// find(a, lo, hi, p) returns the index of the first element in
	// the unordered sub-array a[lo,hi) for which the predicate 'p'
	// returns true. Returns -1 if the predicate is false for all
	// elements of a[lo,hi).
	// Preconditions:
	//		a != null or lo == hi
	//		a[lo,hi) != null
	//		0 <= lo <= hi <= a.length
	// Postconditions (let 'r' be the return value):
	//		(r == -1) and (!p(a[lo,hi)))
	//			or
	//		(r != -1) and lo <= r < hi and (p(a[r])) and !p(a[lo,r)))
	public static <T> int find(T[] a, int lo, int hi, Predicate<T> p) {
		assert (checkArguments(a, lo, hi, true));
		for (int i = lo; i < hi; i++)
			// !p(a[lo,i))
			if (p.test(a[i]))
				// !p(a[lo,i)) and p(a[i])
				return i;
		// !p(a[lo,hi))
		return -1;
	}

	// find(a, p) returns the index of the first element of
	// the unordered array 'a' for which the predicate returns
	// true. Returns -1 if the predicate is false for all
	// elements of 'a'.
	// Preconditions:
	//		a != null
	//		a[0,a.length) != null
	// Postconditions (let 'r' be the return value):
	//		(r == -1) and !p(a[0,a.length))
	//			or
	//		(r != -1) and 0 <= r < a.length and (p(a[r])) and !p(a[0,r)))
	public static <T> int find(T[] a, Predicate<T> p) {
		assert (checkArguments(a, true));
		return find(a, 0, a.length, p);
	}

	// find(a, lo, hi, value) returns the index of the first element in
	// the unordered sub-array a[lo,hi) that matches 'value'. Returns -1
	// if 'value' is not found in a[lo,hi).
	// Preconditions:
	//		a != null or lo == hi
	//		a[lo,hi) != null
	//		0 <= lo <= hi <= a.length
	// Postconditions (let 'r' be the return value):
	//		(r == -1) and a[lo,hi) != value)
	//			or
	//		(r != -1) and 0 <= r < a.length and (a[r] == value) and a[lo,r) != value)
	public static <T> int find(T[] a, int lo, int hi, T value) {
		assert (checkArguments(a, lo, hi, true));
		return find(a, lo, hi, new IsEqual<T>(value));
	}

	// find(a, p) returns the index of the first
	// occurrence of 'value' in the unordered array 'a'.
	// Returns -1 if 'value' is not found.
	// Preconditions:
	//		a != null
	//		a[0,a.length) != null
	// Postconditions (let 'r' be the return value):
	//		(r == -1) and (a[0,a.length) != value)
	//			or
	//		(r != -1) and 0 <= r < a.length and (a[r] == value) and (a[0,r) != value)
	public static <T> int find(T[] a, T value) {
		assert (checkArguments(a, true));
		return find(a, 0, a.length, value);
	}

	// count(a, lo, hi, p) returns the number of elements of the
	// unordered subarray a[lo,hi) for which the predicate 'p'
	// returns true.
	// Preconditions:
	//		a != null or lo == hi
	//		a[lo,hi) != null
	//		0 <= lo <= hi <= a.length
	// Postconditions (let 'r' be the return value):
	//		p(a[i] is true for r values in [lo,hi)
	public static <T> int count(T[] a, int lo, int hi, Predicate<T> p) {
		assert (checkArguments(a, lo, hi, true));
		int count = 0;
		for (int i = lo; i < hi; i++)
			// p(a[j]) is true for 'count' items in a[lo,i)
			if (p.test(a[i]))
				count++;
		// p(a[j]) is true for 'count' times in a[lo,hi)
		return count;
	}

	// count(a, p) returns the number of elements of the unordered
	// array 'a' for which the predicate 'p' returns true.
	// Preconditions:
	//		a != null
	//		a[0,a.length) != null
	// Postconditions (let 'r' be the return value):
	//		p(a[i] is true for r values in [0,a.length)
	public static <T> int count(T[] a, Predicate<T> p) {
		assert (checkArguments(a, true));
		return count(a, 0, a.length, p);
	}

	// count(a, lo, hi, p) returns the number of elements of the
	// unordered subarray a[lo,hi) that are equal to 'value'.
	// Preconditions:
	//		a != null or lo == hi
	//		a[lo,hi) != null
	//		0 <= lo <= hi <= a.length
	// Postconditions (let 'r' be the return value):
	//		value occurs r times in the a[lo,hi)
	public static <T> int count(T[] a, int lo, int hi, T value) {
		assert (checkArguments(a, lo, hi, true));
		return count(a, lo, hi, new IsEqual<T>(value));
	}

	// count(a, p) returns the number of elements of the unordered
	// array 'a' that are equal to 'value'.
	// Preconditions:
	//		a != null
	//		a[0,a.length) != null
	// Postconditions (let 'r' be the return value):
	//		value occurs r times in the array
	public static <T> int count(T[] a, T value) {
		assert (checkArguments(a, true));
		return count(a, 0, a.length, value);
	}

	// minElement returns the index of the smallest
	// element of the [lo,hi) subrange of the unordered
	// array 'a'. If there are multiple occurrences of
	// the minimum element, the index of the first
	// (lowest-indexed) one is returned.
	// Preconditions:
	//		a != null
	//		a[lo,hi) != null
	//		0 <= lo <= a.length
	//		0 <= hi <= a.length
	//		hi - lo > 0
	// Postconditions (let 'r' be the return value):
	//		lo <= r < hi
	//		a[lo,r) > a[r]
	//		a[r,hi) >= a[r]
	public static <T> int minElement(T[] a, int lo, int hi, Comparator<T> c) {
		assert (checkArguments(a, lo, hi, false));
		int idxMin = lo;
		for (int i = lo + 1; i < hi; i++)
			// for i in [0,idxMin) a[i] > a[idxMin]
			// for i in [idxMin,i) a[i] >= a[idxMin]
			if (c.compare(a[i], a[idxMin]) < 0)
				idxMin = i;
		// for i in [0,idxMin) a[i] > a[idxMin]
		// for i in [idxMin,i] a[i] >= a[idxMin]
		// for i in [0,idxMin) a[i] > a[idxMin]
		// for i in [idxMin,hi) a[i] >= a[idxMin]
		return idxMin;
	}

	// minElement returns the index of the smallest
	// element of the unordered array 'a'. If there
	// are multiple occurrences of the minimum element,
	// the index of the first (lowest-indexed) one is
	// returned.
	// Preconditions:
	//		a != null
	//		a[0,a.length) != null
	//		a.length > 0
	// Postconditions (let 'r' be the return value):
	//		for i in [0,r) a[i] > a[r]
	//		for i in [r,a.length) a[i] >= a[r]
	public static <T> int minElement(T[] a, Comparator<T> c) {
		assert (checkArguments(a, false));
		return minElement(a, 0, a.length, c);
	}

	// minElement returns the index of the smallest
	// element of the [lo,hi) subrange of the unordered
	// array 'a'. If there are multiple occurrences of
	// the minimum element, the index of the first
	// (lowest-indexed) one is returned.
	// Preconditions:
	// a != null
	// 0 <= lo <= a.length
	// 0 <= hi <= a.length
	// hi - lo > 0
	// Postconditions (let 'r' be the return value):
	// for i in [lo,r) a[i] > a[r]
	// for i in [r,hi) a[i] >= a[r]
	public static <T extends Comparable<T>> int minElement(T[] a, int lo, int hi) {
		assert (checkArguments(a, lo, hi, false));
		return minElement(a, lo, hi, new WrapComparable<T>());
	}

	// minElement returns the index of the smallest
	// element of the unordered array 'a'. If there
	// are multiple occurrences of the minimum element,
	// the index of the first (lowest-indexed) one is
	// returned.
	// Preconditions:
	// a != null
	// a.length > 0
	// Postconditions (let 'r' be the return value):
	// for i in [0,r) a[i] > a[r]
	// for i in [r,a.length) a[i] >= a[r]
	public static <T extends Comparable<T>> int minElement(T[] a) {
		assert (checkArguments(a, false));
		return minElement(a, 0, a.length);
	}

	// maxElement returns the index of the largest
	// element of the [lo,hi) subrange of the unordered
	// array 'a'. If there are multiple occurrences of
	// the maximum element, the index of the first
	// (lowest-indexed) one is returned.
	// Preconditions:
	// a != null
	// 0 <= lo <= a.length
	// 0 <= hi <= a.length
	// hi - lo > 0
	// Postconditions (let 'r' be the return value):
	// for i in [lo,r) a[i] < a[r]
	// for i in [r,hi) a[i] <= a[r]
	public static <T> int maxElement(T[] a, int lo, int hi, Comparator<T> c) {
		assert (checkArguments(a, lo, hi, false));
		int idxMax = lo;
		for (int i = lo + 1; i < hi; i++)
			// for i in [0,idxMin) a[i] < a[idxMax]
			// for i in [idxMin,i) a[i] <= a[idxMax]
			if (c.compare(a[i], a[idxMax]) > 0)
				idxMax = i;
		// for i in [0,idxMin) a[i] < a[idxMax]
		// for i in [idxMin,i] a[i] <= a[idxMax]
		// for i in [0,idxMin) a[i] < a[idxMax]
		// for i in [idxMin,hi) a[i] <= a[idxMax]
		return idxMax;
	}

	// maxElement returns the index of the largest
	// element of the unordered array 'a'. If there
	// are multiple occurrences of the maximum element,
	// the index of the first (lowest-indexed) one is
	// returned
	// Preconditions:
	// a != null
	// a.length > 0
	// Postconditions (let 'r' be the return value):
	// for i in [0,r) a[i] < a[r]
	// for i in [r,a.length) a[i] <= a[r]
	public static <T> int maxElement(T[] a, Comparator<T> c) {
		assert (checkArguments(a, false));
		return maxElement(a, 0, a.length, c);
	}

	// maxElement returns the index of the largest
	// element of the [lo,hi) subrange of the unordered
	// array 'a'. If there are multiple occurrences of
	// the maximum element, the index of the first
	// (lowest-indexed) one is returned.
	// Preconditions:
	// a != null
	// 0 <= lo <= a.length
	// 0 <= hi <= a.length
	// hi - lo > 0
	// Postconditions (let 'r' be the return value):
	// for i in [lo,r) a[i] < a[r]
	// for i in [r,hi) a[i] <= a[r]
	public static <T extends Comparable<T>> int maxElement(T[] a, int lo, int hi) {
		assert (checkArguments(a, lo, hi, false));
		return maxElement(a, lo, hi, new WrapComparable<T>());
	}

	// maxElement returns the index of the largest
	// element of the unordered array 'a'. If there
	// are multiple occurrences of the maximum element,
	// the index of the first (lowest-indexed) one is
	// returned
	// Preconditions:
	// a != null
	// a.length > 0
	// Postconditions (let 'r' be the return value):
	// for i in [0,r) a[i] < a[r]
	// for i in [r,a.length) a[i] <= a[r]
	public static <T extends Comparable<T>> int maxElement(T[] a) {
		assert (checkArguments(a, false));
		return maxElement(a, 0, a.length);
	}

	// swap exchanges the values at indices idx1 and idx2
	// in array a.
	// Preconditions:
	// a != null
	// a.length > 0
	// 0 <= idx1 < a.length
	// 0 <= idx2 < a.length
	// Postconditions (let a' be the original array):
	// for i in [0, length)
	// if i not in {idx1,idx2} a[i] = a'[i]
	// a[idx1] = a'[idx2]
	// a[idx2] = a'[idx1]
	public static <T> void swap(T[] a, int idx1, int idx2) {
		T t = a[idx1];
		a[idx1] = a[idx2];
		a[idx2] = t;
	}

	// copy copies the subrange aFrom[lo,hi) to aTo[lo,hi). All
	// other elements of aTo are untouched.
	// Preconditions:
	// aFrom != null
	// aTo != null
	// aFrom.length > 0
	// aTo.length > 0
	// 0 <= lo < aFrom.length
	// 0 <= hi < aFrom.length
	// 0 <= lo < aTo.length
	// 0 <= hi < aTo.length
	// Postconditions (let aTo' and aFrom' be the original aTo and aFrom):
	// for i in [0,a.length) aFrom[i] == aFrom'[i]
	// for i in [0,lo) aTo[i] = aTo'[i]
	// for i in [lo,hi) aTo[i] = aFrom[i]
	// for i in [hi,aTo.length) aTo[i] = aTo'[i]
	public static <T> void copy(T[] aFrom, T[] aTo, int lo, int hi) {
		assert (checkArguments(aFrom, lo, hi, true));
		assert (checkArguments(aTo, lo, hi, true));
		for (int i = lo; i < hi; i++)
			aTo[i] = aFrom[i];
	}

	// copy copies the subrange aFrom[loFrom,hiFrom) to
	// aTo[aTo,aTo+(hiFrom-loFrom)). All other elements
	// of aTo are untouched.
	// Preconditions:
	// aFrom != null
	// aTo != null
	// aFrom.length > 0
	// aTo.length > 0
	// 0 <= loFrom < aFrom.length
	// 0 <= hiFrom < aFrom.length
	// 0 <= loTo < aTo.length
	// 0 <= loTo+(hiFrom-loFrom) < aTo.length
	// Postconditions (let aTo' and aFrom' be the original aTo and aFrom):
	// let hiTo = loTo + (hiFrom - loFrom)
	// for i in [0,a.length) aFrom[i] == aFrom'[i]
	// for i in [0,loTo) aTo[i] = aTo'[i]
	// for i in [loTo,hiTo) aTo[i] = aFrom[i]
	// for i in [hiTo,aTo.length) aTo[i] = aTo'[i]
	public static <T> void copy(T[] aFrom, int loFrom, int hiFrom, T[] aTo, int loTo) {
		assert (checkArguments(aFrom, loFrom, hiFrom, true));
		assert (checkArguments(aTo, loTo, loTo + (hiFrom - loFrom), true));
		for (int iFrom = loFrom, iTo = loTo; iFrom < hiFrom; iFrom++, iTo++) {
			aTo[iTo] = aFrom[iFrom];
		}
	}

	/**
	 * Moves <tt>aFrom[loFrom,hiFrom)</tt> to <tt>aTo[loTo,loTo+(hiFrom-hTo))</tt>.
	 * The elements in aFrom are set to null.
	 * 
	 * @param aFrom
	 *			the array to move from
	 * @param loFrom
	 * 			the low index of the source range
	 * @param hiFrom
	 * 			the high index of the source range
	 * @param aTo
	 *			the array to move to
	 * @param loTo
	 *			the low index of the target range
	 */
	public static <T> void move(T[] aFrom, int loFrom, int hiFrom, T[] aTo, int loTo) {
		
		// Check preconditions
		assert(aFrom != null);
		assert(aTo != null);
		assert(loFrom <= hiFrom);
		assert((0 <= loFrom) && (loFrom <= aFrom.length));
		assert((0 <= hiFrom) && (hiFrom <= aFrom.length));
		assert((0 <= loTo) && (loTo <= aTo.length));
		assert(loTo + (hiFrom - loFrom) <= aTo.length);

		while (loFrom < hiFrom) {
			aTo[loTo++] = aFrom[loFrom];
			aFrom[loFrom++] = null;
		}
	}

	/**
	 * Moves <tt>aFrom[lo,hi)</tt> to <tt>aTo[lo,hi)</tt>.  The elements in
	 * aFrom are set to null.
	 * 
	 * @param aFrom
	 *            the array to move from
	 * @param aTo
	 *            the array to move to
	 * @param lo
	 *            the start of the range to copy
	 * @param hi
	 *            the end of the range to copy
	 */
	public static <T> void move(T[] aFrom, T[] aTo, int lo, int hi) {
		
		// Check preconditions
		assert(aFrom != null);
		assert(aTo != null);
		assert(lo <= hi);
		assert((0 <= lo) && (lo <= aFrom.length) && (lo <= aTo.length));
		assert((0 <= hi) && (hi <= aFrom.length) && (hi <= aTo.length));

		move(aFrom, lo, hi, aTo, lo);
	}

	// rotateRight rotates the array range [lo,hi) one element
	// to the right, with a[hi-1] rotating to a[lo].
	// Preconditions:
	// a != null
	// 0 <= lo <= a.length
	// 0 <= hi <= a.length
	// hi - lo > 0
	// Postconditions (let a' be the original array):
	// for i in [lo+1,hi) a[i] < a'[i-1]
	// a[lo] = a'[hi-1]
	public static <T> void rotateRight(T[] a, int lo, int hi) {
		checkArguments(a, lo, hi, true);
		if (hi - lo < 2)
			return;
		T t = a[hi - 1];
		for (int i = hi - 1; i > lo; i--) {
			a[i] = a[i - 1];
		}
		a[lo] = t;
	}

	// rotateLeft rotates the array range [lo,hi) one element
	// to the left, with a[lo] rotating to a[hi-1].
	// Preconditions:
	// a != null
	// 0 <= lo <= a.length
	// 0 <= hi <= a.length
	// hi - lo > 0
	// Postconditions (let a' be the original array):
	// for i in [lo,hi-1) a[i] < a'[i+1]
	// a[hi-1] = a'[lo]
	public static <T> void rotateLeft(T[] a, int lo, int hi) {
		checkArguments(a, lo, hi, false);
		if (hi - lo < 2)
			return;
		T t = a[lo];
		for (int i = lo + 1; i < hi; i++)
			a[i - 1] = a[i];
		a[hi - 1] = t;
	}

	// isOrdered returns true if the given array is ordered according
	// to the given Comparator, and false otherwise.
	public static <T> boolean isOrdered(T[] a, Comparator<T> c) {
		for (int i = 1; i < a.length; i++) {
			if (c.compare(a[i - 1], a[i]) > 0)
				return false;
		}
		return true;
	}

	public static <T extends Comparable<T>> boolean isOrdered(T[] a) {
		return isOrdered(a, new WrapComparable<T>());
	}

	// lowerBound returns the index of the first (lowest-index)
	// element of sub-array a[lo,hi) that is greater than or equal
	// to the given value.
	// Preconditions:
	// 		a != null
	// 		0 <= lo <= a.length
	// 		0 <= hi <= a.length
	// 		hi - lo > 0
	// Postconditions (let r be the return value):
	// 		for i in [lo,r) a[i] < value
	// 		for i in [r,hi) a[i] >= value
	public static <T> int lowerBound(T[] a, int lo, int hi, T value, Comparator<T> c) {
		assert (checkArguments(a, lo, hi, false));
		assert (isOrdered(a, c));
		// In all invariants, lo' and hi' are the original values
		// of lo and hi.
		// Note that due to the invariant that mid is in [lo,hi),
		// we will never get lo > hi.
		while (lo < hi) {
			// for all j in [lo',lo) a[mid] < value
			// for all j in [hi,hi') a[mid] >= value
			int mid = lo + (hi - lo) / 2;
			// mid in [lo,hi)
			if (c.compare(a[mid], value) < 0)
				lo = mid + 1;
			// for all j in [lo',lo) a[mid] < value
			// NOTE: mid + 1 is important to guarantee termination!
			else
				hi = mid;
			// for all j in [hi,hi') a[mid] >= value
		}
		// for all j in [lo',hi) a[mid] < value
		// for all j in [hi,hi') a[mid] >= value
		return hi;
	}

	// lowerBound returns the index of the first (lowest-index)
	// element of array 'a' that is greater than or equal to the
	// given value.
	// Preconditions:
	// a != null
	// a.length > 0
	// Postconditions (let r be the return value):
	// for i in [0,r) a[i] < value
	// for i in [r,a.length) a[i] >= value
	public static <T> int lowerBound(T[] a, T value, Comparator<T> c) {
		assert (checkArguments(a, false));
		assert (isOrdered(a, c));
		return lowerBound(a, 0, a.length, value, c);
	}

	// lowerBound returns the index of the first (lowest-index)
	// element of sub-array a[lo,hi) that is greater than or equal
	// to the given value.
	// Preconditions:
	// 		a != null
	// 		0 <= lo <= a.length
	// 		0 <= hi <= a.length
	// 		hi - lo > 0
	// Postconditions (let r be the return value):
	// 		for i in [lo,r) a[i] < value
	// 		for i in [r,hi) a[i] >= value
	public static <T extends Comparable<T>> int lowerBound(T[] a, int lo, int hi, T value) {
		assert (checkArguments(a, lo, hi, false));
		assert (isOrdered(a));
		return lowerBound(a, lo, hi, value, new WrapComparable<T>());
	}

	// lowerBound returns the index of the first (lowest-index)
	// element of array 'a' that is greater than or equal to the
	// given value.
	// Preconditions:
	// a != null
	// a.length > 0
	// Postconditions (let r be the return value):
	// for i in [0,r) a[i] < value
	// for i in [r,a.length) a[i] >= value
	public static <T extends Comparable<T>> int lowerBound(T[] a, T value) {
		assert (checkArguments(a, false));
		assert (isOrdered(a));
		return lowerBound(a, 0, a.length, value);
	}

	// upperBound returns the index of the first (lowest-index)
	// element of sub-array a[lo,hi) that is strictly greater than
	// the given value.
	// Preconditions:
	// a != null
	// 0 <= lo <= a.length
	// 0 <= hi <= a.length
	// hi - lo > 0
	// Postconditions (let r be the return value):
	// for i in [lo,r) a[i] <= value
	// for i in [r,hi) a[i] > value
	public static <T> int upperBound(T[] a, int lo, int hi, T value, Comparator<T> c) {
		assert (checkArguments(a, lo, hi, true));
		assert (isOrdered(a, c));
		// In all invariants, lo' and hi' are the original values
		// of lo and hi.
		// Note that due to the invariant that mid is in [lo,hi),
		// we will never get lo > hi.
		while (lo < hi) {
			// In all invariants, lo' and hi' are the original values
			// of lo and hi.
			// for all j in [lo',lo) a[mid] <= value
			// for all j in [hi,hi') a[mid] > value
			int mid = lo + (hi - lo) / 2;
			// mid in [lo,hi)
			if (c.compare(a[mid], value) <= 0)
				lo = mid + 1;
			// for all j in [lo',lo) a[mid] <= value
			else
				hi = mid;
			// for all j in [hi,hi') a[mid] > value
		}
		// for all j in [lo',hi) a[mid] <= value
		// for all j in [hi,hi') a[mid] > value
		return hi;
	}

	// upperBound returns the index of the first (lowest-index)
	// element of array 'a' that is strictly greater than the
	// given value.
	// Preconditions:
	// a != null
	// a.length > 0
	// Postconditions (let r be the return value):
	// for i in [0,r) a[i] <= value
	// for i in [r,a.length) a[i] > value
	public static <T> int upperBound(T[] a, T value, Comparator<T> c) {
		assert (checkArguments(a, true));
		assert (isOrdered(a, c));
		return upperBound(a, 0, a.length, value, c);
	}

	// upperBound returns the index of the first (lowest-index)
	// element of sub-array a[lo,hi) that is strictly greater than
	// the given value.
	// Preconditions:
	// a != null
	// 0 <= lo <= a.length
	// 0 <= hi <= a.length
	// hi - lo > 0
	// Postconditions (let r be the return value):
	// for i in [lo,r) a[i] <= value
	// for i in [r,hi) a[i] > value
	public static <T extends Comparable<T>> int upperBound(T[] a, int lo, int hi, T value) {
		assert (checkArguments(a, lo, hi, true));
		assert (isOrdered(a));
		return upperBound(a, lo, hi, value, new WrapComparable<T>());
	}

	// upperBound returns the index of the first (lowest-index)
	// element of array 'a' that is strictly greater than the
	// given value.
	// Preconditions:
	// a != null
	// a.length > 0
	// Postconditions (let r be the return value):
	// for i in [0,r) a[i] <= value
	// for i in [r,a.length) a[i] > value
	public static <T extends Comparable<T>> int upperBound(T[] a, T value) {
		assert (checkArguments(a, true));
		assert (isOrdered(a));
		return upperBound(a, 0, a.length, value);
	}

	public static <T> int countOrdered(T[] a, int lo, int hi, T value, Comparator<T> c) {
		assert (checkArguments(a, lo, hi, true));
		assert (isOrdered(a, c));
		return upperBound(a, lo, hi, value, c) - lowerBound(a, lo, hi, value, c);
	}

	public static <T> int countOrdered(T[] a, T value, Comparator<T> c) {
		assert (checkArguments(a, false));
		assert (isOrdered(a, c));
		return upperBound(a, value, c) - lowerBound(a, value, c);
	}

	public static <T extends Comparable<T>> int countOrdered(T[] a, int lo, int hi, T value) {
		assert (checkArguments(a, lo, hi, true));
		assert (isOrdered(a));
		return upperBound(a, lo, hi, value) - lowerBound(a, lo, hi, value);
	}

	public static <T extends Comparable<T>> int countOrdered(T[] a, T value) {
		assert (checkArguments(a, false));
		assert (isOrdered(a));
		return upperBound(a, value) - lowerBound(a, value);
	}

	private static class ForwardIterator<T> implements Iterator<T> {

		T[] a;
		int pos;
		int hi;

		public ForwardIterator(T[] a, int lo, int hi) {
			this.a = a;
			this.pos = lo;
			this.hi = hi;
		}

		@Override
		public boolean hasNext() {
			return pos < hi;
		}

		@Override
		public T next() {
			return a[pos++];
		}
	}

	public static <T> Iterator<T> getForwardIterator(T[] a, int lo, int hi) {
		return new ForwardIterator<T>(a, lo, hi);
	}

	private static class BackwardIterator<T> implements Iterator<T> {

		T[] a;
		int pos;
		int lo;

		public BackwardIterator(T[] a, int lo, int hi) {
			this.a = a;
			this.pos = hi;
			this.lo = lo;
		}

		@Override
		public boolean hasNext() {
			return pos > lo;
		}

		@Override
		public T next() {
			return a[--pos];
		}
	}

	public static <T> Iterator<T> getBackwardIterator(T[] a, int lo, int hi) {
		return new BackwardIterator<T>(a, lo, hi);
	}
}
