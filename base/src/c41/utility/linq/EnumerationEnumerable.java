package c41.utility.linq;

import java.util.Enumeration;

import c41.utility.assertion.Arguments;

class EnumerationEnumerable<T> implements IReferenceEnumerable<T> {

	private final Enumerator enumerator;
	
	public EnumerationEnumerable(Enumeration<T> enumeration) {
		Arguments.isNotNull(enumeration);
		this.enumerator = new Enumerator(enumeration);
	}
	
	@Override
	public IEnumerator<T> iterator() {
		return enumerator;
	}

	private final class Enumerator extends EnumeratorBase<T>{

		private final Enumeration<T> enumeration;
		private T current;
		
		public Enumerator(Enumeration<T> enumeration) {
			this.enumeration = enumeration;
		}
		
		@Override
		public boolean hasNext() {
			return enumeration.hasMoreElements();
		}

		@Override
		protected void doMoveNext() {
			this.current = enumeration.nextElement();
		}

		@Override
		protected T doCurrent() {
			return current;
		}
		
	}
	
}
