package base.utility.linq;

import java.util.ArrayList;
import java.util.Comparator;

class OrderByEnumerable<T> implements IReferenceSortedEnumerable<T>{

	private final IEnumerable<T> enumerable;
	private final Comparator<? super T> comparator;
	
	public OrderByEnumerable(IEnumerable<T> enumerable, Comparator<? super T> comparator) {
		this.enumerable = enumerable;
		this.comparator = comparator;
	}
	
	@Override
	public ISortedEnumerator<T> iterator() {
		return new Enumerator();
	}

	private class Enumerator implements ISortedEnumerator<T>{

		private final ArrayList<T> queue = new ArrayList<>();
		private int index = -1;
	
		public Enumerator() {
			IEnumerator<T> enumerator = enumerable.iterator();
			while(enumerator.hasNext()) {
				queue.add(enumerator.next());
			}
			queue.sort(comparator);
		}
		
		@Override
		public boolean hasNext() {
			return index+1 < queue.size();
		}

		@Override
		public boolean hasNextEquals() {
			return hasNext() && comparator.compare(current(), queue.get(index+1)) == 0;
		}
		
		@Override
		public void moveNext() {
			++index;
		}

		@Override
		public T current() {
			return queue.get(index);
		}

	}
	
}
