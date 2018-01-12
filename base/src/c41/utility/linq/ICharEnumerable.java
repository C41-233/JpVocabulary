package c41.utility.linq;

import java.util.List;

import c41.utility.lambda.action.ICharAction;
import c41.utility.lambda.action.IForeachCharAction;
import c41.utility.lambda.predicate.ICharPredicate;
import c41.utility.lambda.selector.ICharSelector;

public interface ICharEnumerable extends IEnumerable<Character>{

	@Override
	public ICharEnumerator iterator();
	
	public default boolean isAll(ICharPredicate predicate) {
		ICharEnumerator enumerator = iterator();
		while(enumerator.hasNext()) {
			char ch = enumerator.nextChar();
			if(predicate.is(ch) == false) {
				return false;
			}
		}
		return true;
	}
	
	public default boolean isNotAll(ICharPredicate predicate) {
		ICharEnumerator enumerator = iterator();
		while(enumerator.hasNext()) {
			char ch = enumerator.nextChar();
			if(predicate.is(ch) == false) {
				return true;
			}
		}
		return false;
	}
	
	public default char[] toArray() {
		List<Character> list = toList();
		char[] array = new char[list.size()];
		for(int i=0; i<list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}
	
	public default void foreach(IForeachCharAction action) {
		ICharEnumerator enumerator = iterator();
		int i = 0;
		while(enumerator.hasNext()) {
			char ch = enumerator.nextChar();
			action.invoke(ch, i++);
		}
	}

	public default void foreach(ICharAction action) {
		ICharEnumerator enumerator = iterator();
		while(enumerator.hasNext()) {
			char ch = enumerator.nextChar();
			action.invoke(ch);
		}
	}
	
	@SuppressWarnings("unchecked")
	public default <V> IReferenceEnumerable<V> select(ICharSelector<? extends V> selector){
		return new CharSelectEnumerable(this, selector);
	}
	
	@Override
	public default ICharEnumerable skip(int n) {
		return new CharSkipEnumerable(this, n);
	}
	
}