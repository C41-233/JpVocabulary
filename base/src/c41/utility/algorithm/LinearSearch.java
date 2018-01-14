package c41.utility.algorithm;

import java.util.Iterator;
import java.util.Objects;

import c41.lambda.predicate.IPredicate;
import c41.reflect.StaticClassException;
import c41.utility.assertion.Arguments;

public final class LinearSearch {

	private LinearSearch() {
		throw new StaticClassException();
	}

	/**
	 * 迭代器所有元素都满足谓词。
	 * @param iterator 迭代器
	 * @param predicate 谓词
	 * @return 如果所有元素都满足谓词，则返回true
	 */
	public static <T> boolean isAll(Iterator<T> iterator, IPredicate<? super T> predicate) {
		Arguments.isNotNull(iterator);
		Arguments.isNotNull(predicate);
		
		while(iterator.hasNext()) {
			T obj = iterator.next();
			if(predicate.is(obj) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 所有元素都满足谓词。
	 * @param iterable 容器
	 * @param predicate 谓词
	 * @return 如果所有元素都满足谓词，则返回true
	 */
	public static <T> boolean isAll(Iterable<T> iterable, IPredicate<? super T> predicate) {
		Arguments.isNotNull(iterable);
		return isAll(iterable.iterator(), predicate);
	}

	/**
	 * 迭代器非所有元素都满足谓词。
	 * @param iterator 迭代器
	 * @param predicate 谓词
	 * @return 如果非所有元素都满足谓词，返回true
	 */
	public static <T> boolean isNotAll(Iterator<T> iterator, IPredicate<? super T> predicate) {
		Arguments.isNotNull(iterator);
		Arguments.isNotNull(predicate);
		
		while(iterator.hasNext()) {
			T obj = iterator.next();
			if(predicate.is(obj) == false) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 非所有元素都满足谓词。
	 * @param iterable 容器
	 * @param predicate 谓词
	 * @return 如果非所有元素都满足谓词，返回true
	 */
	public static <T> boolean isNotAll(Iterable<T> iterable, IPredicate<? super T> predicate) {
		Arguments.isNotNull(iterable);
		return isNotAll(iterable.iterator(), predicate);
	}
	
	public static boolean isExist(int[] array, int value) {
		Arguments.isNotNull(array);
		
		for (int i : array) {
			if(i == value) {
				return true;
			}
		}
		return false;
	}

	public static <T> boolean isExist(T[] array, T value) {
		Arguments.isNotNull(array);
		
		for (T obj : array) {
			if(Objects.equals(obj, value)) {
				return true;
			}
		}
		return false;
	}
	
	public static <T> boolean isExist(T[] array, IPredicate<? super T> predicate) {
		Arguments.isNotNull(array);
		Arguments.isNotNull(predicate);
		
		for (T obj : array) {
			if(predicate.is(obj)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 存在满足谓词的元素。
	 * @param iterable 容器
	 * @param predicate 谓词
	 * @return 如果存在，则返回true
	 */
	public static <T> boolean isExist(Iterable<T> iterable, IPredicate<? super T> predicate) {
		Arguments.isNotNull(iterable);
		return isExist(iterable, predicate);
	}
	
	/**
	 * 迭代器存在满足谓词的元素。
	 * @param iterator 迭代器
	 * @param predicate 谓词
	 * @return 如果存在，则返回true
	 */
	public static <T> boolean isExist(Iterator<T> iterator, IPredicate<? super T> predicate) {
		Arguments.isNotNull(iterator);
		Arguments.isNotNull(predicate);
		
		while(iterator.hasNext()) {
			T obj = iterator.next();
			if(predicate.is(obj)) {
				return true;
			}
		}
		return false;
	}
	
}
