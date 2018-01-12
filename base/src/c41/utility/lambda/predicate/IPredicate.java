package c41.utility.lambda.predicate;

import c41.utility.lambda.function.IFunction1;

@FunctionalInterface
public interface IPredicate<T> extends IFunction1<Boolean, T> {

	public boolean is(T obj);
	
	@Override
	public default Boolean invoke(T obj) {
		return is(obj);
	}
	
}
