package base.utility.lambda;

@FunctionalInterface
public interface IReferencePredicate<T> {

	public boolean is(T obj);
	
}
