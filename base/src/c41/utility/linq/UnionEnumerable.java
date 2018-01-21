package c41.utility.linq;

import c41.utility.assertion.Arguments;

class UnionEnumerable<T> implements IReferenceEnumerable<T>{

	private final IEnumerable<? extends T> enumerable1;
	private final IEnumerable<? extends T> enumerable2;
	
	public UnionEnumerable(IEnumerable<? extends T> enumerable1, IEnumerable<? extends T> enumerable2) {
		Arguments.isNotNull(enumerable1);
		Arguments.isNotNull(enumerable2);
		
		this.enumerable1 = enumerable1;
		this.enumerable2 = enumerable2;
	}
	
	@Override
	public IEnumerator<T> iterator() {
		return new Enumerator();
	}

	private final class Enumerator extends EnumeratorBase<T>{

		private IEnumerator<? extends T> enumerator1 = enumerable1.iterator();
		private IEnumerator<? extends T> enumerator2 = null; //延迟构造
		
		@Override
		public boolean hasNext() {
			if(enumerator1 != null) {
				if(enumerator1.hasNext()) {
					return true;
				}
				if(enumerator2 == null) {
					enumerator2 = enumerable2.iterator();
				}
			}
			return enumerator2.hasNext();
		}

		@Override
		public void doMoveNext() {
			if(enumerator1 != null) {
				if(enumerator1.hasNext()) {
					enumerator1.moveNext();
					return;
				}
				enumerator1 = null;
			}
			if(enumerator2 == null) {
				enumerator2 = enumerable2.iterator();
			}
			enumerator2.moveNext();
		}

		@Override
		public T doCurrent() {
			if(enumerator1 != null) {
				return enumerator1.current();
			}
			return enumerator2.current();
		}
		
	}
	
}
