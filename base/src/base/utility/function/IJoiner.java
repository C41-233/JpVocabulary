package base.utility.function;

@FunctionalInterface
public interface IJoiner<T, U, V> {

	public V join(T t, U u);
	
}
