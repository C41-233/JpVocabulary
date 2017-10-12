package base.utility.linq;

import java.util.HashSet;
import java.util.Iterator;

public interface IQuery<T> extends Iterable<T>{

	public default boolean isAllDistinct() {
		HashSet<T> hashSet = new HashSet<>();
		Iterator<T> iterator = iterator();
		while(iterator.hasNext()) {
			T value = iterator.next();
			if(hashSet.contains(value)) {
				return false;
			}
			hashSet.add(value);
		}
		return true;
	}
	
	public default T firstDistinctValue() {

		HashSet<T> hashSet = new HashSet<>();
		Iterator<T> iterator = iterator();
		while(iterator.hasNext()) {
			T value = iterator.next();
			if(hashSet.contains(value)) {
				return value;
			}
			hashSet.add(value);
		}
		return null;
	}
	
}
