package base.utility.function;

public final class Comparators {

	private Comparators() {}
	
	public static <T extends Comparable<? super T>> int compare(T t1, T t2) {
		if(t1 == null && t2 == null) {
			return 0;
		}
		if(t1 == null) {
			return -t2.compareTo(t1);
		}
		return t1.compareTo(t2);
	}
	
}
