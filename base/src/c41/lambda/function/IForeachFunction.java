package c41.lambda.function;

@FunctionalInterface
public interface IForeachFunction<T> extends IFunction2<Boolean, T, Integer>{

	@Override
	public default Boolean invoke(T obj, Integer i) {
		return apply(obj, i);
	}
	
	public boolean apply(T obj, int i);
	
}
