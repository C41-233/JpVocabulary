package base.utility.function;

@FunctionalInterface
public interface ISelector<T, V> {

	public V select(T obj);
	
}
