package c41.utility.linq;

import java.util.NoSuchElementException;

import c41.utility.assertion.Arguments;

class ArrayEnumerable<T> implements IReferenceEnumerable<T>{

	private final T[] array;
	
	public ArrayEnumerable(T[] array) {
		this.array = array;
	}
	
	@Override
	public int count() {
		return array.length;
	}

	@Override
	public T at(int i) {
		Arguments.is(i>=0, "%d < 0", i);
		
		if(i >= array.length) {
			throw new NoSuchElementException();
		}
		return array[i];
	}
	
	@Override
	public IEnumerator<T> iterator() {
		return new Enumerator();
	}

	private final class Enumerator extends EnumeratorBase<T>{

		private int index = -1;
		
		@Override
		public boolean hasNext() {
			return index+1 < array.length;
		}

		@Override
		public void doMoveNext() {
			++index;
		}

		@Override
		public T doCurrent() {
			return array[index];
		}
		
	}
	
}