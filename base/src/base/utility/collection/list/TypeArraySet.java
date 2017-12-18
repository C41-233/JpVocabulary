package base.utility.collection.list;

import java.util.HashSet;

public class TypeArraySet<T> implements ITypeCollection<T>{

	private final TypeArrayList<T> list;
	private final HashSet<T> set = new HashSet<>();
	
	public TypeArraySet(Class<T> clazz) {
		this.list = new TypeArrayList<>(clazz);
	}
			
	@Override
	public void add(T obj) {
		if(set.add(obj)) {
			list.add(obj);
		}
	}

	@Override
	public void addAll(T[] array) {
		for(T obj : array) {
			add(obj);
		}
	}

	@Override
	public T[] toArray() {
		return list.toArray();
	}

}
