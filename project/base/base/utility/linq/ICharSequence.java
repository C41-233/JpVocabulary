package base.utility.linq;

import java.util.Iterator;

public interface ICharSequence extends Iterable<Character> {

	public ICharIterator iteratorChar();
	
	@Override
	public default Iterator<Character> iterator() {
		return iteratorChar();
	}
	
	public boolean isAll(ICharPredicate predicate);
	
	public boolean notAll(ICharPredicate predicate);
	
}
