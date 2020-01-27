import java.util.Comparator;

import edu.princeton.cs.algs4.StdOut;

public class Student {

	private String name;
	private int id;

	private static class CompareByName implements Comparator<Student> {

		@Override
		public int compare(Student s1, Student s2) {
			return s1.name.compareTo(s2.name);
		}
		
	}
	
	private static class CompareByID implements Comparator<Student> {

		@Override
		public int compare(Student s1, Student s2) {
			if (s1.id < s2.id) return -1;
			if (s1.id > s2.id) return 1;
			return 0;
		}
		
	}

	public Student(String n, int i) {
		name = n;
		id = i;
	}
	
	public String toString() {
		return name + " " + id;
	}
	
	public static void main(String[] args) {
		LinkedList<Student> students = new LinkedList<>();
		students.addBack(new Student("John Doe", 123));
		students.addBack(new Student("Jane Doe", 234));
		students.addBack(new Student("John Smith", 150));
		
		StdOut.println(students.minElement(new Student.CompareByName()));
		StdOut.println(students.minElement(new Student.CompareByID()));
	}
}
