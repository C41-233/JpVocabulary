package base.utility.linq;

import java.util.List;
import java.util.Objects;

import base.utility.function.ICharAction;
import base.utility.function.ICharForeach;
import base.utility.function.ICharPredicate;
import base.utility.function.ICharSelector;

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
	
	public default boolean notAll(ICharPredicate predicate) {
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
	
	public default void foreach(ICharForeach action) {
		ICharEnumerator enumerator = iterator();
		int i = 0;
		while(enumerator.hasNext()) {
			char ch = enumerator.nextChar();
			action.action(ch, i++);
		}
	}

	public default void foreach(ICharAction action) {
		ICharEnumerator enumerator = iterator();
		while(enumerator.hasNext()) {
			char ch = enumerator.nextChar();
			action.action(ch);
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

class CharArrayEnumerable implements ICharEnumerable{

	private final char[] array;
	
	public CharArrayEnumerable(String string) {
		Objects.requireNonNull(string);
		this.array = string.toCharArray();
	}
	
	public CharArrayEnumerable(char[] array) {
		Objects.requireNonNull(array);
		this.array = array;
	}
	
	@Override
	public int count() {
		return array.length;
	}
	
	@Override
	public char[] toArray() {
		return array.clone();
	}
	
	@Override
	public ICharEnumerator iterator() {
		return new Enumerator();
	}

	private class Enumerator implements ICharEnumerator{

		private int i = -1;
		
		@Override
		public boolean hasNext() {
			return i+1 < array.length;
		}

		@Override
		public void moveNext() {
			++i;
		}

		@Override
		public char currentChar() {
			return array[i];
		}
		
	}
	
}