package base.utility.linq;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import base.utility.collection.DefaultValueHashMap;
import base.utility.function.Comparators;
import base.utility.function.IAction;
import base.utility.function.IForeachAction;
import base.utility.function.IReferencePredicate;
import base.utility.function.ISelector;
import base.utility.function.ISelectorEx;

public interface IReferenceEnumerable<T> extends IEnumerable<T>{

	@Override
	public IEnumerator<T> iterator();

	public default boolean isAll(IReferencePredicate<? super T> predicate) {
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			T obj = enumerator.next();
			if(predicate.is(obj) == false) {
				return false;
			}
		}
		return true;
	}

	public default boolean notAll(IReferencePredicate<? super T> predicate) {
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			T obj = enumerator.next();
			if(predicate.is(obj) == false) {
				return true;
			}
		}
		return false;
	}
	
	public default boolean isExist(IReferencePredicate<? super T> predicate) {
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			T obj = enumerator.next();
			if(predicate.is(obj)) {
				return true;
			}
		}
		return false;
	}

	public default boolean isExist(T value) {
		return isExist(obj->Objects.equals(obj, value));
	}

	public default boolean notExist(IReferencePredicate<? super T> predicate) {
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			T obj = enumerator.next();
			if(predicate.is(obj)) {
				return false;
			}
		}
		return true;
	}
	
	public default boolean notExist(T value) {
		return notExist(obj->Objects.equals(obj, value));
	}

	public default T at(int index) {
		IEnumerator<T> enumerator = iterator();
		for(int i=0; i<index && enumerator.hasNext(); i++, enumerator.moveNext());
		if(enumerator.hasNext()) {
			return enumerator.next();
		}
		else {
			throw new IndexOutOfBoundsException();
		}
	}
	
	public default T find(IReferencePredicate<? super T> predicate) {
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			T obj = enumerator.next();
			if(predicate.is(obj)) {
				return obj;
			}
		}
		return null;
	}

	public default int findIndex(IReferencePredicate<? super T> predicate) {
		int index = 0;
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			T obj = enumerator.next();
			if(predicate.is(obj)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public default int count(IReferencePredicate<T> predicate) {
		int count = 0;
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			T obj = enumerator.next();
			if(predicate.is(obj)) {
				count++;
			}
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	public default T[] toArray(Class<T> type) {
		List<T> list = toList();
		T[] array = (T[]) Array.newInstance(type, list.size());
		for(int i=0; i<list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}

	public default T[] toArray(T[] array) {
		return toList().toArray(array);
	}
	
	public default void foreach(IAction<? super T> action) {
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			T obj = enumerator.next();
			action.call(obj);
		}
	}
	
	public default void foreach(IForeachAction<? super T> action) {
		IEnumerator<T> enumerator = iterator();
		int index = 0;
		while(enumerator.hasNext()) {
			T obj = enumerator.next();
			action.action(obj, index++);
		}
	}
	
	public default <V> IReferenceEnumerable<V> select(ISelector<? super T, ? extends V> selector){
		return new SelectEnumerable<T, V>(this, selector);
	}

	public default <V> IReferenceEnumerable<V> select(ISelectorEx<? super T, ? extends V> selector){
		return new SelectEnumerable<T, V>(this, selector);
	}
	
	public default <V> IReferenceEnumerable<V> selectMany(ISelector<? super T, ? extends Iterable<? extends V>> selector){
		return new SelectManyEnumerable<T, V>(this, selector);
	}
	
	public default IReferenceEnumerable<T> where(IReferencePredicate<? super T> predicate){
		return new WhereEnumerable<T>(this, predicate);
	}
	
	public default IReferenceSortedEnumerable<T> orderBy(Comparator<? super T> comparator){
		return new OrderByEnumerable<T>(this, comparator);
	}
	
	@SuppressWarnings("unchecked")
	public default <V> IReferenceEnumerable<IReferenceGroup<V, T>> groupBy(ISelector<T, V> selector){
		DefaultValueHashMap<V, ReferenceGroup<V, T>> hashMap = new DefaultValueHashMap<>(key->new ReferenceGroup(key));
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			T value = enumerator.next();
			V key = selector.select(value);
			ReferenceGroup<V, T> group = hashMap.get(key);
			group.values.add(value);
		}
		return new IterableEnumerable(hashMap.values());
	}
	
	@SuppressWarnings("unchecked")
	public default IReferenceEnumerable<T> sort(){
		return new OrderByEnumerable<T>(this, (t1, t2)->{
			return Comparators.compare((Comparable)t1, (Comparable)t2);
		});
	}

}

class IterableEnumerable<T> implements IReferenceEnumerable<T>{
	
	private final Iterable<T> iterable;
	
	public IterableEnumerable(Iterable<T> iterable) {
		this.iterable = iterable;
	}

	@Override
	public IEnumerator<T> iterator() {
		return new Enumerator();
	}
	
	private class Enumerator implements IEnumerator<T>{

		private final Iterator<T> iterator = iterable.iterator();
		
		private T current;
		
		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public void moveNext() {
			current = iterator.next();
		}

		@Override
		public T current() {
			return current;
		}
		
	}
	
}

class ArrayEnumerable<T> implements IReferenceEnumerable<T>{

	private final T[] array;
	
	public ArrayEnumerable(T[] array) {
		this.array = array;
	}
	
	@Override
	public IEnumerator<T> iterator() {
		return new Enumerator();
	}

	private class Enumerator implements IEnumerator<T>{

		private int index = -1;
		
		@Override
		public boolean hasNext() {
			return index+1 < array.length;
		}

		@Override
		public void moveNext() {
			++index;
		}

		@Override
		public T current() {
			return array[index];
		}
		
	}
	
}
