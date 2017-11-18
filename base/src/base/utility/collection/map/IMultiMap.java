package base.utility.collection.map;

public interface IMultiMap <K, V>{

	public int size();
	
	public int count(K key);
	
	public void put(K key, V value);
	
}
