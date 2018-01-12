package c41.utility.lambda.function;

@FunctionalInterface
public interface IFunction2<R, T1, T2> {

	public R invoke(T1 t1, T2 t2);
	
}
