package base.utility.linq;

import java.util.Comparator;

public interface IReferenceSortedEnumerable<T> extends IReferenceEnumerable<T>{

	@Override
	public ISortedEnumerator<T> iterator();
	
	@SuppressWarnings("unchecked")
	public default IReferenceSortedEnumerable<T> thenBy(Comparator<? super T> comparator){
		return new ThenByEnumerable(this, comparator);
	}
	
}