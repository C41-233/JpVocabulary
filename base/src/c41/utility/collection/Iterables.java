package c41.utility.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import c41.lambda.function.IFunction;
import c41.lambda.predicate.IPredicate;
import c41.reflect.StaticClassException;
import c41.utility.assertion.Arguments;

public final class Iterables {

	private Iterables() {
		throw new StaticClassException();
	}
	
	public static <T> T at(Iterable<T> iterable, int index) {
		Arguments.isNotNull(iterable);
		Arguments.is(index>=0, "%d < 0", index);
		
		Iterator<T> iterator = iterable.iterator();
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

	public static int count(Iterable<?> iterable) {
		Arguments.isNotNull(iterable);
		
		Iterator<?> enumerator = iterable.iterator();
		int count = 0;
		while(enumerator.hasNext()) {
			enumerator.next();
			count++;
		}
		return count;
	}

	public static <T> int count(Iterable<T> iterable, IPredicate<? super T> predicate) {
		Arguments.isNotNull(iterable);
		Arguments.isNotNull(predicate);
		
		int count = 0;
		Iterator<T> iterator = iterable.iterator();
		while(iterator.hasNext()) {
			T obj = iterator.next();
			if(predicate.is(obj)) {
				count++;
			}
		}
		return count;
	}

	@SuppressWarnings("unlikely-arg-type")
	public static <T, V> boolean equals(Iterable<T> it1, Iterable<V> it2) {
		Arguments.isNotNull(it1);
		Arguments.isNotNull(it2);
		
		Iterator<T> iterator1 = it1.iterator();
		Iterator<V> iterator2 = it2.iterator();
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

	public static <T> T findFirst(Iterable<T> iterable, IPredicate<? super T> predicate) {
		Arguments.isNotNull(iterable);
		Arguments.isNotNull(predicate);
		
		Iterator<T> iterator = iterable.iterator();
		while(iterator.hasNext()) {
			T obj = iterator.next();
			if(predicate.is(obj)) {
				return obj;
			}
		}
		throw new NoSuchElementException();
	}
	
	public static <T> T findFirstDuplicate(Iterable<T> iterable) {
		Arguments.isNotNull(iterable);
		
		HashSet<T> set = new HashSet<>();
		Iterator<T> iterator = iterable.iterator();
		while(iterator.hasNext()) {
			T obj = iterator.next();
			if(set.add(obj) == false) {
				return obj;
			}
		}
		throw new NoSuchElementException();
	}

	public static <T> T findFirstDuplicateOrDefault(Iterable<T> iterable) {
		return findFirstDuplicateOrDefault(iterable, null);
	}
	
	public static <T> T findFirstDuplicateOrDefault(Iterable<T> iterable, T def) {
		Arguments.isNotNull(iterable);
		
		HashSet<T> set = new HashSet<>();
		Iterator<T> iterator = iterable.iterator();
		while(iterator.hasNext()) {
			T obj = iterator.next();
			if(set.add(obj) == false) {
				return obj;
			}
		}
		return def;
	}
	
	public static <T> T findFirstOrDefault(Iterable<T> iterable, IPredicate<? super T> predicate) {
		return findFirstOrDefault(iterable, predicate, null);
	}

	public static <T> T findFirstOrDefault(Iterable<T> iterable, IPredicate<? super T> predicate, T def) {
		Arguments.isNotNull(iterable);
		Arguments.isNotNull(predicate);
		
		Iterator<T> iterator = iterable.iterator();
		while(iterator.hasNext()) {
			T obj = iterator.next();
			if(predicate.is(obj)) {
				return obj;
			}
		}
		return def;
	}
	
	public static boolean hasDuplicate(Iterable<?> iterable) {
		Arguments.isNotNull(iterable);

		HashSet<Object> set = new HashSet<>();
		Iterator<?> iterator = iterable.iterator();
		while(iterator.hasNext()) {
			if(set.add(iterator.next()) == false) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 所有元素都满足谓词。
	 * @param <T> 泛型参数
	 * @param iterable 容器
	 * @param predicate 谓词
	 * @return 如果所有元素都满足谓词，则返回true
	 */
	public static <T> boolean isAll(Iterable<T> iterable, IPredicate<? super T> predicate) {
		Arguments.isNotNull(iterable);
		return Iterables.isAll(iterable.iterator(), predicate);
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

	public static boolean isEmpty(Iterable<?> iterable) {
		Arguments.isNotNull(iterable);
		return iterable.iterator().hasNext() == false;
	}

	/**
	 * 存在满足谓词的元素。
	 * @param <T> 泛型参数
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
	 * 非所有元素都满足谓词。
	 * @param <T> 泛型参数
	 * @param iterable 容器
	 * @param predicate 谓词
	 * @return 如果非所有元素都满足谓词，返回true
	 */
	public static <T> boolean isNotAll(Iterable<T> iterable, IPredicate<? super T> predicate) {
		Arguments.isNotNull(iterable);
		return Iterables.isNotAll(iterable.iterator(), predicate);
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
	
	public static boolean isNotEmpty(Iterable<?> iterable) {
		return isEmpty(iterable) == false;
	}
	
	public static <T, TCollection extends Collection<T>> TCollection toCollection(Iterable<T> iterable, IFunction<TCollection> provider) {
		Arguments.isNotNull(iterable);
		Arguments.isNotNull(provider);
		
		TCollection collection = provider.invoke();
		Iterator<T> iterator = iterable.iterator();
		while(iterator.hasNext()) {
			collection.add(iterator.next());
		}
		return collection;
	}
	
	public static <T> List<T> toList(Iterable<T> iterable){
		return toCollection(iterable, ()->new ArrayList<>());
	}
	
	public static <T> Set<T> toSet(Iterable<T> iterable){
		return toCollection(iterable, ()->new HashSet<>());
	}
	
}
