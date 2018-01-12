package c41.utility.linq;

abstract class EnumeratorBase<T> implements IEnumerator<T>{

	private boolean isBefore = true;
	
	@Override
	public final void moveNext() {
		if(!hasNext()) {
			throw EnumeratorException.throwAfter();
		}
		doMoveNext();
		isBefore = false;
	}

	protected abstract void doMoveNext();
	
	@Override
	public final T current() {
		if(isBefore) {
			throw EnumeratorException.throwBefore();
		}
		return doCurrent();
	}
	
	protected abstract T doCurrent();
}
