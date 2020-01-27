import java.util.Comparator;

import edu.princeton.cs.algs4.StdOut;

public class LinkedList<T> {

	// Every node has our data along with a reference to the
	// next node.
	private class Node {
		T data;
		Node next;

		// Constructor.
		Node(T d, Node n) {
			data = d;
			next = n;
		}
	}

	// Every LinkedIntList has a reference to the front-most
	// Node, a link to the back-most Node, and a count of
	// the number of nodes.
	//
	// A "class invariant" is a condition that should be true
	// before and after every instance method executes.  For
	// LinkedIntList, one class invariant is:
	//
	//		front == null && back == null && count == 0
	//			||
	//		front != null && back != null && count != 0
	//
	// In other words, a LinkedIntList may be either empty
	// or non-empty.  If empty, then the 'front' and 'back'
	// references are null and 'count' is zero; otherwise,
	// 'front' and 'back' are non-null, and 'count' is
	// non-zero.
	//
	// Another invariant is
	//		front == back if and only if count == 1
	//
	private Node front = null;
	private Node back = null;
	private int count = 0;
	
	public void exchange() {
		Node oldFront = front;
		front = oldFront.next;
		oldFront.next = front.next;
		front.next = oldFront;
		
	}
	
	public T peek(){
		if(front != null) {
			return front.data;
		}
		return null;
	}

	// addFront adds a new Node with the given data to
	// the front of the list.
	public void addFront(T i) {

		// Create the new Node and link it into the list.
		front = new Node(i, front);
		
		// We have another node.
		count++;

		// If 'back' is null, that means the list was
		// previously empty, and we need to adjust the
		// 'back' reference as well to refer to the one
		// and only node in the list (the one we just
		// added).
		if (back == null)
			back = front;
	}

	// addBack adds a new Node with the given data to
	// the back of the list.
	public void addBack(T i) {
		
		if (back == null) {
			// If the list is currently empty, adding to the back
			// is the same as adding to the front, and we already
			// have the necessary logic there, so let's just
			// use it.
			addFront(i);
		} else {
			// Other, we just have to create a new node and
			// link it into the back of the list.
			back.next = new Node(i, null);
			back = back.next;
			count++;
		}
	}

	// removeFront removes the front-most Node, and returns
	// the data stored there.
	// Precondition: the list cannot be empty
	public T removeFront() {
		
		// Check for an empty list.  It's better to throw an
		// exception here with a description of what happened
		// than to just let the next line of code fail with
		// a null reference exception.
		if (front == null)
			throw new RuntimeException("remove from empty list");
		Node oldFront= front;
		front = front.next;
		count--;

		// If 'front' is null, we now have an empty list,
		// and so 'back' just be set to null.
		if (front == null)
			back = null;

		// Setting the 'next' link to null in the Node we're
		// about to discard helps the garbage collector.
		oldFront.next = null;
		
		// Finally we can return the data that was stored in
		// that front-most node.
		return oldFront.data;
	}

	public T minElement(Comparator<T> comp) {
		T min = front.data;
		for (Node n = front; n != null; n = n.next)
			if (comp.compare(n.data, min) < 0)
				min = n.data;
		return min;
	}

	public boolean isEmpty() {
		
		// We could also use "back == null" or
		// "count == 0".  By our invariant, they should
		// all indicate an empty list.
		return front == null;
	}
	
	int size() {
		
		// Recomputing the count by traversing the list does
		// not scale.  We need to keep an explicit count.
		return count;
	}

	// Every user-defined type inherits a toString() function
	// from the Object class.  The default implemenation in
	// Object merely prints out the address of the object,
	// which is generally useless.  It is a good idea for
	// every class to provide its own definition of
	// toString().  In our case, we'll return a String
	// showing each list element followed by an arrow.
	// The end of the list will be indicated by "null".
	public String toString() {
		
		// Start with an empty string.
		String s = "";
		
		// This is the canonical 'for' loop for traversing
		// a linked list.  Know it!
		// Note that if the list is empty, 'n = front'
		// will assign 'null' to 'n', and therefore the
		// loop body never executes.
		for (Node n = front; n != null; n = n.next)
			s += n.data + " -> ";

		// We indicate the end of the list with "null".
		s += "null";
		return s;
	}

	public static void main(String[] args) {
		
		// As usual, we'll use main() as a test client to
		// provide at least some sort of rudimentary test
		// we can use as a reality check.
		
		// Our test clients will generally use the Java
		// 'assert' statement, which will generate an error
		// of the given condition is not true.  The Java
		// runtime generally ignores these statements;
		// they are only executed if the program is run
		// with the -enableassertions flag turned on.
		
		// Create a new list.  A new LinkedIntList is
		// initially empty.
		LinkedList<Integer> lil = new LinkedList<>();
		assert lil.isEmpty();
		assert lil.size() == 0;
		
		// Make sure the toString() function works
		// with an empty string.  The expected output
		// is "null".
		assert lil.toString().equals("null");

		// Add an item.  The list should no longer be
		// empty, and its size should be 1.
		lil.addFront(42);
		assert !lil.isEmpty();
		assert lil.size() == 1;

		// Remove the item.  That should return the
		// value 42, and the list should now be empty.
		int result = lil.removeFront();
		assert result == 42;
		assert lil.isEmpty();
		assert lil.size() == 0;

		// Add three items.  The resulting list should
		// be
		//		1 -> 2 -> 3
		//
		lil.addBack(2);
		assert !lil.isEmpty();
		assert lil.size() == 1;
		lil.addFront(1);
		assert !lil.isEmpty();
		assert lil.size() == 2;
		lil.addBack(3);
		assert !lil.isEmpty();
		assert lil.size() == 3;
		assert lil.toString().equals("1 -> 2 -> 3 -> null");

		// Remove the three items one by one, and check
		// that the results are what we expect.
		result = lil.removeFront();
		assert result == 1;
		assert !lil.isEmpty();
		assert lil.size() == 2;
		result = lil.removeFront();
		assert result == 2;
		assert !lil.isEmpty();
		assert lil.size() == 1;
		result = lil.removeFront();
		assert result == 3;
		assert lil.isEmpty();
		assert lil.size() == 0;
		
		/*
		LinkedList<String> dwarves = new LinkedList<>();
		dwarves.addFront("Dopey");
		dwarves.addFront("Sneezy");
		dwarves.addFront("Doc");
		dwarves.addFront("Bashful");
		dwarves.addFront("Happy");
		dwarves.addFront("Grumpy");
		dwarves.addFront("Sleepy");
		StdOut.println(dwarves.minElement(new Comparable<String>()));
		*/
		
		
		StdOut.println("********testing exchange*****");
		LinkedList<String> lls = new LinkedList<>();
		lls.addFront("one");
		lls.addFront("two");
		lls.addFront("three");
		StdOut.println(lls);
		lls.exchange();
		StdOut.println(lls);

		
		StdOut.println("********testing peek*****");
		
		StdOut.println(lls.peek());
		StdOut.println(lls);
		StdOut.println(lls.removeFront());
		StdOut.println(lls.removeFront());
		StdOut.println(lls.removeFront());
		StdOut.println(lls.peek());
		
		
	}
}
