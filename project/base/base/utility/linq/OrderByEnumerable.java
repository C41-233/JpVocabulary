package base.utility.linq;

import java.util.Comparator;
import java.util.PriorityQueue;

class OrderByEnumerable<T> implements IReferenceEnumerable<T>{

	private final IReferenceEnumerable<T> enumerable;
	private final Comparator<T> comparator;
	
	public OrderByEnumerable(IReferenceEnumerable<T> enumerable, Comparator<T> comparator) {
		this.enumerable = enumerable;
		this.comparator = comparator;
	}
	
	@Override
	public IEnumerator<T> iterator() {
		return new Enumerator();
	}

	private class Enumerator implements IEnumerator<T>{

		private final PriorityQueue<T> queue = new PriorityQueue<>(comparator);
		
		private T current;
	
		public Enumerator() {
			IEnumerator<T> enumerator = enumerable.iterator();
			while(enumerator.hasNext()) {
				queue.add(enumerator.next());
			}
		}
		
		@Override
		public boolean hasNext() {
			return queue.isEmpty() == false;
		}

		@Override
		public void moveNext() {
			this.current = queue.poll();
		}

		@Override
		public T current() {
			return current;
		}
		
	}
	
}
