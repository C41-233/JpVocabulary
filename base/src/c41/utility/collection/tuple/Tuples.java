package c41.utility.collection.tuple;

import c41.reflect.StaticClassException;

public final class Tuples {

	private Tuples() {
		throw new StaticClassException();
	}
	
	public static <T1, T2> Tuple2<T1, T2> make(T1 t1, T2 t2){
		return new Tuple2<>(t1, t2);
	}
	
}
