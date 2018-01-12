package c41.utility.linq;

import java.util.Iterator;

import c41.utility.lambda.selector.ISelector;

class SelectManyEnumerable<T, V> implements IReferenceEnumerable<V>{

	private final IEnumerable<T> enumerable;
	private final ISelector<? super T, ? extends Iterable<? extends V>> selector;
	
	public SelectManyEnumerable(IEnumerable<T> enumerable, ISelector<? super T, ? extends Iterable<? extends V>> selector) {
		this.enumerable = enumerable;
		this.selector = selector;
	}

	@Override
	public IEnumerator<V> iterator() {
		return new Enumerator();
	}
	
	private class Enumerator implements IEnumerator<V>{

		private IEnumerator<T> enumerator = enumerable.iterator();
		private Iterator<? extends V> iterator;
		private V current;
		
		public Enumerator() {
			while(enumerator.hasNext()) {
				Iterable<? extends V> iterable = selector.select(enumerator.next());
				this.iterator = iterable.iterator();
				if(iterator.hasNext()) {
					return;
				}
			}
			this.iterator = null;
		}
		
		@Override
		public boolean hasNext() {
			return this.iterator != null;
		}

		@Override
		public void moveNext() {
			current = iterator.next();
			if(iterator.hasNext()) {
				return;
			}

			while(enumerator.hasNext()) {
				Iterable<? extends V> iterable = selector.select(enumerator.next());
				this.iterator = iterable.iterator();
				if(iterator.hasNext()) {
					return;
				}
			}
			this.iterator = null;
		}

		@Override
		public V current() {
			return current;
		}
		
	}
	
}
