package base.utility.linq;

class SkipEnumerable<T> implements IEnumerable<T>{

	private final IEnumerable<T> enumerable;
	private final int skip;
	
	public SkipEnumerable(IEnumerable<T> enumerable, int skip) {
		this.enumerable = enumerable;
		this.skip = skip;
	}
	
	@Override
	public IEnumerator<T> iterator() {
		IEnumerator enumerator = enumerable.iterator();
		for(int i=0; i<skip && enumerator.hasNext(); i++) {
			enumerator.moveNext();
		}
		return enumerator;
	}

}

class ReferenceSkipEnumerable<T> extends SkipEnumerable<T> implements IReferenceEnumerable<T>{
	
	public ReferenceSkipEnumerable(IReferenceEnumerable<T> enumerable, int skip) {
		super(enumerable, skip);
	}

	@Override
	public IReferenceEnumerator<T> iterator() {
		return (IReferenceEnumerator<T>) super.iterator();
	}
	
}

class CharSkipEnumerable extends SkipEnumerable<Character> implements ICharEnumerable{

	public CharSkipEnumerable(ICharEnumerable enumerable, int skip) {
		super(enumerable, skip);
	}
	
	@Override
	public ICharEnumerator iterator() {
		return (ICharEnumerator) super.iterator();
	}
	
}