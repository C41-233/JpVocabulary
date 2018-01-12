package c41.utility.linq;

import java.util.Iterator;

/**
 * Enumerator的基接口。
 * @param <T> 查询元素
 */
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
