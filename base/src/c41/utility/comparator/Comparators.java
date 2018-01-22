package c41.utility.comparator;

import java.util.Comparator;

public final class Comparators {

	private Comparators() {}
	
	public static int compare(int t1, int t2) {
		return t1 - t2;
	}
	
	public static <T extends Comparable<? super T>> int compare(T t1, T t2) {
		if(t1 == null && t2 == null) {
			return 0;
		}
		if(t1 == null) {
			return -t2.compareTo(t1);
		}
		return t1.compareTo(t2);
	}

	@SuppressWarnings("unchecked")
	public static <T> int compareNatural(T obj1, T obj2) {
		if(obj1 instanceof Comparable && obj2 instanceof Comparable) {
			return compare((Comparable)obj1, (Comparable)obj2);
		}
		if(obj1 == obj2) {
			return 0;
		}
		if(obj1 == null) {
			return 1;
		}
		if(obj2 == null) {
			return -1;
		}
		return compare(obj1.toString(), obj2.toString());
	}
	
	public static <T> ComparatorChain<T> by(Comparator<T> comparator){
		return new ComparatorChain<>(comparator);
	}
	
}
