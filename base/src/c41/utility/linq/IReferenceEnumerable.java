package c41.utility.linq;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import c41.lambda.action.IAction1;
import c41.lambda.action.IForeachAction;
import c41.lambda.function.IJoiner;
import c41.lambda.predicate.ICharPredicate;
import c41.lambda.predicate.IIntPredicate;
import c41.lambda.predicate.IPredicate;
import c41.lambda.selector.ISelector;
import c41.lambda.selector.ISelectorEx;
import c41.utility.assertion.Arguments;
import c41.utility.collection.map.DefaultValueHashMap;
import c41.utility.collection.tuple.Tuple2;
import c41.utility.collection.tuple.Tuples;
import c41.utility.comparator.Comparators;

/**
 * 引用类型的Enumerable。
 * @param <T>
 */
public interface IReferenceEnumerable<T> extends IEnumerable<T>{

	@Override
	public IEnumerator<T> iterator();

	/**
	 * 所有元素都满足谓词。
	 * @param predicate 谓词
	 * @return 如果所有元素都满足谓词，则返回true
	 * @see ICharEnumerable#isAll(ICharPredicate)
	 * @see IIntEnumerable#isAll(IIntPredicate)
	 */
	public default boolean isAll(IPredicate<? super T> predicate) {
		Arguments.isNotNull(predicate);
		
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			T obj = enumerator.next();
			if(predicate.is(obj) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 非所有元素都满足谓词。
	 * @param predicate 谓词
	 * @return 如果非所有元素都满足谓词，返回true
	 */
	public default boolean isNotAll(IPredicate<? super T> predicate) {
		Arguments.isNotNull(predicate);
		
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			T obj = enumerator.next();
			if(predicate.is(obj) == false) {
				return true;
			}
		}
		return false;
	}
	
	public default boolean isExist(IPredicate<? super T> predicate) {
		Arguments.isNotNull(predicate);
		
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

	public default boolean isNotExist(IPredicate<? super T> predicate) {
		Arguments.isNotNull(predicate);
		
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			T obj = enumerator.next();
			if(predicate.is(obj)) {
				return false;
			}
		}
		return true;
	}
	
	public default boolean isNotExist(T value) {
		return isNotExist(obj->Objects.equals(obj, value));
	}

	public default boolean isNotExistAnyOf(Object... values){
		HashSet<Object> set = new HashSet<>();
		for(Object value : values) {
			set.add(value);
		}
		return isNotExist(obj->set.contains(obj));
	}
	
	public default T at(int index) {
		Arguments.is(index>=0, "%d < 0", index);
		
		IEnumerator<T> enumerator = iterator();
		for(int i=0; i<index && enumerator.hasNext(); i++) {
			if(!enumerator.hasNext()) {
				throw EnumeratorException.throwAfter();
			}
			enumerator.moveNext();
		}
		if(enumerator.hasNext()) {
			return enumerator.next();
		}
		throw EnumeratorException.throwAfter();
	}

	public default T first() {
		IEnumerator<T> enumerator = iterator();
		if(enumerator.hasNext()) {
			return enumerator.next();
		}
		return null;
	}

	public default T findFirst(IPredicate<? super T> predicate) {
		Arguments.isNotNull(predicate);
		
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			T obj = enumerator.next();
			if(predicate.is(obj)) {
				return obj;
			}
		}
		return null;
	}

