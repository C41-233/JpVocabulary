package base.utility.linq;

public final class Linq {

	public static ICharEnumerable from(String string) {
		return new CharArrayEnumerable(string);
	}
	
	public static ICharEnumerable from(char[] array) {
		return new CharArrayEnumerable(array);
	}
	
	public static <T> IReferenceEnumerable<T> from(Iterable<T> iterable){
		return new IterableEnumerable<T>(iterable);
	}
	
	public static <T> IReferenceEnumerable<T> from(T[] array){
		return new ArrayEnumerable<T>(array);
	}
	
}
