package c41.utility.linq;

import c41.utility.lambda.selector.IIntConverter;

class SelectIntEnumerable<T> implements IIntEnumerable{

	private final IEnumerable<T> enumerable;
	private final IIntConverter<T> selector;
	
	public SelectIntEnumerable(IEnumerable<T> enumerable, IIntConverter<T> selector) {
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
			return selector.convert(enumerator.current());
		}
		
	}
	
}