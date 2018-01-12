package c41.utility.comparator;

import java.util.Comparator;

public class ComparatorChain<T> implements Comparator<T>{

	private final Comparator<T> comparator;
	
	public ComparatorChain(Comparator<T> comparator) {
		this.comparator = comparator;
	}
	
	@Override
	public int compare(T o1, T o2) {
		return comparator.compare(o1, o2);
	}

	public ComparatorChain<T> chain(Comparator<T> next){
		return new ComparatorChain<>((o1, o2)->{
			int comp = comparator.compare(o1, o2);
			if(comp != 0) {
				return comp;
			}
			return next.compare(o1, o2);
		});
	}
	
}
