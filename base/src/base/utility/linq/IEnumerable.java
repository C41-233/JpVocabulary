package base.utility.linq;

import java.util.ArrayList;
import java.util.List;

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
	
	public default IEnumerable<T> skip(int n){
		return new SkipEnumerable<T>(this, n);
	}
	
	@SuppressWarnings("unchecked")
	public default <V> IEnumerable<V> cast(){
		return new SelectEnumerable<T, V>(this, t->(V)t);
	}
	
	/**
	 * 只允许数值基本类型及其包装类型的转换。
	 */
	public default IIntEnumerable toInt() {
		return new SelectIntEnumerable<T>(this, value->((Number)value).intValue());
	}
	
}
