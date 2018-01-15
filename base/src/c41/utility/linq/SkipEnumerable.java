package c41.utility.linq;

import java.util.NoSuchElementException;

import c41.utility.assertion.Arguments;

class SkipEnumerable<T> implements IEnumerable<T>{

	private final IEnumerable<T> enumerable;
	private final int skip;
	
	public SkipEnumerable(IEnumerable<T> enumerable, int skip) {
		Arguments.isNotNull(enumerable);
		Arguments.is(skip>=0, "%d < 0", skip);
		
		this.enumerable = enumerable;
		this.skip = skip;
	}
	
	@Override
	public IEnumerator<T> iterator() {
		IEnumerator<T> enumerator = enumerable.iterator();
		for(int i=0; i < skip; i++) {
			if(!enumerator.hasNext()) {
				throw new NoSuchElementException();
			}
			enumerator.moveNext();
		}
		return enumerator;
	}

}