package base.utility.function;

@FunctionalInterface
public interface ISelectorEx<T, V> {

	public V select(T value, int i);
	
}
