package c41.utility.linq;

abstract class CharEnumeratorBase implements ICharEnumerator{

	private boolean isBefore = true;
	
	@Override
	public final void moveNext() {
		if(!hasNext()) {
			EnumeratorException.throwBefore();
		}
		doMoveNext();
		isBefore = false;
	}
	
	protected abstract void doMoveNext();

	@Override
	public final char currentChar() {
		if(isBefore) {
			EnumeratorException.throwAfter();
		}
		return doCurrentChar();
	}
	
	protected abstract char doCurrentChar();
	
}
