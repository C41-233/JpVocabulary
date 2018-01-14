package c41.lambda.action;

@FunctionalInterface
public interface IForeachAction<T> extends IAction2<T, Integer>{

	public void invoke(T t, int i);
	
	@Override
	public default void invoke(T t, Integer i) {
		invoke(t, (int)i);
	}
	
}
