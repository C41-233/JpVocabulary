package c41.utility.linq;

/**
 * groupBy查询后的组。
 * 
 * @param <TKey> 组所代表的key。
 * @param <TValue> 组所代表的元素。
 */
public interface IReferenceGroup<TKey, TValue> extends IReferenceEnumerable<TValue>{

	public TKey getKey();
	
}