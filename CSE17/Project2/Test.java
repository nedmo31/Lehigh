import java.io.File;
import java.io.FileNotFoundException;
import java.util.ListIterator;
import java.util.Scanner;
/**
 * Test class with main method
 * @author Nathan Edmondson
 * @version 11/10/21
 */
public class Test {

	public static void main(String[] args) {
		LinkedList<String> ll = new LinkedList<>();
		readFromFile(ll, "countries.txt");
		System.out.println("LinkedList Forward:");
		printListForward(ll);
		System.out.println("LinkedList Backward:");
		printListBackwards(ll); 
		System.out.println();
		ArrayList<String> al = new ArrayList<>();
		readFromFile(al, "countries.txt");
		System.out.println("ArrayList Forward:");
		printListForward(al);
		System.out.println("ArrayList Backward:");
		printListBackwards(al); 
		System.out.println();
		DoublyLinkedList<String> dll = new DoublyLinkedList<>();
		readFromFile(dll, "countries.txt"); 
		System.out.println("DoublyLinkedList Forward:");
		printListForward(dll);
		System.out.println("DoublyLinkedList Backward:");
		printListBackwards(dll); 
		System.out.println();
		
	}

	/*
	 * Method to read in a text file and store its contents
	 * in a List of Strings
	 */
	public static void readFromFile(List<String> l1, String filename) { //O(n)
		try {
			Scanner readFile = new Scanner(new File(filename));
			while (readFile.hasNextLine()) {
				String line = readFile.nextLine();
				l1.add(line);
			}
			readFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't find " + filename);
			System.exit(0);
		}
	}
	
	/*
	 * Method to print out a List using a forward
	 * iterator.
	 */
	public static <E> void printListForward(List<E> list) { // O(n)
		ListIterator<E> iter = list.listIterator();
		System.out.print("[");
		while (iter.hasNext()) {
			System.out.print(iter.next() + " ");
		}
		System.out.print("]\n");
	}
	
	/*
	 * Method to print out a list backward using a 
	 * backward iterator.
	 */
	public static <E> void printListBackwards(List<E> list) { // O(n)
		ListIterator<E> iter = list.listIterator(list.size());
		System.out.print("[");
		while (iter.hasPrevious()) {
			System.out.print(iter.previous() + " ");
		}
		System.out.print("]\n");
	}
	
}
