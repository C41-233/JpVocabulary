package base.utility.linq;

import java.util.Comparator;
import java.util.PriorityQueue;

class ThenByEnumerable<T> implements IReferenceSortedEnumerable<T>{

	private final IReferenceSortedEnumerable<T> enumerable;
	private final Comparator<T> comparator;
	
	public ThenByEnumerable(IReferenceSortedEnumerable<T> enumerable, Comparator<T> comparator) {
		this.enumerable = enumerable;
		this.comparator = comparator;
	}
	
	@Override
	public ISortedEnumerator<T> iterator() {
		return new Enumerator();
	}
	
	private class Enumerator implements ISortedEnumerator<T>{

		private final PriorityQueue<T> list = new PriorityQueue<>(comparator);
		
		private ISortedEnumerator<T> enumerator = enumerable.iterator();
		
		private T current;

		@Override
		public boolean hasNext() {
			return list.isEmpty() == false || enumerator.hasNext();
		}

		@Override
		public void moveNext() {
			if(list.isEmpty()) {
				list.add(enumerator.next());
				while(enumerator.hasNextEquals()) {
					list.add(enumerator.next());
				}
			}
			this.current = list.poll();
		}

		@Override
		public T current() {
			return current;
		}

		@Override
		public boolean hasNextEquals() {
			return list.isEmpty() == false && comparator.compare(current, list.peek()) == 0;
		}
		
	}
	
}