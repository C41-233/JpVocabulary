package base.utility.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefaultValueHashMap<TKey, TValue> implements Map<TKey, TValue>{

	@FunctionalInterface
	public interface IFactory<TKey, TValue>{
		public TValue create(TKey key);
	}
	
	@FunctionalInterface
	public interface ISimpleFactory<TValue>{
		public TValue create();
	}
	
	private final IFactory<TKey, TValue> factory;
	private final HashMap<TKey, TValue> map = new HashMap<>();
	
	public DefaultValueHashMap(ISimpleFactory<TValue> factory) {
		this(key->factory.create());
	}
	
	public DefaultValueHashMap(IFactory<TKey, TValue> factory) {
		this.factory = factory;
	}
	
	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TValue get(Object key) {
		TValue value = map.get(key);
		if(value == null) {
			value = factory.create((TKey) key);
			map.put((TKey) key, value);
		}
		return value;
	}

	@Override
	public TValue put(TKey key, TValue value) {
		return map.put(key, value);
	}

	@Override
	public TValue remove(Object key) {
		return map.remove(key);
	}

	@Override
	public void putAll(Map<? extends TKey, ? extends TValue> m) {
		map.putAll(m);
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Set<TKey> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<TValue> values() {
		return map.values();
	}

	@Override
	public Set<Entry<TKey, TValue>> entrySet() {
		return map.entrySet();
	}

}
