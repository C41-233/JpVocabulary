package base.utility.linq;

import java.lang.reflect.Array;
import java.util.List;

import base.utility.function.IReferencePredicate;

public interface IReferenceEnumerable<T> extends IEnumerable<T>{

	@Override
	public IReferenceEnumerator<T> iterator();

	public default boolean isAll(IReferencePredicate<? super T> predicate) {
		IReferenceEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			T obj = enumerator.next();
			if(predicate.is(obj) == false) {
				return false;
			}
		}
		return true;
	}

	public default boolean notAll(IReferencePredicate<? super T> predicate) {
		IReferenceEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			T obj = enumerator.next();
			if(predicate.is(obj) == false) {
				return true;
			}
		}
		return false;
	}
	
	public default T[] toArray(Class<T> type) {
		List<T> list = toList();
		T[] array = (T[]) Array.newInstance(type, list.size());
		for(int i=0; i<list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}
	
	@Override
	public default IReferenceEnumerable<T> skip(int n){
		return new ReferenceSkipEnumerable(this, n);
	}
	
}

class IterableEnumerable<T> implements IReferenceEnumerable<T>{
	
	private final Iterable<T> iterable;
	
	public IterableEnumerable(Iterable<T> iterable) {
		this.iterable = iterable;
	}

	@Override
	public IReferenceEnumerator<T> iterator() {
		return new Iterator();
	}
	
	private class Iterator implements IReferenceEnumerator<T>{

		private final java.util.Iterator<T> iterator = iterable.iterator();
		
		private T current;
		
		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public void moveNext() {
			current = iterator.next();
		}

		@Override
		public T current() {
			return current;
		}
		
	}
	
}