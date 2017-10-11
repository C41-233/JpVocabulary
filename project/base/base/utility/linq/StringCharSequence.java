package base.utility.linq;

class StringCharSequence implements ICharSequence {

	private final char[] value;
	
	public StringCharSequence(String s) {
		this.value = s.toCharArray();
	}
	
	@Override
	public ICharIterator iteratorChar() {
		
		return null;
	}

	@Override
	public boolean isAll(ICharPredicate predicate) {
		for(char ch : value) {
			if(predicate.is(ch) == false) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean notAll(ICharPredicate predicate) {
		for(char ch : value) {
			if(predicate.is(ch) == false) {
				return true;
			}
		}
		return false;
	}
	
	private class StringCharIterator implements ICharIterator{

		private int index = -1;
		
		@Override
		public boolean hasNext() {
			return index+1 < value.length;
		}

		@Override
		public char value() {
			return value[index];
		}

		@Override
		public void move() {
			index++;
		}
		
	}

}
