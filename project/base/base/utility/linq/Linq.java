package base.utility.linq;

import java.util.Objects;

public final class Linq {

	public static ICharQuery from(String string) {
		Objects.requireNonNull(string);
		return new StringCharQuery(string);
	}
	
	public static <T> IQuery<T> from(Iterable<T> iterable){
		Objects.requireNonNull(iterable);
		return new IterableQuery<>(iterable);
	}
	
}
