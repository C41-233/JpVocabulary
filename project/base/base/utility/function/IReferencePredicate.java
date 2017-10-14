package base.utility.function;

@FunctionalInterface
public interface IReferencePredicate<T> {

	public boolean is(T obj);
	
}