	public default int findFirstIndex(T value) {
		int index = 0;
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			T obj = enumerator.next();
			if(Objects.equals(obj, value)) {
				return index;
			}
			index++;
		}
		return -1;
	}
	
	public default int findFirstIndex(IPredicate<? super T> predicate) {
		Arguments.isNotNull(predicate);
		
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

	public default T findFirstDuplicate() {
		HashSet<T> set = new HashSet<>();
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			T obj = enumerator.next();
			if(set.add(obj) == false) {
				return obj;
			}
		}
		return null;
	}

	public default int count(IPredicate<T> predicate) {
		Arguments.isNotNull(predicate);
		
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
		Arguments.isNotNull(type);
		
		List<T> list = toList();
		T[] array = (T[]) Array.newInstance(type, list.size());
		for(int i=0; i<list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}

	public default T[] toArray(T[] array) {
		Arguments.isNotNull(array);
		return toList().toArray(array);
	}

	public default <K> Map<K, IReferenceEnumerable<T>> toMap(ISelector<T, K> selector){
		Arguments.isNotNull(selector);
		
		Map<K, ArrayList<T>> map = new HashMap<>();
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			T obj = enumerator.next();
			K key = selector.select(obj);
			ArrayList<T> list = map.get(key);
			if(list == null) {
				list = new ArrayList<>();
				map.put(key, list);
			}
			list.add(obj);
		}
		
		Map<K, IReferenceEnumerable<T>> rst = new HashMap<>();
		for(Entry<K, ArrayList<T>> kv : map.entrySet()) {
			rst.put(kv.getKey(), new ListEnumerable<>(kv.getValue()));
		}
		return rst;
	}
	
	public default void foreach(IAction1<? super T> action) {
		Arguments.isNotNull(action);
		
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			T obj = enumerator.next();
			action.invoke(obj);
		}
	}
	
	public default void foreach(IForeachAction<? super T> action) {
		Arguments.isNotNull(action);
		
		IEnumerator<T> enumerator = iterator();
		int index = 0;
		while(enumerator.hasNext()) {
			T obj = enumerator.next();
			action.invoke(obj, index++);
		}
	}
	
	public default <V> IReferenceEnumerable<V> select(ISelector<? super T, ? extends V> selector){
		Arguments.isNotNull(selector);
		return new SelectEnumerable<>(this, selector);
	}

	public default <V> IReferenceEnumerable<V> select(ISelectorEx<? super T, ? extends V> selector){
		Arguments.isNotNull(selector);
		return new SelectEnumerable<>(this, selector);
	}
	
	public default <V> IReferenceEnumerable<V> selectMany(ISelector<? super T, ? extends Iterable<? extends V>> selector){
		Arguments.isNotNull(selector);
		return new SelectManyEnumerable<>(this, selector);
	}
	
	public default IReferenceEnumerable<T> where(IPredicate<? super T> predicate){
		Arguments.isNotNull(predicate);
		return new WhereEnumerable<>(this, predicate);
	}
	
	public default <V> IReferenceEnumerable<V> instanceOf(Class<V> cl){
		Arguments.isNotNull(cl);
		return where(c->cl.isInstance(c)).cast();
	}
	
	public default IReferenceSortedEnumerable<T> orderBy(Comparator<? super T> comparator){
		Arguments.isNotNull(comparator);
		return new OrderByEnumerable<>(this, comparator);
	}
	
	public default <V extends Comparable<? super V>> IReferenceSortedEnumerable<T> orderBy(ISelector<? super T, V> selector){
		Arguments.isNotNull(selector);
		return new OrderByEnumerable<>(this, (t1, t2)->Comparators.compare(selector.select(t1), selector.select(t2)));
	}

	@SuppressWarnings("unchecked")
	public default IReferenceSortedEnumerable<T> orderBySelf(){
		return new OrderByEnumerable<>(this, (t1, t2)->{
			return Comparators.compare((Comparable)t1, (Comparable)t2);
		});
	}

	public default IReferenceSortedEnumerable<T> orderByCondition(IPredicate<T> predicate){
		Arguments.isNotNull(predicate);
		
		return new OrderByEnumerable<>(this, (t1, t2)->{
			boolean b1 = predicate.is(t1);
			boolean b2 = predicate.is(t2);
			
			if(b1 && !b2) {
				return -1; 
			}
			if(b2 && !b1) {
				return 1;
			}
			return 0;
		});
	}
	
	@SuppressWarnings("unchecked")
	public default <V> IReferenceEnumerable<IReferenceGroup<V, T>> groupBy(ISelector<T, V> selector){
		Arguments.isNotNull(selector);
		
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
	
	public default IReferenceEnumerable<T> union(Iterable<? extends T> iterable){
		Arguments.isNotNull(iterable);
		return new UnionEnumerable<>(this, Linq.from(iterable));
	}
	
	public default <U> IReferenceEnumerable<Tuple2<T, U>> join(Iterable<U> other){
		Arguments.isNotNull(other);
		return new JoinEnumerable<>(this, other, (t, u)->Tuples.make(t, u));
	}
	
	public default <U, V> IReferenceEnumerable<V> join(Iterable<U> other, IJoiner<T, U, V> joiner){
		Arguments.isNotNull(other);
		Arguments.isNotNull(joiner);
		return new JoinEnumerable<>(this, other, joiner);
	}
	
	public default <U, V> IReferenceEnumerable<V> join(U[] other, IJoiner<T, U, V> joiner){
		Arguments.isNotNull(other);
		Arguments.isNotNull(joiner);
		return new JoinEnumerable<>(this, new ArrayEnumerable<>(other), joiner);
	}
	
}