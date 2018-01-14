package c41.lambda.function;

@FunctionalInterface
public interface IFunction1<R, T1>{

	public R invoke(T1 t1);
	
}
