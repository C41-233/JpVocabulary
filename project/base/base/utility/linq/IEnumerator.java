package base.utility.linq;

import java.util.Iterator;

public interface IEnumerator<T> extends Iterator<T>{

	@Override
	public boolean hasNext();
	
	@Override
	public T next();
	
	public void moveNext();
	
}
