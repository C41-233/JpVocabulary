package c41.utility.linq;

import c41.utility.lambda.selector.ICharSelector;
import c41.utility.lambda.selector.ISelector;
import c41.utility.lambda.selector.ISelectorEx;

class SelectEnumerable<T, V> implements IReferenceEnumerable<V>{

	private final IEnumerable<T> enumerable;
	private final ISelectorEx<? super T, ? extends V> selector;
	
	public SelectEnumerable(IEnumerable<T> enumerable, ISelector<? super T, ? extends V> selector) {
		this(enumerable,  (value, i)->selector.select(value));
	}
	
	public SelectEnumerable(IEnumerable<T> enumerable, ISelectorEx<? super T, ? extends V> selector) {
		this.enumerable = enumerable;
		this.selector = selector;
	}
	
	@Override
	public IEnumerator<V> iterator() {
		return new Enumerator();
	}

	private class Enumerator implements IEnumerator<V>{

		private final IEnumerator<T> enumerator = enumerable.iterator();
		private int index = -1;
		
		@Override
		public boolean hasNext() {
			return enumerator.hasNext();
		}

		@Override
		public void moveNext() {
			enumerator.moveNext();
			index++;
		}

		@Override
		public V current() {
			return selector.select(enumerator.current(), index);
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
