package base.utility.linq;

import java.util.Comparator;

import base.utility.comparator.Comparators;
import base.utility.function.ISelector;

public interface IReferenceSortedEnumerable<T> extends IReferenceEnumerable<T>{

	@Override
	public ISortedEnumerator<T> iterator();
	
	public default IReferenceSortedEnumerable<T> thenBy(Comparator<? super T> comparator){
		return new ThenByEnumerable<T>(this, comparator);
	}

	public default <V extends Comparable<? super V>> IReferenceSortedEnumerable<T> thenBy(ISelector<T, V> selector){
		return new ThenByEnumerable<T>(this, (t1, t2)->Comparators.compare(selector.select(t1), selector.select(t2)));
	}
	
	@SuppressWarnings("unchecked")
	public default IReferenceSortedEnumerable<T> thenBySelf(){
		return new ThenByEnumerable<T>(this, (t1, t2)->{
			return Comparators.compare((Comparable)t1, (Comparable)t2);
		});
	}
	
}