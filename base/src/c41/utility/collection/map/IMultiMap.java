package c41.utility.collection.map;

public interface IMultiMap <K, V>{

	public int size();
	
	public int count(K key);
	
	public void put(K key, V value);
	
	public boolean remove(K key, V value);
	
	public boolean removeAll(K key);
	
	public V getOne(String key);
	
	public Iterable<V> getAll(K key);
	
	public boolean containsKey(K key);
	
}
