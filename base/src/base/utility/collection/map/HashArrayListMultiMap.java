package base.utility.collection.map;

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
	}
	
	private ArrayList<V> getListOrCreate(K key){
		ArrayList<V> list = data.get(key);
		if(list == null) {
			list = new ArrayList<>();
			data.put(key, list);
		}
		return list;
	}
	
	public Iterable<V> values(K key){
		ArrayList<V> list = data.get(key);
		return list == null ? Collections.emptyList() : list;
	}
	
}
