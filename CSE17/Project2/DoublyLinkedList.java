import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
/**
 * Data structure that links nodes to eachother, 
 * both forward and backward. Only have direct access
 * to first and last element
 * @author Nathan Edmondson
 * @version 11/10/21
 */
public class DoublyLinkedList<E> implements List<E> {
	// Data members
	private Node head, tail;
	int size;

	// Inner class Node
	private class Node {
		E value;
		Node next, previous;

		Node(E initialValue) {
			value = initialValue;
			next = null;
			previous = null;
		}
	}

	// Constructor
	public DoublyLinkedList() {// O(1)
		head = tail = null;
		size = 0;
	}

	// Adding an item to the list
	public boolean addFirst(E item) {// O(1)
		Node newNode = new Node(item);
		if (head == null) { // adding the first node
			head = tail = newNode;
		} else {
			newNode.next = head;
			head.previous = newNode;
			head = newNode;
		}
		size++;
		return true;
	}

	public boolean addLast(E item) {// O(1)
		Node newNode = new Node(item);
		if (head == null) {
			head = tail = newNode;
		} else {
			newNode.previous = tail;
			tail.next = newNode;
			tail = newNode;
		}
		size++;
		return true;
	}

	public boolean add(E item) { // O(1)
		return addLast(item);
	}
	// Retrieving an item from the list
	public E getFirst() { // O(1)
		if (head == null)
			throw new NoSuchElementException();
		return head.value;
	}

	public E getLast() { // O(1)
		if (head == null)
			throw new NoSuchElementException();
		return tail.value;
	}
	// Removing an item from the list
	public boolean removeFirst() { // O(1)
		if (head == null)
			throw new NoSuchElementException();
		head = head.next;
		head.previous = null;
		if (head == null)
			tail = null;
		size--;
		return true;
	}

	public boolean removeLast() { // O(1)
		if (head == null)
			throw new NoSuchElementException();
		tail = tail.previous;
		tail.next = null;
		size--;
		return true;
	}
	
	// toString() method
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

	// clear, check if empty, and size of the list
	public void clear() { // O(1)
		head = tail = null;
		size = 0;
	}

	public boolean isEmpty() { // O(1)
		return (size == 0);
	}

	public int size() { // O(1)
		return size;
	}
	// Generating an iterator for the list
	public Iterator<E> iterator() { // O(1)
		return new LinkedListIterator();
	}

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
	public int contains(E item) { // O(n)
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
	
	public int remove(E item) { // O(n)
		Node current = head;
		Node previous = null;
		int iterations = 0;
		while(current!= null && !item.equals(current.value)) {
			previous = current;
			current = current.next;
			iterations++;
		}
		if(current != null) {
			if(previous == null)
				removeFirst();
			else if (current.next == null)
				removeLast();
			else {
				current.next.previous = previous;
				previous.next = current.next;
				size--;
			}
		}
		return iterations;
	}
	
	public int add(int index, E item) { // O(n)
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
	
	private class DoublyLinkedListListIterator implements ListIterator<E> {

		private Node current, previous;
		
		public DoublyLinkedListListIterator() { // O(1)
			current = head;
			previous = null;
		}
		
		public DoublyLinkedListListIterator(int index) { // O(1)
			current = head;
			previous = null;
			if (index < 0 || index > size) {
				throw new ArrayIndexOutOfBoundsException();
			}
			else if (index == size) {
				previous = tail;
				current = null;
			}
			else {
				for (int i = 0; i < index; i++) {
					previous = current;
					current = current.next;
				}
			}
			
		}
		
		@Override
		public boolean hasNext() { // O(1)
			return current != null;
		}

		@Override
		public E next() { // O(1)
			if (current == null) 
				throw new NoSuchElementException();
			E value = current.value;
			current = current.next;
			return value;
		}

		@Override
		public boolean hasPrevious() { // O(1)
			return previous != null;
		}

		@Override
		public E previous() { // O(1)
			E value = previous.value;
			current = previous;
			previous = previous.previous;
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
	public ListIterator<E> listIterator() { // O(1)
		return new DoublyLinkedListListIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) { // O(1)
		return new DoublyLinkedListListIterator(index);
	}
}