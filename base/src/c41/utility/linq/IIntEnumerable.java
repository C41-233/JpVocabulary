package c41.utility.linq;

import java.util.List;

import c41.utility.assertion.Arguments;

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
		Arguments.is(index>=0, "%d < 0", index);
		
		IIntEnumerator enumerator = iterator();
		for(int i = 0; i < index; i++) {
			if(!enumerator.hasNext()) {
				throw EnumeratorException.throwAfter();
			}
			enumerator.moveNext();
		}
		if(enumerator.hasNext()) {
			return enumerator.nextInt();
		}
		throw EnumeratorException.throwAfter();
	}
	
}
