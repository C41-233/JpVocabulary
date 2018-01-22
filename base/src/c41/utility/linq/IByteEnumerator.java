package c41.utility.linq;

/**
 * 基本类型byte的Enumerator。
 */
public interface IByteEnumerator extends IEnumerator<Byte>{

	@Override
	public default Byte next() {
		moveNext();
		return current();
	}
	
	/**
	 * 迭代器向后移动，并返回下一个元素。
	 * @return 下一个元素
	 */
	public default byte nextByte() {
		moveNext();
		return currentByte();
	}
	
	@Override
	public default Byte current() {
		return currentByte();
	}
	
	/**
	 * 返回当前元素。
	 * @return 当前元素
	 */
	public byte currentByte();
	
}
