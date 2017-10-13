package base.utility.linq;

import java.util.Iterator;
import java.util.Objects;

import base.utility.function.ICharPredicate;

public interface ICharQuery extends Iterable<Character> {

	public ICharIterator iteratorChar();
	
	@Override
	public default Iterator<Character> iterator() {
		return iteratorChar();
	}
	
	public default boolean isAll(ICharPredicate predicate) {
		Objects.requireNonNull(predicate);
		
		ICharIterator iterator = iteratorChar();
		while(iterator.hasNext()) {
			char ch = iterator.nextChar();
			if(predicate.is(ch) == false) {
				return false;
			}
		}
		return true;
	}
	
	public default boolean notAll(ICharPredicate predicate) {
		Objects.requireNonNull(predicate);
		
		ICharIterator iterator = iteratorChar();
		while(iterator.hasNext()) {
			char ch = iterator.nextChar();
			if(predicate.is(ch) == false) {
				return true;
			}
		}
		return false;
	}
	
}
