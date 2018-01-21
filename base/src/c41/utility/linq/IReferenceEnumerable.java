package c41.utility.linq;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import c41.lambda.action.IAction1;
import c41.lambda.action.IForeachAction;
import c41.lambda.function.IBooleanFunction1;
import c41.lambda.function.IForeachFunction;
import c41.lambda.function.IFunction;
import c41.lambda.function.IJoiner;
import c41.lambda.predicate.ICharPredicate;
import c41.lambda.predicate.IIntPredicate;
import c41.lambda.predicate.IPredicate;
import c41.lambda.selector.ISelector;
import c41.lambda.selector.ISelectorEx;
import c41.utility.algorithm.LinearSearch;
import c41.utility.assertion.Arguments;
import c41.utility.collection.Iterables;
import c41.utility.collection.tuple.Tuple2;
import c41.utility.collection.tuple.Tuples;
import c41.utility.comparator.Comparators;

/**
 * 引用类型的Enumerable。
 * @param <T> 元素类型
 */
public interface IReferenceEnumerable<T> extends IEnumerable<T>{

	/**
	 * 查询指定下标的元素。
	 * @param index 下标
	 * @return 指定元素
	 * @throws IllegalArgumentException index &lt; 0
	 * @throws NoSuchElementException 下标超出迭代器范围
	 */
	public default T at(int index) {
		return Iterables.at(this, index);
	}

	public default int count(IPredicate<? super T> predicate) {
		return Iterables.count(this, predicate);
	}

	public default T findFirst(IPredicate<? super T> predicate) {
		return Iterables.findFirst(this, predicate);
	}
	
	public default T findFirstDuplicate() {
		return Iterables.findFirstDuplicate(this);
	}

	public default T findFirstDuplicateOrCreateDefault(IFunction<? extends T> defProvider) {
		return Iterables.findFirstDuplicateOrCreateDefault(this, defProvider);
	}
	
	public default T findFirstDuplicateOrDefault() {
		return Iterables.findFirstDuplicateOrDefault(this);
	}
	
	public default T findFirstDuplicateOrDefault(T def) {
		return Iterables.findFirstDuplicateOrDefault(this, def);
	}
	
	public default int findFirstIndex(IPredicate<? super T> predicate) {
		return Iterables.findFirstIndex(this, predicate);
	}

	public default int findFirstIndex(T value) {
		return Iterables.findFirstIndex(this, value);
	}
	
	public default T findFirstOrCreateDefault(IPredicate<? super T> predicate, IFunction<? extends T> defProvider) {
		return Iterables.findFirstOrCreateDefault(this, predicate, defProvider);
	}
	
	public default T findFirstOrDefault(IPredicate<? super T> predicate) {
		return Iterables.findFirstOrDefault(this, predicate);
	}

	public default T findFirstOrDefault(IPredicate<? super T> predicate, T def) {
		return Iterables.findFirstOrDefault(this, predicate, def);
	}
	
	public default T first() {
		return Iterables.first(this);
	}

	/**
	 * 对每个元素执行操作。
	 * @param action 对每个元素执行的操作
	 * @return 执行的次数
	 */
	public default int foreach(IAction1<? super T> action) {
		return Iterables.foreach(this, action);
	}

	public default int foreachEx(IBooleanFunction1<? super T> function) {
		return Iterables.foreachEx(this, function);
	}

	/**
	 * 对每个元素执行操作。
	 * @param action 对每个元素执行的操作，参数包含当前元素及其下标
	 * @return 执行的次数
	 */
	public default int foreach(IForeachAction<? super T> action) {
		return Iterables.foreach(this, action);
	}
	
	public default int foreachEx(IForeachFunction<? super T> function) {
		return Iterables.foreachEx(this, function);
	}
	
	public default <K> IReferenceGroupEnumerable<K, T> groupBy(ISelector<T, K> selector){
		return new GroupByReferenceEnumerable<>(this, selector);
	}
	
