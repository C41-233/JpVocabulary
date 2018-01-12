package c41.utility.linq;

import java.util.Iterator;

class IterableEnumerable<T> implements IReferenceEnumerable<T>{
	
	private final Iterable<T> iterable;
	
	public IterableEnumerable(Iterable<T> iterable) {
		this.iterable = iterable;
	}

	@Override
	public IEnumerator<T> iterator() {
		return new Enumerator();
	}
	
	private class Enumerator extends EnumeratorBase<T>{

		private final Iterator<T> iterator = iterable.iterator();
		
		private T current;
		
		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public void doMoveNext() {
			current = iterator.next();
		}

		@Override
		public T doCurrent() {
			return current;
		}
		
	}
	
}