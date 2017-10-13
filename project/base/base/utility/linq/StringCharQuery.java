package base.utility.linq;

import java.util.NoSuchElementException;

class StringCharQuery implements ICharQuery {

	private final char[] value;
	
	public StringCharQuery(String s) {
		this.value = s.toCharArray();
	}
	
	@Override
	public ICharIterator iteratorChar() {
		return new StringCharIterator();
	}

	private class StringCharIterator implements ICharIterator{

		private int index = -1;
		
		@Override
		public boolean hasNext() {
			return index+1 < value.length;
		}

		@Override
		public char current() {
			if(index >= value.length || index < 0) {
				throw new NoSuchElementException();
			}
			return value[index];
		}

		@Override
		public void moveNext() {
			if(hasNext() == false) {
				throw new NoSuchElementException();
			}
			++index;
		}

	}

}