	public default <V> IReferenceEnumerable<V> instanceOf(Class<V> cl){
		Arguments.isNotNull(cl);
		return where(c->cl.isInstance(c)).cast();
	}

	/**
	 * 所有元素都满足谓词。
	 * @param predicate 谓词
	 * @return 如果所有元素都满足谓词，则返回true
	 * @see ICharEnumerable#isAll(ICharPredicate)
	 * @see IIntEnumerable#isAll(IIntPredicate)
	 */
	public default boolean isAll(IPredicate<? super T> predicate) {
		return Iterables.isAll(this, predicate);
	}

	/**
	 * 存在满足谓词的元素。
	 * @param predicate 谓词
	 * @return 如果存在，则返回true
	 */
	public default boolean isExist(IPredicate<? super T> predicate) {
		return Iterables.isExist(this, predicate);
	}

	/**
	 * 存在与{@code value}相等的元素。
	 * 比较以{@code equals}的方式进行。
	 * @param value 元素
	 * @return 如果存在，则返回true
	 */
	public default boolean isExist(T value) {
		return Iterables.isExist(this, value);
	}
	
	/**
	 * 存在与@{code value}引用相同的元素。
	 * @param value 元素
	 * @return 如果存在，则返回true
	 */
	public default boolean isExistReference(T value) {
		return Iterables.isExistReference(this, value);
	}

	/**
	 * 非所有元素都满足谓词。
	 * @param predicate 谓词
	 * @return 如果非所有元素都满足谓词，返回true
	 */
	public default boolean isNotAll(IPredicate<? super T> predicate) {
		return Iterables.isNotAll(this, predicate);
	}

	/**
	 * 不存在满足谓词的元素。
	 * @param predicate 谓词
	 * @return 如果不存在，则返回true
	 */
	public default boolean isNotExist(IPredicate<? super T> predicate) {
		return Iterables.isNotExist(this, predicate);
	}

	/**
	 * 不存在与{@code value}相等的元素。
	 * 比较以{@code equals}的方式进行。
	 * @param value 元素
	 * @return 如果不存在，则返回true
	 */
	public default boolean isNotExist(T value) {
		return Iterables.isNotExist(this, value);
	}

	/**
	 * 不存在与任何一个指定元素相等的元素。
	 * @param values 多个指定元素
	 * @return 如果不存在，则返回true
	 */
	public default boolean isNotExistAnyOf(Object... values){
		Arguments.isNotNull(values);
		
		if(values.length <= 16) {
			return isNotExist(obj->LinearSearch.isExist(values, obj));
		}
		
		HashSet<Object> set = new HashSet<>();
		for(Object value : values) {
			set.add(value);
		}
		return isNotExist(obj->set.contains(obj));
	}

	/**
	 * 不存在与{@code value}引用相同的元素。
	 * @param value 元素
	 * @return 如果不存在，则返回true
	 */
	public default boolean isNotExistReference(T value) {
		return isNotExist(obj->obj == value);
	}
	
	@Override
	public IEnumerator<T> iterator();
	
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
	
	public default IReferenceSortedEnumerable<T> orderBy(Comparator<? super T> comparator){
		Arguments.isNotNull(comparator);
		return new OrderByEnumerable<>(this, comparator);
	}
	
	public default <V extends Comparable<? super V>> IReferenceSortedEnumerable<T> orderBy(ISelector<? super T, V> selector){
		Arguments.isNotNull(selector);
		return new OrderByEnumerable<>(this, (t1, t2)->Comparators.compare(selector.select(t1), selector.select(t2)));
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
	public default IReferenceSortedEnumerable<T> orderBySelf(){
		return new OrderByEnumerable<>(this, (t1, t2)->{
			return Comparators.compare((Comparable)t1, (Comparable)t2);
		});
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
	
	public default IReferenceEnumerable<T> union(Iterable<? extends T> iterable){
		return new UnionEnumerable<>(this, Linq.from(iterable));
	}
	
	public default IReferenceEnumerable<T> where(IPredicate<? super T> predicate){
		return new WhereEnumerable<>(this, predicate);
	}
	
}