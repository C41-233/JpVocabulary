package c41.utility.linq;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import c41.utility.assertion.Arguments;

/**
 * Enumerable的基接口。
 * @param <T> 查询元素
 */
public interface IEnumerable<T> extends Iterable<T>{

	@Override
	public IEnumerator<T> iterator();
	
	/**
	 * 返回元素的个数。
	 * @return 元素个数
	 */
	public default int count() {
		IEnumerator<T> enumerator = iterator();
		int count = 0;
		while(enumerator.hasNext()) {
			enumerator.moveNext();
			count++;
		}
		return count;
	}
	
	/**
	 * 将所有元素以List的形式返回，元素的顺序与查询的顺序一致。
	 * @return List
	 */
	public default List<T> toList(){
		List<T> list = new ArrayList<>();
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			list.add(enumerator.next());
		}
		return list;
	}
	
	/**
	 * 将所有元素以Set的形式返回。
	 * @return Set
	 */
	public default Set<T> toSet(){
		HashSet<T> set = new HashSet<>();
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			set.add(enumerator.next());
		}
		return set;
	}

	/**
	 * 是否存在重复元素。
	 * 元素的比较依赖<code>equals</code>。
	 * @return 如果存在重复元素，则返回true。
	 */
	public default boolean hasDuplicate() {
		HashSet<T> set = new HashSet<>();
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			if(set.add(enumerator.next()) == false) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否是一个空查询（元素的个数为0）。
	 * @return 如果是一个空查询，则返回true
	 */
	public default boolean isEmpty() {
		IEnumerator<T> enumerator = iterator();
		return enumerator.hasNext() == false;
	}
	
	/**
	 * 是否是一个非空查询（元素的个数不为0）。
	 * @return 如果是一个非空查询，则返回true
	 */
	public default boolean isNotEmpty() {
		return isEmpty() == false;
	}

	/**
	 * 跳过前n个元素。
	 * 如果在延迟执行时，跳过的个数超出了迭代器范围，则抛出{@link NoSuchElementException}。
	 * @param n 跳过的元素个数
	 * @return 跳过后的查询
	 */
	public default IEnumerable<T> skip(int n){
		Arguments.is(n>=0, "%d < 0", n);
		return new SkipEnumerable<>(this, n);
	}
	
	
	/**
	 * 将元素强制类型转换。
	 * @return 转换后的Enumerable。
	 */
	@SuppressWarnings("unchecked")
	public default <V> IReferenceEnumerable<V> cast(){
		return (IReferenceEnumerable<V>) this;
	}
	
	/**
	 * 将元素转换为int。
	 * @return IIntEnumerable
	 */
	public default IIntEnumerable toInt() {
		return new SelectIntEnumerable<>(this, value->((Number)value).intValue());
	}
	
}
