import java.util.ArrayList;
import java.util.LinkedList;

public class HashMapLP<K, V> {
	public static int iterations, collisions;
	private int size;
	private double loadFactor;
	private HashMapEntry<K, V>[] hashTable;

	// Constructors
	public HashMapLP() {
		this(100, 0.5);
	}
	public HashMapLP(int c) {
		this(c, 0.5);
	}
	public HashMapLP(int c, double lf) {
		hashTable = new HashMapEntry[trimToPowerOf2(c)];
		loadFactor = lf;
		size = 0;
		collisions = 0;
	}

	// private methods
	private int trimToPowerOf2(int c) {
		int capacity = 1;
		while (capacity < c)
			capacity = capacity << 1;
		return capacity;
	}
	private int hash(int hashCode) {
		return hashCode & (hashTable.length - 1);
	}
	private void rehash() {
		ArrayList<HashMapEntry<K,V>> list = toList();
		hashTable = new HashMapEntry[hashTable.length << 1];
		size = 0;
		for(HashMapEntry<K,V> entry : list)
			put(entry.getKey(), entry.getValue());

	}
	// public interface
	public int size() {
		return size;
	}
	public void clear() {
		size = 0;
		for (int i = 0; i < hashTable.length; i++) {
			hashTable[i] = null;
		}
	}
	public boolean isEmpty() {
		return (size == 0);
	}
	public boolean containsKey(K key) {
		if (get(key) != null)
			return true;
		return false;
	}
	// returns the value of key if found, null otherwise
	public V get(K key) {
		iterations = 0;
		int HTIndex = hash(key.hashCode());
		if (hashTable[HTIndex] != null) {
			for (int i = 0; i < hashTable.length; i++) {
				iterations++;
				if (hashTable[HTIndex] == null)
					continue;
				if (hashTable[HTIndex].getKey().equals(key))
					return hashTable[HTIndex].getValue();
				HTIndex++;
				if (HTIndex >= hashTable.length)
					HTIndex = 0;
			}
		}
		return null;
	}
	// remove a key if found
	public void remove(K key) {
		int HTIndex = hash(key.hashCode());
		if (hashTable[HTIndex] != null) {
			for (int i = 0; i < hashTable.length; i++) {
				if (hashTable[HTIndex].getKey().equals(key)) {
					hashTable[HTIndex] = null;
					size--;
					break;
				}
				HTIndex++;
				if (HTIndex >= hashTable.length)
					HTIndex = 0;
			}
		}
	}
	// adds a new key or modifies an existing key
	public V put(K key, V value) {
		// key in the hash map - find it and modify its value
		if(get(key) != null) {
			int HTIndex = hash(key.hashCode());
			for (int i = 0; i < hashTable.length; i++) {
				if (hashTable[HTIndex].getKey().equals(key)) {
					V old = hashTable[HTIndex].getValue();
					hashTable[HTIndex] = new HashMapEntry<K,V>(key, value);
					return old;
				}
				HTIndex++;
				if (HTIndex >= hashTable.length)
					HTIndex = 0;
			}
		}
		// key not in the hash map - check load factor
		if(size >= hashTable.length * loadFactor)
			rehash();
		int HTIndex = hash(key.hashCode());
		if(hashTable[HTIndex] == null){
			hashTable[HTIndex] = new HashMapEntry<>(key, value);
			size++;
		}
		else {
			collisions++;
			for (int i = 0; i < hashTable.length; i++) {
				if (hashTable[HTIndex] == null) {
					hashTable[HTIndex] = new HashMapEntry<K,V>(key, value);
					return value;
				}
				HTIndex++;
				if (HTIndex >= hashTable.length)
					HTIndex = 0;
			}
		}
		return value;
	}
	// returns the elements of the hash map as a list
	public ArrayList<HashMapEntry<K,V>> toList(){
		ArrayList<HashMapEntry<K,V>> list = new ArrayList<>();
		for(int i=0; i< hashTable.length; i++) {
			if(hashTable[i] != null) {
				list.add(hashTable[i]);
			}
		}
		return list;
	}
	// returns the elements of the hash map as a string
	public String toString() {
		String out = "[";
		for(int i=0; i<hashTable.length; i++) {
			if(hashTable[i]!=null) {
				out += hashTable[i].toString();
				out += "\n";
			}
		}
		out += "]";
		return out;
	}

}
