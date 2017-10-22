package base.utility.collection.map;

import java.util.HashMap;

public class PairHashMap <TKey1, TKey2, TValue>{

	private final HashMap<TKey1, HashMap<TKey2, TValue>> map = new HashMap<>();
	
	public TValue put(TKey1 key1, TKey2 key2, TValue value) {
		HashMap<TKey2, TValue> entry = map.get(key1);
		if(entry == null) {
			entry = new HashMap<TKey2, TValue>();
			map.put(key1, entry);
		}
		return entry.put(key2, value);
	}
	
	public TValue get(TKey1 key1, TKey2 key2) {
		HashMap<TKey2, TValue> entry = map.get(key1);
		if(entry == null) {
			return null;
		}
		return entry.get(key2);
	}
	
	public TValue remove(TKey1 key1, TKey2 key2) {
		HashMap<TKey2, TValue> entry = map.get(key1);
		if(entry == null) {
			return null;
		}
		TValue rst = entry.remove(key2);
		if(entry.size() == 0) {
			map.remove(key1);
		}
		return rst;
	}
	
}
