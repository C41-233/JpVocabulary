package base.utility.function;

@FunctionalInterface
public interface ICharSelector<V> {

	public V select(char ch);
	
}
