package c41.utility.linq;

import java.util.Iterator;

public interface IEnumerator<T> extends Iterator<T>{

	@Override
	public boolean hasNext();
	
	@Override
	public default T next() {
		moveNext();
		return current();
	}
	
	public void moveNext();
	
	public T current();
	
}
