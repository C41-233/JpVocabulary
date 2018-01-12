package c41.utility.linq;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Enumerator的基接口。
 * 迭代器初始所在的位置是范围之前，此时不能调用current。
 * @param <T> 查询元素
 */
public interface IEnumerator<T> extends Iterator<T>{

	/**
	 * 迭代器是否可以向后移动一个元素。
	 * 仅当hasNext返回true时，才可以调用next。
	 */
	@Override
	public boolean hasNext();
	
	/**
	 * 迭代器向后移动，并返回下一个元素。
	 * @return 下一个元素
	 * @throws NoSuchElementException 迭代器已经到达末尾
	 */
	@Override
	public default T next() {
		moveNext();
		return current();
	}
	
	/**
	 * 迭代器向后移动
	 * @throws NoSuchElementException 迭代器已经到达末尾
	 */
	public void moveNext();
	
	/**
	 * 获取当前元素。
	 * @return 当前元素
	 * @throws NoSuchElementException 迭代器在范围之前
	 */
	public T current();
	
}
