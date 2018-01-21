package c41.utility.linq;

import java.util.Iterator;

import c41.utility.assertion.Arguments;

class IteratorEnumerable<T> implements IReferenceEnumerable<T> {

	private final Enumerator enumerator;
	
	public IteratorEnumerable(Iterator<T> iterator) {
		Arguments.isNotNull(iterator);
		this.enumerator = new Enumerator(iterator);
	}
	
	@Override
	public IEnumerator<T> iterator() {
		return enumerator;
	}

	private final class Enumerator extends EnumeratorBase<T>{

		private final Iterator<T> iterator;
		private T current;
		
		public Enumerator(Iterator<T> iterator) {
			this.iterator = iterator;
		}
		
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
