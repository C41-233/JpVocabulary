package base.utility.linq;

import base.core.ArgumentNullException;

public final class Linq {

	public static ICharQuery from(String string) {
		if(string == null) {
			throw new ArgumentNullException();
		}
		return new StringCharQuery(string);
	}
	
	public static <T> IQuery<T> from(Iterable<T> iterable){
		if(iterable == null) {
			throw new ArgumentNullException();
		}
		return new IterableQuery<>(iterable);
	}
	
}
