package c41.utility.linq;

/**
 * 当Enumerator访问的位置超出查询范围时被抛出的异常。
 */
public class EnumeratorOutOfRangeException extends RuntimeException{

	public EnumeratorOutOfRangeException(String msg) {
		super(msg);
	}
	
	static EnumeratorOutOfRangeException throwBefore() {
		return new EnumeratorOutOfRangeException("current before enumerator range");
	}

	static EnumeratorOutOfRangeException throwAfter() {
		return new EnumeratorOutOfRangeException("move next out of enumerator range");
	}
	
}
