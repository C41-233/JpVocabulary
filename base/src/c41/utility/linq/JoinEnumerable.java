package c41.utility.linq;

import java.util.Iterator;

import c41.lambda.function.IJoiner;

class JoinEnumerable<T, U, V> implements IReferenceEnumerable<V>{

	private final Iterable<T> source1;
	private final Iterable<U> source2;
	private final IJoiner<T, U, V> joiner;
	
	public JoinEnumerable(Iterable<T> source1, Iterable<U> source2, IJoiner<T, U, V> joiner) {
		this.source1 = source1;
		this.source2 = source2;
		this.joiner = joiner;
	}
	
	@Override
	public IEnumerator<V> iterator() {
		return new Enumerator();
	}

	private class Enumerator extends EnumeratorBase<V>{

		private final Iterator<T> data1 = source1.iterator();
		private Iterator<U> data2 = source2.iterator();
		
		private T t;
		private V v;
		
		private boolean hasT;
		
		@Override
		public boolean hasNext() {
			if(hasT == false && data1.hasNext() == false) {
				return false;
			}
			
			if(data2.hasNext()) {
				return true;
				
			}
			if(data1.hasNext() == false) {
				return false;
			}
			
			data2 = source2.iterator();
			t = null;
			hasT = false;
			return data2.hasNext();
		}

		@Override
		public void doMoveNext() {
			if(hasT == false) {
				t = data1.next();
				hasT = true;
			}
			if(data2.hasNext() == false) {
				t = data1.next();
				data2 = source2.iterator();
			}
			U u = data2.next();
			v = joiner.join(t, u);
		}

		@Override
		public V doCurrent() {
			return v;
		}
		
	}
	
}
