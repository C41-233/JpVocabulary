package c41.utility.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import c41.lambda.action.IAction1;
import c41.lambda.action.IForeachAction;
import c41.lambda.function.IFunction;
import c41.lambda.predicate.IPredicate;
import c41.reflect.StaticClassException;
import c41.utility.assertion.Arguments;

public final class Iterators {

	private Iterators() {
		throw new StaticClassException();
	}

	public static <T> T at(Iterator<T> iterator, int index) {
		Arguments.isNotNull(iterator);
		Arguments.is(index>=0, "%d < 0", index);
		
		for(int i=0; i<index; i++) {
			if(!iterator.hasNext()) {
				throw new NoSuchElementException();
			}
			iterator.next();
		}
		if(iterator.hasNext()) {
			return iterator.next();
		}
		throw new NoSuchElementException();
	}

	public static int count(Iterator<?> iterator) {
		Arguments.isNotNull(iterator);
		
		int count = 0;
		while(iterator.hasNext()) {
			iterator.next();
			count++;
		}
		return count;
	}

	public static <T> int count(Iterator<T> iterator, IPredicate<? super T> predicate) {
		Arguments.isNotNull(iterator);
		Arguments.isNotNull(predicate);
		
		int count = 0;
		while(iterator.hasNext()) {
			T obj = iterator.next();
			if(predicate.is(obj)) {
				count++;
			}
		}
		return count;
	}

	@SuppressWarnings("unlikely-arg-type")
	public static <T, V> boolean equals(Iterator<T> iterator1, Iterator<V> iterator2) {
		if(iterator1 == iterator2) {
			return true;
		}
		
		if(iterator1 == null || iterator2 == null) {
			return false;
		}
		
		while(iterator1.hasNext() && iterator2.hasNext()) {
			if(Objects.equals(iterator1.next(), iterator2.next()) == false) {
				return false;
			}
		}
		if(iterator1.hasNext() || iterator2.hasNext()) {
			return false;
		}
		return true;
	}

	public static <T> T findFirst(Iterator<T> iterator, IPredicate<? super T> predicate) {
		return findFirstOrCreateDefault(iterator, predicate, ()->{
			throw new NoSuchElementException();
		});
	}

	public static <T> T findFirstDuplicate(Iterator<T> iterator) {
		return findFirstDuplicateOrCreateDefault(iterator, ()->{
			throw new NoSuchElementException();
		});
	}

	public static <T> T findFirstDuplicateOrCreateDefault(Iterator<T> iterator, IFunction<? extends T> defProvider){
		Arguments.isNotNull(iterator);
		Arguments.isNotNull(defProvider);
		
		HashSet<T> set = new HashSet<>();
		while(iterator.hasNext()) {
			T obj = iterator.next();
			if(set.add(obj) == false) {
				return obj;
			}
		}
		return defProvider.invoke();
	}

	public static <T> T findFirstDuplicateOrDefault(Iterator<T> iterator) {
		return findFirstDuplicateOrDefault(iterator, null);
	}

	public static <T> T findFirstDuplicateOrDefault(Iterator<T> iterator, T def) {
		return findFirstDuplicateOrCreateDefault(iterator, ()->def);
	}

	public static <T> int findFirstIndex(Iterator<T> iterator, IPredicate<? super T> predicate) {
		Arguments.isNotNull(iterator);
		Arguments.isNotNull(predicate);
	
		int index = 0;
		while(iterator.hasNext()) {
			T obj = iterator.next();
			if(predicate.is(obj)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public static <T> int findFirstIndex(Iterator<T> iterator, T value) {
		Arguments.isNotNull(iterator);
		
		int index = 0;
		while(iterator.hasNext()) {
			T obj = iterator.next();
			if(Objects.equals(obj, value)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public static <T> T findFirstOrCreateDefault(Iterator<T> iterator, IPredicate<? super T> predicate, IFunction<? extends T> defProvider) {
		Arguments.isNotNull(iterator);
		Arguments.isNotNull(predicate);
		Arguments.isNotNull(defProvider);
		
		while(iterator.hasNext()) {
			T obj = iterator.next();
			if(predicate.is(obj)) {
				return obj;
			}
		}
		return defProvider.invoke();
	}

	public static <T> T findFirstOrDefault(Iterator<T> iterable, IPredicate<? super T> predicate) {
		return findFirstOrDefault(iterable, predicate, null);
	}

	public static <T> T findFirstOrDefault(Iterator<T> iterator, IPredicate<? super T> predicate, T def) {
		return findFirstOrCreateDefault(iterator, predicate, ()->def);
	}

	public static <T> T first(Iterator<T> iterator) {
		Arguments.isNotNull(iterator);
		return iterator.next();
	}

	public static boolean hasDuplicate(Iterator<?> iterator) {
		Arguments.isNotNull(iterator);
	
		HashSet<Object> set = new HashSet<>();
		while(iterator.hasNext()) {
			if(set.add(iterator.next()) == false) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 迭代器所有元素都满足谓词。
	 * @param <T> 泛型参数
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

	public static boolean isEmpty(Iterator<?> iterator) {
		Arguments.isNotNull(iterator);
		return iterator.hasNext() == false;
	}

	/**
	 * 迭代器存在满足谓词的元素。
	 * @param <T> 泛型参数
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

	/**
	 * 迭代器非所有元素都满足谓词。
	 * @param <T> 泛型参数
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

	public static boolean isNotEmpty(Iterator<?> iterable) {
		return isEmpty(iterable) == false;
	}

	public static <T, TCollection extends Collection<T>> TCollection toCollection(Iterator<T> iterator, IFunction<TCollection> provider) {
		Arguments.isNotNull(iterator);
		Arguments.isNotNull(provider);
		
		TCollection collection = provider.invoke();
		while(iterator.hasNext()) {
			collection.add(iterator.next());
		}
		return collection;
	}

	public static <T> List<T> toList(Iterator<T> iterator){
		return toCollection(iterator, ()->new ArrayList<>());
	}
	
	public static <T> Set<T> toSet(Iterator<T> iterator){
		return toCollection(iterator, ()->new HashSet<>());
	}

	/**
	 * 对每个元素执行操作。
	 * @param <T> 泛型参数
	 * @param iterator 迭代器
	 * @param action 对每个元素执行的操作
	 * @return 执行的次数
	 */
	public static <T> int foreach(Iterator<T> iterator, IAction1<? super T> action) {
		Arguments.isNotNull(iterator);
		Arguments.isNotNull(action);
		
		int count = 0;
		while(iterator.hasNext()) {
			action.invoke(iterator.next());
			count++;
		}
		return count;
	}

	/**
	 * 对每个元素执行操作。
	 * @param <T> 泛型参数
	 * @param iterator 迭代器
	 * @param action 对每个元素执行的操作，参数包含当前元素及其下标
	 * @return 执行的次数
	 */
	public static <T> int foreach(Iterator<T> iterator, IForeachAction<? super T> action) {
		Arguments.isNotNull(iterator);
		Arguments.isNotNull(action);
		
		int count = 0;
		while(iterator.hasNext()) {
			action.invoke(iterator.next(), count++);
		}
		return count;
	}
}
