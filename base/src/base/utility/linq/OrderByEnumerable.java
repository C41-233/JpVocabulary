package base.utility.linq;

import java.util.Comparator;
import java.util.PriorityQueue;

import base.core.Reference;

class OrderByEnumerable<T> implements IReferenceSortedEnumerable<T>{

	private final IReferenceEnumerable<T> enumerable;
	private final Comparator<? super T> comparator;
	
	public OrderByEnumerable(IReferenceEnumerable<T> enumerable, Comparator<? super T> comparator) {
		this.enumerable = enumerable;
		this.comparator = comparator;
	}
	
	@Override
	public ISortedEnumerator<T> iterator() {
		return new Enumerator();
	}

	private class Enumerator implements ISortedEnumerator<T>{

		private final PriorityQueue<T> queue = new PriorityQueue<>(comparator);
		
		private Reference<T> current;
	
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
		public boolean hasNextEquals() {
			return current!=null && hasNext() && comparator.compare(current.value, queue.peek()) == 0;
		}
		
		@Override
		public void moveNext() {
			if(this.current == null) {
				this.current = new Reference<T>();
			}
			this.current.value = queue.poll();
		}

		@Override
		public T current() {
			return current.value;
		}

	}
	
}
