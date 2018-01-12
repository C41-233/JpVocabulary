package c41.utility.lambda.function;

@FunctionalInterface
public interface IFunction1<R, T1>{

	public R invoke(T1 t1);
	
}
