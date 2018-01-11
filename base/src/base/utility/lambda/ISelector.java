package base.utility.lambda;

@FunctionalInterface
public interface ISelector<T, V> {

	public V select(T arg1);
	
}
