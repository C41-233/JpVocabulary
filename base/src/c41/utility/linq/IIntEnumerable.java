package c41.utility.linq;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import c41.lambda.predicate.ICharPredicate;
import c41.lambda.predicate.*;
import c41.lambda.predicate.IPredicate;
import c41.utility.algorithm.LinearSearch;
import c41.utility.assertion.Arguments;

public interface IIntEnumerable extends IEnumerable<Integer>{

	public default int at(int index) {
		Arguments.is(index>=0, "%d < 0", index);
		
		IIntEnumerator enumerator = iterator();
		for(int i=0; i<index; i++) {
			if(!enumerator.hasNext()) {
				throw new NoSuchElementException();
			}
			enumerator.moveNext();
		}
		if(enumerator.hasNext()) {
			return enumerator.nextInt();
		}
		throw new NoSuchElementException();
	}

	public default boolean isAll(IIntPredicate predicate) {
		Arguments.isNotNull(predicate);
		
		IIntEnumerator enumerator = iterator();
		while(enumerator.hasNext()) {
			int val = enumerator.nextInt();
			if(predicate.is(val) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 存在满足谓词的元素。
	 * @param predicate 谓词
	 * @return 如果存在，则返回true
	 */
	public default boolean isExist(IIntPredicate predicate) {
		Arguments.isNotNull(predicate);
		
		IIntEnumerator enumerator = iterator();
		while(enumerator.hasNext()) {
			int val = enumerator.nextInt();
			if(predicate.is(val)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 存在指定值。
	 * @param value 值
	 * @return 如果存在，则返回true
	 */
	public default boolean isExist(int value) {
		return isExist(val->val == value);
	}

	/**
	 * 非所有元素都满足谓词。
	 * @param predicate 谓词
	 * @return 如果非所有元素都满足谓词，返回true
	 */
	public default boolean isNotAll(IIntPredicate predicate) {
		Arguments.isNotNull(predicate);
		
		IIntEnumerator enumerator = iterator();
		while(enumerator.hasNext()) {
			int val = enumerator.nextInt();
			if(predicate.is(val) == false) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 不存在满足谓词的元素。
	 * @param predicate 谓词
	 * @return 如果不存在，则返回true
	 */
	public default boolean isNotExist(IIntPredicate predicate) {
		Arguments.isNotNull(predicate);
		
		IIntEnumerator enumerator = iterator();
		while(enumerator.hasNext()) {
			int value = enumerator.nextInt();
			if(predicate.is(value)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 不存在指定值。
	 * @param value 元素
	 * @return 如果不存在，则返回true
	 */
	public default boolean isNotExist(int value) {
		return isNotExist(val-> val==value);
	}

	/**
	 * 不存在任何一个指定值。
	 * @param values 多个指定值
	 * @return 如果不存在，则返回true
	 */
	public default boolean isNotExistAnyOf(int...values) {
		Arguments.isNotNull(values);
		
		if(values.length <= 8) {
			return isNotExist(value->LinearSearch.isExist(values, value));
		}
		
		int[] set = values.clone();
		Arrays.sort(set);
		return isNotExist(val->Arrays.binarySearch(set, val)>=0);
	}
	
	@Override
	public IIntEnumerator iterator();
	
	public default int[] toArray() {
		List<Integer> list = toList();
		int[] array = new int[list.size()];
		for(int i=0; i<list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}
	
}
