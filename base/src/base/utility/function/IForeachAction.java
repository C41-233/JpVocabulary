package base.utility.function;

@FunctionalInterface
public interface IForeachAction<T> {

	public void action(T t, int i);
	
}
