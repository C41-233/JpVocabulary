package c41.utility.linq;

import c41.core.Reference;
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

	private class Enumerator implements IEnumerator<T>{

		private final IEnumerator<T> enumerator = enumerable.iterator();
		
		private Reference<T> current;
		private Reference<T> next;
		
		public Enumerator() {
			while(enumerator.hasNext()) {
				T value = enumerator.next();
				if(predicate.is(value)) {
					this.next = new Reference<>(value);
					break;
				}
			}
		}
		
		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public void moveNext() {
			if(current == null) {
				current = new Reference<>();
			}
			current.value = next.value;
			while(enumerator.hasNext()) {
				T value = enumerator.next();
				if(predicate.is(value)) {
					this.next.value = value;
					return;
				}
			}
			this.next = null;
		}

		@Override
		public T current() {
			return current.value;
		}
		
	}
	
}
