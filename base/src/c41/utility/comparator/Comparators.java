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

	public static <T> ComparatorChain<T> chain(Comparator<T> comparator){
		return new ComparatorChain<>(comparator);
	}
	
}
