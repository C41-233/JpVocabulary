package c41.utility.linq;

import java.util.List;

import c41.utility.assertion.Arguments;
import c41.utility.lambda.action.ICharAction;
import c41.utility.lambda.action.IForeachCharAction;
import c41.utility.lambda.predicate.ICharPredicate;
import c41.utility.lambda.selector.ICharSelector;

/**
 * 基本类型char的Enumerator。
 */
public interface ICharEnumerable extends IEnumerable<Character>{

	@Override
	public ICharEnumerator iterator();
	
	/**
	 * 所有元素都满足谓词。
	 * @param predicate 谓词
	 * @return 如果所有元素都满足谓词，则返回true
	 */
	public default boolean isAll(ICharPredicate predicate) {
		Arguments.isNotNull(predicate);
		ICharEnumerator enumerator = iterator();
		while(enumerator.hasNext()) {
			char ch = enumerator.nextChar();
			if(predicate.is(ch) == false) {
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
	public default boolean isNotAll(ICharPredicate predicate) {
		Arguments.isNotNull(predicate);
		ICharEnumerator enumerator = iterator();
		while(enumerator.hasNext()) {
			char ch = enumerator.nextChar();
			if(predicate.is(ch) == false) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 将查询转化为一个char数组。
	 * @return char数组
	 */
	public default char[] toArray() {
		List<Character> list = toList();
		char[] array = new char[list.size()];
		for(int i=0; i<list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}
	
	/**
	 * 遍历每一个元素执行Action。
	 * @param action Action
	 */
	public default void foreach(IForeachCharAction action) {
		Arguments.isNotNull(action);
		ICharEnumerator enumerator = iterator();
		int i = 0;
		while(enumerator.hasNext()) {
			char ch = enumerator.nextChar();
			action.invoke(ch, i++);
		}
	}

	/**
	 * 遍历每一个元素执行Action。
	 * @param action Action
	 */
	public default void foreach(ICharAction action) {
		Arguments.isNotNull(action);
		ICharEnumerator enumerator = iterator();
		while(enumerator.hasNext()) {
			char ch = enumerator.nextChar();
			action.invoke(ch);
		}
	}
	
	/**
	 * 对每一个元素进行投影。
	 * @param selector 投影
	 * @return 投影后的元素查询
	 */
	@SuppressWarnings("unchecked")
	public default <V> IReferenceEnumerable<V> select(ICharSelector<? extends V> selector){
		Arguments.isNotNull(selector);
		return new CharSelectEnumerable(this, selector);
	}
	
	@Override
	public default ICharEnumerable skip(int n) {
		Arguments.is(n>=0, "%d < 0", n);
		return new CharSkipEnumerable(this, n);
	}
	
}