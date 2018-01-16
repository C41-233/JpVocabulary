package c41.utility.linq;

import c41.lambda.selector.ISelector;
import c41.utility.assertion.Arguments;
import c41.utility.collection.map.DefaultValueHashMap;

class GroupByReferenceEnumerable<K, V> implements IReferenceGroupEnumerable<K, V>{

	private final IReferenceEnumerable<IReferenceGroup<K, V>> enumerable;
	
	public GroupByReferenceEnumerable(IReferenceEnumerable<V> enumerable, ISelector<V, K> selector) {
		Arguments.isNotNull(enumerable);
		Arguments.isNotNull(selector);

		IEnumerator<V> enumerator = enumerable.iterator();
		DefaultValueHashMap<K, ReferenceGroup<K, V>> keyToGroups = new DefaultValueHashMap<>(key->new ReferenceGroup<>(key));
		while(enumerator.hasNext()) {
			V value = enumerator.next();
			K key = selector.select(value);
			ReferenceGroup<K, V> group = keyToGroups.get(key);
			group.add(value);
		}
		this.enumerable = Linq.from(keyToGroups.values()).cast();
	}
	
	@Override
	public IEnumerator<IReferenceGroup<K, V>> iterator() {
		return enumerable.iterator();
	}
	
}
