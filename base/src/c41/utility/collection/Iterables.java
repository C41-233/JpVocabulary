package c41.utility.collection;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import c41.lambda.action.IAction1;
import c41.lambda.action.IForeachAction;
import c41.lambda.function.IBooleanFunction1;
import c41.lambda.function.IForeachFunction;
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
		return Iterators.at(iterable.iterator(), index);
	}

	public static int count(Iterable<?> iterable) {
		Arguments.isNotNull(iterable);
		return Iterators.count(iterable.iterator());
	}

	public static <T> int count(Iterable<T> iterable, IPredicate<? super T> predicate) {
		Arguments.isNotNull(iterable);
		return Iterators.count(iterable.iterator(), predicate);
	}

	public static <T, V> boolean equals(Iterable<T> iterable1, Iterable<V> iterable2) {
		if(iterable1 == iterable2) {
			return true;
		}
		if(iterable1 == null || iterable2 == null) {
			return false;
		}
		return Iterators.equals(iterable1.iterator(), iterable2.iterator());
	}

	public static <T> T findFirst(Iterable<T> iterable, IPredicate<? super T> predicate) {
		Arguments.isNotNull(iterable);
		return Iterators.findFirst(iterable.iterator(), predicate);
	}
	
	public static <T> T findFirstDuplicate(Iterable<T> iterable) {
		Arguments.isNotNull(iterable);
		return Iterators.findFirstDuplicate(iterable.iterator());
	}

	public static <T> T findFirstDuplicateOrCreateDefault(Iterable<T> iterable, IFunction<? extends T> defProvider){
		Arguments.isNotNull(iterable);
		return Iterators.findFirstDuplicateOrCreateDefault(iterable.iterator(), defProvider);
	}
	
	public static <T> T findFirstDuplicateOrDefault(Iterable<T> iterable) {
		Arguments.isNotNull(iterable);
		return Iterators.findFirstDuplicateOrDefault(iterable.iterator());
	}

	public static <T> T findFirstDuplicateOrDefault(Iterable<T> iterable, T def) {
		Arguments.isNotNull(iterable);
		return Iterators.findFirstDuplicateOrDefault(iterable.iterator(), def);
	}
	
	public static <T> int findFirstIndex(Iterable<T> iterable, IPredicate<? super T> predicate) {
		Arguments.isNotNull(iterable);
		return Iterators.findFirstIndex(iterable.iterator(), predicate);
	}

	public static <T> int findFirstIndex(Iterable<T> iterable, T value) {
		Arguments.isNotNull(iterable);
		return Iterators.findFirstIndex(iterable.iterator(), value);
	}
	
	public static <T> T findFirstOrCreateDefault(Iterable<T> iterable, IPredicate<? super T> predicate, IFunction<? extends T> defProvider) {
		Arguments.isNotNull(iterable);
		return Iterators.findFirstOrCreateDefault(iterable.iterator(), predicate, defProvider);
	}
	
	public static <T> T findFirstOrDefault(Iterable<T> iterable, IPredicate<? super T> predicate) {
		Arguments.isNotNull(iterable);
		return Iterators.findFirstOrDefault(iterable.iterator(), predicate);
	}

	public static <T> T findFirstOrDefault(Iterable<T> iterable, IPredicate<? super T> predicate, T def) {
		Arguments.isNotNull(iterable);
		return Iterators.findFirstOrDefault(iterable.iterator(), predicate, def);
	}

	public static <T> T first(Iterable<T> iterable) {
		Arguments.isNotNull(iterable);
		return Iterators.first(iterable.iterator());
	}

	/**
	 * 对每个元素执行操作。
	 * @param <T> 泛型参数
	 * @param iterable 迭代器
	 * @param action 对每个元素执行的操作
	 * @return 执行的次数
	 */
	public static <T> int foreach(Iterable<T> iterable, IAction1<? super T> action) {		
		Arguments.isNotNull(iterable);
		return Iterators.foreach(iterable.iterator(), action);
	}

	/**
	 * 对每个元素执行操作。
	 * @param <T> 泛型参数
	 * @param iterable 迭代器
	 * @param function 对每个元素执行的操作，返回false表示break
	 * @return 执行的次数
	 */
	public static <T> int foreach(Iterable<T> iterable, IBooleanFunction1<? super T> function) {
		Arguments.isNotNull(iterable);
		return Iterators.foreach(iterable.iterator(), function);
	}
	
	/**
	 * 对每个元素执行操作。
	 * @param <T> 泛型参数
	 * @param iterable 迭代器
	 * @param action 对每个元素执行的操作，参数包含当前元素及其下标
	 * @return 执行的次数
	 */
	public static <T> int foreach(Iterable<T> iterable, IForeachAction<? super T> action) {	
		Arguments.isNotNull(iterable);
		return Iterators.foreach(iterable.iterator(), action);
	}
	
	/**
	 * 对每个元素执行操作。
	 * @param <T> 泛型参数
	 * @param iterable 迭代器
	 * @param function 对每个元素执行的操作，参数包含当前元素及其下标，返回false表示break
	 * @return 执行的次数
	 */
	public static <T> int foreach(Iterable<T> iterable, IForeachFunction<? super T> function) {
		Arguments.isNotNull(iterable);
		return Iterators.foreach(iterable.iterator(), function);
	}
	
	public static boolean hasDuplicate(Iterable<?> iterable) {
		Arguments.isNotNull(iterable);
		return Iterators.hasDuplicate(iterable.iterator());
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
		return Iterators.isAll(iterable.iterator(), predicate);
	}
	
	public static boolean isEmpty(Iterable<?> iterable) {
		Arguments.isNotNull(iterable);
		return Iterators.isEmpty(iterable.iterator());
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
	 * 非所有元素都满足谓词。
	 * @param <T> 泛型参数
	 * @param iterable 容器
	 * @param predicate 谓词
	 * @return 如果非所有元素都满足谓词，返回true
	 */
	public static <T> boolean isNotAll(Iterable<T> iterable, IPredicate<? super T> predicate) {
		Arguments.isNotNull(iterable);
		return Iterators.isNotAll(iterable.iterator(), predicate);
	}

	public static boolean isNotEmpty(Iterable<?> iterable) {
		Arguments.isNotNull(iterable);
		return Iterators.isNotEmpty(iterable.iterator());
	}

	public static <T, TCollection extends Collection<T>> TCollection toCollection(Iterable<T> iterable, IFunction<TCollection> provider) {
		Arguments.isNotNull(iterable);
		return Iterators.toCollection(iterable.iterator(), provider);
	}

	public static <T> List<T> toList(Iterable<T> iterable){
		Arguments.isNotNull(iterable);
		return Iterators.toList(iterable.iterator());
	}
	public static <T> Set<T> toSet(Iterable<T> iterable){
		Arguments.isNotNull(iterable);
		return Iterators.toSet(iterable.iterator());
	}
}
