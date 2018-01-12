package c41.utility.collection;

import java.util.Iterator;
import java.util.Objects;

public final class Iterables {

	private Iterables() {}
	
	@SuppressWarnings("unlikely-arg-type")
	public static <T, V> boolean equals(Iterable<T> it1, Iterable<V> it2) {
		Iterator<T> iterator1 = it1.iterator();
		Iterator<V> iterator2 = it2.iterator();
		while(iterator1.hasNext() && iterator2.hasNext()) {
			if(Objects.equals(iterator1.next(), iterator2.next()) == false) {
				return false;
			}
		}
		if(iterator1.hasNext() || iterator2.hasNext()) {
			return false;
		}
		return true;
	}
	
}
