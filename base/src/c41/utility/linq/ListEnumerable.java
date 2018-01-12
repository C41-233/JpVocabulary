package c41.utility.linq;

import java.util.Iterator;
import java.util.List;

class ListEnumerable<T> implements IReferenceEnumerable<T>{
	
	private final List<T> list; 
	
	public ListEnumerable(List<T> list) {
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
	
	@Override
	public IEnumerator<T> iterator() {
		return new Enumerator();
	}

	private class Enumerator implements IEnumerator<T>{

		private final Iterator<T> iterator = list.iterator();
		
		private T current;
		
		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public void moveNext() {
			current = iterator.next();
		}

		@Override
		public T current() {
			return current;
		}
		
	}
	
}