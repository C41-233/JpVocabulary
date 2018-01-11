package base.utility.lambda;

@FunctionalInterface
public interface ISelectorEx<T, V> {

	public V select(T value, int i);
	
}
