package c41.utility.linq;

/**
 * 有序Enumerator的基接口。
 * @param <T>
 */
public interface ISortedEnumerator<T> extends IEnumerator<T>{

	public boolean hasNextEquals();
	
}
