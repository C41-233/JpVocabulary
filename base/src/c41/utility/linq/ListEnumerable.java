package c41.utility.linq;

import java.util.List;
import java.util.NoSuchElementException;

import c41.utility.assertion.Arguments;

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
		Arguments.is(i>=0, "%d < 0", i);
		
		if(i >= list.size()) {
			throw new NoSuchElementException();
		}
		return list.get(i);
	}
	
}