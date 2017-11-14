package base.utility.linq;

import java.util.Iterator;

import base.core.Reference;
import base.utility.function.IJoiner;

public class JoinEnumerable<T, U, V> implements IReferenceEnumerable<V>{

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

	private class Enumerator implements IEnumerator<V>{

		private final Iterator<T> data1 = source1.iterator();
		private Iterator<U> data2 = source2.iterator();
		private Reference<T> t;
		private V v;
		
		@Override
		public boolean hasNext() {
			if(data2.hasNext()) {
				return true;
			}
			if(data1.hasNext() == false) {
				return false;
			}
			data2 = source2.iterator();
			t = null;
			return data2.hasNext();
		}

		@Override
		public void moveNext() {
			if(t == null) {
				t = new Reference<>(data1.next());
			}
			if(data2.hasNext() == false) {
				t.value = data1.next();
				data2 = source2.iterator();
			}
			U u = data2.next();
			v = joiner.join(t.value, u);
		}

		@Override
		public V current() {
			return v;
		}
		
	}
	
}
