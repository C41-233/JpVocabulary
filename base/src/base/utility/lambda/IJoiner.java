package base.utility.lambda;

@FunctionalInterface
public interface IJoiner<T, U, V> {

	public V join(T t, U u);
	
}
