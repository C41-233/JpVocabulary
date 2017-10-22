package base.utility.linq;

import java.util.ArrayList;
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

		private final ArrayList<T> list = new ArrayList<>();
		private ISortedEnumerator<T> enumerator = enumerable.iterator();
		private int index = -1;

		public Enumerator() {
			if(enumerator.hasNext()) {
				list.add(enumerator.next());
				while(enumerator.hasNextEquals()) {
					list.add(enumerator.next());
				}
				list.sort(comparator);
			}
		}
		
		@Override
		public boolean hasNext() {
			return index+1 < list.size() || enumerator.hasNext();
		}

		@Override
		public void moveNext() {
			if(index >= 0) {
				list.set(index, null); //gc
			}
			index++;
			
			if(index >= list.size()) {
				list.clear();
				list.add(enumerator.next());
				while(enumerator.hasNextEquals()) {
					list.add(enumerator.next());
				}
				list.sort(comparator);
				index = 0;
			}
		}

		@Override
		public T current() {
			return list.get(index);
		}

		@Override
		public boolean hasNextEquals() {
			return index >=0 && index+1 < list.size() && comparator.compare(current(), list.get(index+1)) == 0;
		}
		
	}
	
}