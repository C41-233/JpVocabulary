package c41.utility.linq;

class CharSkipEnumerable extends SkipEnumerable<Character> implements ICharEnumerable{

	public CharSkipEnumerable(ICharEnumerable enumerable, int skip) {
		super(enumerable, skip);
	}
	
	@Override
	public ICharEnumerator iterator() {
		return (ICharEnumerator) super.iterator();
	}
	
}