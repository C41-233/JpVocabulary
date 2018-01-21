package c41.utility.linq;

import c41.utility.assertion.Arguments;

class CharArrayEnumerable implements ICharEnumerable{

	private final char[] array;
	
	public CharArrayEnumerable(String string) {
		Arguments.isNotNull(string);
		this.array = string.toCharArray();
	}
	
	public CharArrayEnumerable(char[] array) {
		Arguments.isNotNull(array);
		this.array = array;
	}
	
	@Override
	public int count() {
		return array.length;
	}
	
	@Override
	public char[] toArray() {
		return array.clone();
	}
	
	@Override
	public ICharEnumerator iterator() {
		return new Enumerator();
	}

	private final class Enumerator extends CharEnumeratorBase{

		private int i = -1;
		
		@Override
		public boolean hasNext() {
			return i+1 < array.length;
		}

		@Override
		public void doMoveNext() {
			++i;
		}

		@Override
		public char doCurrentChar() {
			return array[i];
		}
		
	}
	
}