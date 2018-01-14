package c41.utility.linq;

import java.util.List;

class ListEnumerable<T> extends IterableEnumerable<T>{
	
	private final List<T> list; 
	
	public ListEnumerable(List<T> list) {
		super(list);
		this.list = list;
	}
	
	@Override
	public int count() {
		return list.size();
	}
	
	@Override
	public T at(int i) {
		return list.get(i);
	}
	
}