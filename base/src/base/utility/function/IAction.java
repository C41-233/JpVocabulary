package base.utility.function;

@FunctionalInterface
public interface IAction<T> {

	public void call(T t);
	
}
