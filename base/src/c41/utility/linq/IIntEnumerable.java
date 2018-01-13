package c41.utility.linq;

import java.util.List;

import c41.utility.assertion.Arguments;
import c41.utility.lambda.predicate.ICharPredicate;
import c41.utility.lambda.predicate.IIntPredicate;
import c41.utility.lambda.predicate.IPredicate;

/**
 * 基本类型int的Enumerable。
 */
public interface IIntEnumerable extends IEnumerable<Integer>{

	@Override
	public IIntEnumerator iterator();

	/**
	 * 所有元素都满足谓词。
	 * @param predicate 谓词
	 * @return 如果所有元素都满足谓词，则返回true
	 * @see IReferenceEnumerable#isAll(IPredicate)
	 * @see ICharEnumerable#isAll(ICharPredicate)
	 */
	public default boolean isAll(IIntPredicate predicate) {
		Arguments.isNotNull(predicate);
		
		IIntEnumerator enumerator = iterator();
		while(enumerator.hasNext()) {
			int val = enumerator.nextInt();
			if(predicate.is(val) == false) {
				return false;
			}
		}
		return true;
	}
	
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
