package c41.utility.linq;

public interface IReferenceGroupEnumerable<K, V> extends IReferenceEnumerable<IReferenceGroup<K, V>>{
	
	@Override
	public IEnumerator<IReferenceGroup<K, V>> iterator();
	
}
