package base.utility.lambda;

@FunctionalInterface
public interface IForeachAction<T> {

	public void action(T t, int i);
	
}
