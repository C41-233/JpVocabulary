package base.utility.linq;

import base.utility.lambda.ISelector;
import base.utility.lambda.ISelectorEx;
import base.utility.lambda.ISelectorInt;
import base.utility.lambda.selector.ICharSelector;

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

class SelectIntEnumerable<T> implements IIntEnumerable{

	private final IEnumerable<T> enumerable;
	private final ISelectorInt<T> selector;
	
	public SelectIntEnumerable(IEnumerable<T> enumerable, ISelectorInt<T> selector) {
		this.enumerable = enumerable;
		this.selector = selector;
	}
	
	@Override
	public IIntEnumerator iterator() {
		return new Enumerator();
	}
	
	private class Enumerator implements IIntEnumerator{

		private final IEnumerator<T> enumerator = enumerable.iterator();
		
		@Override
		public boolean hasNext() {
			return enumerator.hasNext();
		}

		@Override
		public void moveNext() {
			enumerator.moveNext();
		}

		@Override
		public int currentInt() {
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
