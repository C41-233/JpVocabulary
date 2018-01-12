package c41.utility.collection.list;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TypeArrayList<T> implements ITypeCollection<T>{

	private final Class<T> cl;
	private final ArrayList<T> list = new ArrayList<>();
	
	public TypeArrayList(Class<T> cl) {
		this.cl = cl;
	}
	
	@Override
	public void add(T obj) {
		list.add(obj);
	}
	
	@Override
	public void addAll(T[] array) {
		for(T obj : array) {
			list.add(obj);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked") 
	public T[] toArray() {
		T[] arr = (T[]) Array.newInstance(cl, list.size());
		return list.toArray(arr);
	}

}
