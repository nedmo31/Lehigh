import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
/**
 * Data structure that links nodes together.
 * You have direct access to the first and last node
 * @author Downloaded from CourseSite
 * @version 11/10/21
 */
public class LinkedList<E> implements List<E> {
	// Data members
	private Node head, tail;
	int size;

	// Inner class Node
	private class Node {
		E value;
		Node next;

		/**
		 * Constructor for node. Not linked
		 * to anything yet
		 * @param initialValue the value of the node
		 */
		Node(E initialValue) {
			value = initialValue;
			next = null;
		}
	}

	/**
	 * Constructor to make a blank LinkedList
	 * The head and tail are null
	 */
	public LinkedList() {// O(1)
		head = tail = null;
		size = 0;
	}

	/**
	 * Method to add a Node to the front of the 
	 * LinkedList. 
	 * @param item the item to add to the front
	 * @return true if the operation was successfull
	 */
	public boolean addFirst(E item) {// O(1)
		Node newNode = new Node(item);
		if (head == null) { // adding the first node
			head = tail = newNode;
		} else {
			newNode.next = head;
			head = newNode;
		}
		size++;
		return true;
	}

	/**
	 * Method to add a Node to the end of the 
	 * LinkedList. 
	 * @param item the item to add to the end
	 * @return true if the operation was successful
	 */
	public boolean addLast(E item) {// O(1)
		Node newNode = new Node(item);
		if (head == null) { // adding the first node
			head = tail = newNode;
		} else {
			tail.next = newNode;
			tail = newNode;
		}
		size++;
		return true;
	}

	/**
	 * Calls the addLast() method 
	 * @return true if the operation was successful
	 */
	public boolean add(E item) { // O(1)
		return addLast(item);
	}
	
	/**
	 * Method to get the head of the LinkedList
	 * @return the value of the head
	 */
	public E getFirst() { // O(1)
		if (head == null)
			throw new NoSuchElementException();
		return head.value;
	}

	/**
	 * Method to get the tail of the LinkedList
	 * @return the value of the tail
	 */
	public E getLast() { // O(1)
		if (head == null)
			throw new NoSuchElementException();
		return tail.value;
	}
	
	/**
	 * Method to remove the head of the LinkedList.
	 * updated version of behead()
	 * @return true if the operation was successful 
	 */
	public boolean removeFirst() { // O(1)
		if (head == null)
			throw new NoSuchElementException();
		head = head.next;
		if (head == null)
			tail = null;
		size--;
		return true;
	}

	/**
	 * Method to remove the the tail of the LinkedList.
	 * @return true if the operation was successful
	 */
	public boolean removeLast() { // O(n)
		if (head == null)
			throw new NoSuchElementException();
		if (size == 1)
			return removeFirst();
		Node current = head;
		Node previous = null;
		while (current.next != null) {
			previous = current;
			current = current.next;
		}
		previous.next = null;
		tail = previous;
		size--;
		return true;
	}
	
	/**
	 * Overridden toString() method to return the 
	 * LinkedList as a String
	 * @return a formatted String with the nodes
	 */
	public String toString() { // O(n)
		String output = "[";
		Node node = head;
		while (node != null) {
			output += node.value + " ";
			node = node.next;
		}
		output += "]";
		return output;
	}

	/**
	 * Method to clear all elements from the LinkedList
	 */
	public void clear() { // O(1)
		head = tail = null;
		size = 0;
	}

	/**
	 * Method to check if the LinkedList is empty
	 * @return true if size == 0
	 */
	public boolean isEmpty() { // O(1)
		return (size == 0);
	}

	/**
	 * Getter method for the size of the LinkedList
	 * @return the number of Nodes in the LinkedList
	 */
	public int size() { // O(1)
		return size;
	}
	
	/**
	 * Method to get an iterator over the LinkedList
	 * @return a new LinkedListIterator
	 */
	public Iterator<E> iterator() {
		return new LinkedListIterator();
	}

	//inner LinkedListIterator class
	private class LinkedListIterator implements Iterator<E> {
		private Node current = head;

		public boolean hasNext() { // O(1)
			return (current != null);
		}

		public E next() { // O(1)
			if (current == null)
				throw new NoSuchElementException();
			E value = current.value;
			current = current.next;
			return value;
		}
	}
	public int contains(E item) {
		Iterator<E> iter = iterator();
		int iterations = 0;
		while(iter.hasNext()) {
			iterations++;
			if(item.equals(iter.next())) {
				return iterations;
			}
		}
		return iterations;
	}
	
	public int remove(E item) {
		Node current = head;
		Node previous = null;
		int iterations=0;
		while(current!= null && !item.equals(current.value)) {
			previous = current;
			current = current.next;
			iterations++;
		}
		if(current != null) {
			if(previous == null)
				removeFirst();
			else {
				previous.next = current.next;
				size--;
			}
		}
		return iterations;
	}
	
	public int add(int index, E item) {
		if(index < 0 || index > size) {
			throw new ArrayIndexOutOfBoundsException();	
		}
		if(index == 0) {
			addFirst(item);
			return 0;
		}
		if(index == size) {
			addLast(item);
			return 0;
		}
		int i=0;
		int iterations=0;
		Node current = head;
		Node previous = null;
		while(current!=null && i<index) {
			previous = current;
			current = current.next;
			i++;
			iterations++;
		}
		Node newNode = new Node(item);
		previous.next = newNode;
		newNode.next = current;
		size++;
		return iterations;
	}
	
	private class LinkedListListIterator implements ListIterator<E> {

		private Node current, previous;
		
		public LinkedListListIterator() {
			current = head;
			previous = null;
		}
		
		public LinkedListListIterator(int index) {
			current = head;
			previous = null;
			if (index < 0 || index > size) {
				throw new ArrayIndexOutOfBoundsException();
			}
			for (int i = 0; i < index; i++) {
				previous = current;
				current = current.next;
			}
			
		}
		
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public E next() { // O(1)
			if (current == null) 
				throw new NoSuchElementException();
			previous = current;
			current = current.next;
			return previous.value;
		}

		@Override
		public boolean hasPrevious() {
			return previous != null;
		}

		@Override
		public E previous() {
			E value = previous.value;
			current = head;
			previous = null;
			while (!current.value.equals(value)) {
				previous = current;
				current = current.next;
			}
			return value;
		}

		@Override
		public int nextIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int previousIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void set(E e) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(E e) {
			throw new UnsupportedOperationException();
		}
		
	}
	
	@Override
	public ListIterator<E> listIterator() {
		return new LinkedListListIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new LinkedListListIterator(index);
	}
}
