package c41.utility.collection.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class HashArrayListMultiMap<K, V> implements IMultiMap<K, V>{

	private final HashMap<K, ArrayList<V>> data;
	
	private int size;
	
	public HashArrayListMultiMap() {
		data = new HashMap<>();
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public int count(K key) {
		ArrayList<V> list = data.get(key);
		return list == null ? 0 : list.size();
	}

	@Override
	public void put(K key, V value) {
		ArrayList<V> list = getListOrCreate(key);
		list.add(value);
		size++;
	}
	
	private ArrayList<V> getListOrCreate(K key){
		ArrayList<V> list = data.get(key);
		if(list == null) {
			list = new ArrayList<>();
			data.put(key, list);
		}
		return list;
	}

	@Override
	public boolean remove(K key, V value) {
		ArrayList<V> list = data.get(key);
		if(list == null) {
			return false;
		}
		
		boolean del = list.remove(value);
		if(del) {
			size--;
		}
		return del;
	}
	
	@Override
	public boolean removeAll(K key) {
		ArrayList<V> list = data.get(key);
		if(list == null) {
			return false;
		}
		
		size -= list.size();
		return true;
	}

	@Override
	public V getOne(String key) {
		ArrayList<V> list = data.get(key);
		return list == null ? null : list.get(0);
	}

	@Override
	public Iterable<V> getAll(K key){
		ArrayList<V> list = data.get(key);
		return list == null ? Collections.emptyList() : list;
	}

	@Override
	public boolean containsKey(K key) {
		return data.containsKey(key);
	}

}
