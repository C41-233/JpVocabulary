package base.utility.linq;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface IEnumerable<T> extends Iterable<T>{

	@Override
	public IEnumerator<T> iterator();
	
	public default int count() {
		IEnumerator<T> enumerator = iterator();
		int count = 0;
		while(enumerator.hasNext()) {
			enumerator.moveNext();
			count++;
		}
		return count;
	}
	
	public default List<T> toList(){
		List<T> list = new ArrayList<>();
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			list.add(enumerator.next());
		}
		return list;
	}
	
	public default Set<T> toSet(){
		HashSet<T> set = new HashSet<>();
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			set.add(enumerator.next());
		}
		return set;
	}

	public default boolean hasDuplicate() {
		HashSet<T> set = new HashSet<>();
		IEnumerator<T> enumerator = iterator();
		while(enumerator.hasNext()) {
			if(set.add(enumerator.next()) == false) {
				return true;
			}
		}
		return false;
	}

	public default boolean isEmpty() {
		IEnumerator<T> enumerator = iterator();
		return enumerator.hasNext() == false;
	}
	
	public default boolean isNotEmpty() {
		return isEmpty() == false;
	}

	public default IEnumerable<T> skip(int n){
		return new SkipEnumerable<T>(this, n);
	}
	
	
	@SuppressWarnings("unchecked")
	public default <V> IReferenceEnumerable<V> cast(){
		return (IReferenceEnumerable<V>) this;
	}
	
	/**
	 * 只允许数值基本类型及其包装类型的转换。
	 */
	public default IIntEnumerable toInt() {
		return new SelectIntEnumerable<T>(this, value->((Number)value).intValue());
	}
	
}
