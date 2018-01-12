package c41.utility.linq;

import java.util.List;

/**
 * 基本类型int的Enumerable。
 */
public interface IIntEnumerable extends IEnumerable<Integer>{

	@Override
	public IIntEnumerator iterator();
	
	public default int[] toArray() {
		List<Integer> list = toList();
		int[] array = new int[list.size()];
		for(int i=0; i<list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}
	
	public default int at(int index) {
		IIntEnumerator enumerator = iterator();
		for(int i=0; i<index && enumerator.hasNext(); i++, enumerator.moveNext());
		if(enumerator.hasNext()) {
			return enumerator.nextInt();
		}
		else {
			throw EnumeratorException.throwAfter();
		}
	}
	
}
