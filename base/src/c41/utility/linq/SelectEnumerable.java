package c41.utility.linq;

import c41.lambda.selector.ISelector;
import c41.lambda.selector.ISelectorEx;
import c41.utility.assertion.Arguments;

class SelectEnumerable<T, V> implements IReferenceEnumerable<V>{

	private final IEnumerable<T> enumerable;
	private final ISelectorEx<? super T, ? extends V> selector;
	
	public SelectEnumerable(IEnumerable<T> enumerable, ISelector<? super T, ? extends V> selector) {
		this(enumerable,  (value, i)->selector.select(value));
	}
	
	public SelectEnumerable(IEnumerable<T> enumerable, ISelectorEx<? super T, ? extends V> selector) {
		Arguments.isNotNull(enumerable);
		Arguments.isNotNull(selector);
		
		this.enumerable = enumerable;
		this.selector = selector;
	}
	
	@Override
	public IEnumerator<V> iterator() {
		return new Enumerator();
	}

	private final class Enumerator implements IEnumerator<V>{

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
