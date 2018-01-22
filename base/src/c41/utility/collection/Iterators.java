package c41.utility.collection;

import java.lang.reflect.Array;
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
import c41.lambda.function.IBooleanFunction1;
import c41.lambda.function.IForeachFunction;
import c41.lambda.function.IFunction;
import c41.lambda.predicate.IPredicate;
import c41.reflect.StaticClassException;
import c41.utility.algorithm.LinearSearch;
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

	public static boolean equals(Iterator<?> iterator1, Iterator<?> iterator2) {
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

	/**
	 * 对每个元素执行操作。
	 * @param <T> 泛型参数
	 * @param iterator 迭代器
	 * @param function 对每个元素执行的操作，返回false表示break
	 * @return 执行的次数
	 */
	public static <T> int foreachEx(Iterator<T> iterator, IBooleanFunction1<? super T> function) {
		Arguments.isNotNull(iterator);
		Arguments.isNotNull(function);
		
		int count = 0;
		while(iterator.hasNext()) {
			boolean next = function.invoke(iterator.next());
			if(!next) {
				break;
			}
		}
		return count;
	}

	/**
	 * 对每个元素执行操作。
	 * @param <T> 泛型参数
	 * @param iterator 迭代器
	 * @param function 对每个元素执行的操作，参数包含当前元素及其下标，返回false表示break
	 * @return 执行的次数
	 */
	public static <T> int foreachEx(Iterator<T> iterator, IForeachFunction<? super T> function) {
		Arguments.isNotNull(iterator);
		Arguments.isNotNull(function);
		
		int count = 0;
		while(iterator.hasNext()) {
			boolean next = function.invoke(iterator.next(), count++);
			if(!next) {
				break;
			}
		}
		return count;
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

	public static boolean isExist(Iterator<?> iterator, Object value) {
		Arguments.isNotNull(iterator);
		
		while(iterator.hasNext()) {
			Object obj = iterator.next();
			if(Objects.equals(obj, value)) {
				return true;
			}
		}
		return false;
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
	
	public static boolean isExistReference(Iterator<?> iterator, Object value) {
		Arguments.isNotNull(iterator);
		
		while(iterator.hasNext()) {
			Object obj = iterator.next();
			if(obj == value) {
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

	public static boolean isNotExist(Iterator<?> iterator, Object value){
		Arguments.isNotNull(iterator);

		while(iterator.hasNext()) {
			Object obj = iterator.next();
			if(Objects.equals(obj, value)) {
				return false;
			}
		}
		return true;
	}

	public static <T> boolean isNotExist(Iterator<T> iterator, IPredicate<? super T> predicate) {
		Arguments.isNotNull(iterator);
		Arguments.isNotNull(predicate);

		while(iterator.hasNext()) {
			T obj = iterator.next();
			if(predicate.is(obj)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isNotExistAnyOf(Iterator<?> iterator, Object...values) {
		Arguments.isNotNull(iterator);
		Arguments.isNotNull(values);
		
		if(values.length <= 16) {
			return isNotExist(iterator, (Object obj)->LinearSearch.isExist(values, obj));
		}
		
		HashSet<Object> set = new HashSet<>();
		for(Object value : values) {
			set.add(value);
		}
		return isNotExist(iterator, (Object obj)->set.contains(obj));
	}
	
	public static boolean isNotExistReference(Iterator<?> iterator, Object value) {
		Arguments.isNotNull(iterator);

		while(iterator.hasNext()) {
			Object obj = iterator.next();
			if(obj == value) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(Iterator<T> iterator, Class<T> type) {
		Arguments.isNotNull(type);
		
		List<T> list = toList(iterator);
		T[] array = (T[]) Array.newInstance(type, list.size());
		return list.toArray(array);
	}
	
	public static <T> T[] toArray(Iterator<T> iterator, T[] array) {
		Arguments.isNotNull(array);
		
		List<T> list = toList(iterator); 
		return list.toArray(array);
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
	
}
