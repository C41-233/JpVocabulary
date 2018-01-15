package c41.utility.cache;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import c41.lambda.function.IFunction;

public class WeakMemoryCache<K, V>{

	private final HashMap<K, WeakReference<V>> data = new HashMap<>();

	public synchronized void put(K key, V value) {
		data.put(key, new WeakReference<>(value));
	}
	
	public synchronized V get(K key) {
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
	
	public synchronized V getOrCreate(K key, IFunction<V> factory) {
		WeakReference<V> ref = data.get(key);
		if(ref == null) {
			V value = factory.invoke();
			data.put(key, new WeakReference<>(value));
			return value;
		}
		V value = ref.get();
		if(value == null) {
			value = factory.invoke();
			data.put(key, new WeakReference<>(value));
		}
		return value;
	}
	
	public synchronized boolean contains(K key) {
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
