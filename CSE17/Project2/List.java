import java.util.ListIterator;
/**
 * Interface to model common behavior between lists
 * @author Nathan Edmondson
 * @version 11/10/21
 */
public interface List<E> {
	
	public boolean add(E item);
	
	public int size();
	
	public ListIterator<E> listIterator();
	
	public ListIterator<E> listIterator(int index);

}
