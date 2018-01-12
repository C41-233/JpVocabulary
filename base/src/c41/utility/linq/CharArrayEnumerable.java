package c41.utility.linq;

class CharArrayEnumerable implements ICharEnumerable{

	private final char[] array;
	
	public CharArrayEnumerable(String string) {
		this.array = string.toCharArray();
	}
	
	public CharArrayEnumerable(char[] array) {
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

	private class Enumerator implements ICharEnumerator{

		private int i = -1;
		
		@Override
		public boolean hasNext() {
			return i+1 < array.length;
		}

		@Override
		public void moveNext() {
			if(!hasNext()) {
				throw EnumeratorOutOfRangeException.throwAfter();
			}
			++i;
		}

		@Override
		public char currentChar() {
			if(i < 0) {
				throw EnumeratorOutOfRangeException.throwBefore();
			}
			return array[i];
		}
		
	}
	
}