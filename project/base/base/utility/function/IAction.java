package base.utility.function;

@FunctionalInterface
public interface IAction<T> {

	public void action(T t);
	
}
