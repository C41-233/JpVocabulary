package c41.utility.collection.tuple;

public final class Tuple {

	private Tuple() {}
	
	public static <T1, T2> Tuple2<T1, T2> make(T1 t1, T2 t2){
		return new Tuple2<T1, T2>(t1, t2);
	}
	
}
