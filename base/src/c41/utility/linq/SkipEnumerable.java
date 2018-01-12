package c41.utility.linq;

class SkipEnumerable<T> implements IEnumerable<T>{

	private final IEnumerable<T> enumerable;
	private final int skip;
	
	public SkipEnumerable(IEnumerable<T> enumerable, int skip) {
		this.enumerable = enumerable;
		this.skip = skip;
	}
	
	@Override
	public IEnumerator<T> iterator() {
		IEnumerator<T> enumerator = enumerable.iterator();
		for(int i=0; i < skip; i++) {
			if(!enumerator.hasNext()) {
				throw EnumeratorException.throwAfter();
			}
			enumerator.moveNext();
		}
		return enumerator;
	}

}