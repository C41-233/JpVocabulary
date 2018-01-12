package c41.utility.linq;

/**
 * 基本类型char的Enumerator。
 */
public interface ICharEnumerator extends IEnumerator<Character>{

	@Override
	public default Character next() {
		moveNext();
		return current();
	}
	
	/**
	 * 迭代器向后移动，并返回下一个元素。
	 * @return 下一个元素
	 */
	public default char nextChar() {
		moveNext();
		return currentChar();
	}
	
	@Override
	public default Character current() {
		return currentChar();
	}
	
	/**
	 * 返回当前元素。
	 * @return 当前元素
	 */
	public char currentChar();
	
}
