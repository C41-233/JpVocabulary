package base.utility.linq;

import java.util.Iterator;

/**
 * 迭代器初始位置在第一个元素前，此时不可以访问value
 */
public interface ICharIterator extends Iterator<Character>{

	/**
	 * 迭代器当前值
	 */
	public char value();

	/**
	 * 移动迭代器
	 */
	public void move();
	
	/**
	 * 是否可以移动迭代器
	 */
	@Override
	public boolean hasNext();
	
	/**
	 * 移动迭代器，并获取下一个元素
	 */
	@Override
	public default Character next() {
		moveNext();
		return value();
	}
	
	/**
	 * 移动迭代器
	 * @return 当前元素是否可以访问
	 */
	public default boolean moveNext() {
		if(hasNext()) {
			move();
			return true;
		}
		return false;
	}
	
}
