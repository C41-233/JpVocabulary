package c41.lambda.predicate;

import c41.lambda.function.IFunction1;

@FunctionalInterface
public interface IPredicate<T> extends IFunction1<Boolean, T> {

	public boolean is(T obj);
	
	@Override
	public default Boolean invoke(T obj) {
		return is(obj);
	}
	
}
