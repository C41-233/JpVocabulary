package c41.utility.linq;

import c41.lambda.predicate.IPredicate;

class WhereEnumerable<T> implements IReferenceEnumerable<T>{

	private final IEnumerable<T> enumerable;
	private final IPredicate<? super T> predicate;
	
	public WhereEnumerable(IEnumerable<T> enumerable, IPredicate<? super T> predicate) {
		this.enumerable = enumerable;
		this.predicate = predicate;
	}

	@Override
	public IEnumerator<T> iterator() {
		return new Enumerator();
	}

	private final class Enumerator extends EnumeratorBase<T>{

		private final IEnumerator<T> enumerator = enumerable.iterator();
		
		private T current;
		private T next;
		private boolean hasNext = false;
		
		public Enumerator() {
			while(enumerator.hasNext()) {
				T value = enumerator.next();
				if(predicate.is(value)) {
					this.next = value;
					hasNext = true;
					break;
				}
			}
		}
		
		@Override
		public boolean hasNext() {
			return hasNext;
		}

		@Override
		public void doMoveNext() {
			current = next;
			while(enumerator.hasNext()) {
				T value = enumerator.next();
				if(predicate.is(value)) {
					this.next = value;
					return;
				}
			}
			this.next = null;
			hasNext = false;
		}

		@Override
		public T doCurrent() {
			return current;
		}
		
	}
	
}
