package c41.utility.linq;

import java.util.Comparator;

import c41.utility.assertion.Arguments;
import c41.utility.comparator.Comparators;
import c41.utility.lambda.selector.ISelector;

/**
 * 引用类型的有序Enumerable。
 * @param <T> 查询元素
 */
public interface IReferenceSortedEnumerable<T> extends IReferenceEnumerable<T>{

	@Override
	public ISortedEnumerator<T> iterator();
	
	public default IReferenceSortedEnumerable<T> thenBy(Comparator<? super T> comparator){
		Arguments.isNotNull(comparator);
		return new ThenByEnumerable<T>(this, comparator);
	}

	public default <V extends Comparable<? super V>> IReferenceSortedEnumerable<T> thenBy(ISelector<T, V> selector){
		Arguments.isNotNull(selector);
		return new ThenByEnumerable<T>(this, (t1, t2)->Comparators.compare(selector.select(t1), selector.select(t2)));
	}
	
	@SuppressWarnings("unchecked")
	public default IReferenceSortedEnumerable<T> thenBySelf(){
		return new ThenByEnumerable<T>(this, (t1, t2)->{
			return Comparators.compare((Comparable)t1, (Comparable)t2);
		});
	}
	
}