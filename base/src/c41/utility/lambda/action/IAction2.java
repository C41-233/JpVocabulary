package c41.utility.lambda.action;

@FunctionalInterface
public interface IAction2<T1, T2> {

	public void invoke(T1 t1, T2 t2);
	
}
