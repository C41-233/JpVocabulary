package c41.utility.linq;

import java.util.ArrayList;

class ReferenceGroup<TKey, TValue> implements IReferenceGroup<TKey, TValue>{

	public final TKey key;
	private final IReferenceEnumerable<TValue> enumerable;
	public final ArrayList<TValue> values;
	
	public ReferenceGroup(TKey key) {
		this.key = key;
		this.values = new ArrayList<>();
		this.enumerable = new IterableEnumerable<TValue>(this.values);
	}
	
	@Override
	public IEnumerator<TValue> iterator() {
		return enumerable.iterator();
	}

	@Override
	public TKey getKey() {
		return key;
	}
	
}