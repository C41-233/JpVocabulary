package c41.utility.linq;

import java.util.NoSuchElementException;

/**
 * 当Enumerator访问的位置超出查询范围时被抛出的异常。
 */
final class EnumeratorException{

	static NoSuchElementException throwBefore() {
		throw new NoSuchElementException("current before enumerator range");
	}

	static NoSuchElementException throwAfter() {
		throw new NoSuchElementException("move next out of enumerator range");
	}
	
}
