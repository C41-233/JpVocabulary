package base.utility.linq;

import base.utility.function.ICharSelector;
import base.utility.function.ISelector;

class SelectEnumerable<T, V> implements IReferenceEnumerable<V>{

	private final IEnumerable<T> enumerable;
	private final ISelector<T, V> selector;
	
	public SelectEnumerable(IEnumerable<T> enumerable, ISelector<T, V> selector) {
		this.enumerable = enumerable;
		this.selector = selector;
	}
	
	@Override
	public IEnumerator<V> iterator() {
		return new Enumerator();
	}

	private class Enumerator implements IEnumerator<V>{

		private final IEnumerator<T> enumerator = enumerable.iterator();
		
		private V current;

		@Override
		public boolean hasNext() {
			return enumerator.hasNext();
		}

		@Override
		public void moveNext() {
			enumerator.moveNext();
		}

		@Override
		public V current() {
			return selector.select(enumerator.current());
		}
		
	}
	
}

class CharSelectEnumerable<V> implements IReferenceEnumerable<V>{

	private final ICharEnumerable enumerable;
	private final ICharSelector<V> selector;
	
	public CharSelectEnumerable(ICharEnumerable enumerable, ICharSelector<V> selector) {
		this.enumerable = enumerable;
		this.selector = selector;
	}

	@Override
	public IEnumerator<V> iterator() {
		return new Enumerator();
	}

	private class Enumerator implements IEnumerator<V>{

		private final ICharEnumerator enumerator = enumerable.iterator();
		
		private V current;

		@Override
		public boolean hasNext() {
			return enumerator.hasNext();
		}

		@Override
		public void moveNext() {
			enumerator.moveNext();
		}

		@Override
		public V current() {
			return selector.select(enumerator.current());
		}
		
	}
	
}
