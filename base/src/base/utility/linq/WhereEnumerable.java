package base.utility.linq;

import base.core.Reference;
import base.utility.function.IReferencePredicate;

class WhereEnumerable<T> implements IReferenceEnumerable<T>{

	private final IEnumerable<T> enumerable;
	private final IReferencePredicate<? super T> predicate;
	
	public WhereEnumerable(IEnumerable<T> enumerable, IReferencePredicate<? super T> predicate) {
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
					this.next = new Reference<T>(value);
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
				current = new Reference<T>();
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
