package base.utility.linq;

import java.util.Iterator;

class IterableQuery<T> implements IQuery<T>{

	private final Iterable<T> iterable;
	
	public IterableQuery(Iterable<T> iterable) {
		this.iterable = iterable;
	}

	@Override
	public Iterator<T> iterator() {
		return this.iterable.iterator();
	}
	
}
