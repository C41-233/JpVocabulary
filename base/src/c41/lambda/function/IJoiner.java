package c41.lambda.function;

@FunctionalInterface
public interface IJoiner<T1, T2, V> extends IFunction2<V, T1, T2> {

	public V join(T1 t1, T2 t2);
	
	@Override
	public default V invoke(T1 t1, T2 t2) {
		return join(t1, t2);
	}
	
}
