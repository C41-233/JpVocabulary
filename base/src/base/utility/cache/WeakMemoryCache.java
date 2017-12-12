package base.utility.cache;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class WeakMemoryCache<K, V>{

	private final HashMap<K, WeakReference<V>> data = new HashMap<>();

	public void put(K key, V value) {
		data.put(key, new WeakReference<V>(value));
	}
	
	public V get(K key) {
		WeakReference<V> ref = data.get(key);
		if(ref == null) {
			return null;
		}
		V value = ref.get();
		if(value == null) {
			data.remove(key);
			return null;
		}
		return value;
	}
	
	public boolean contains(K key) {
		WeakReference<V> ref = data.get(key);
		if(ref == null) {
			return false;
		}
		V value = ref.get();
		if(value == null) {
			data.remove(key);
			return false;
		}
		return true;
	}
	
}
