package base.utility.linq;

import java.util.Iterator;

/**
 * 迭代器初始位置在第一个元素前，此时不可以访问value
 */
public interface ICharIterator extends Iterator<Character>{

	/**
	 * 迭代器当前值
	 */
	public char current();

	/**
	 * 移动迭代器
	 */
	public void moveNext();
	
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
		return nextChar();
	}

	/**
	 * 移动迭代器，并获取下一个元素
	 */
	public default char nextChar() {
		moveNext();
		return current();
	}
	
}
