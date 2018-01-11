package base.utility.lambda.selector;

import base.utility.lambda.function.IFunction2;

@FunctionalInterface
public interface ISelectorEx<T, V> extends IFunction2<V, T, Integer>{

	public V select(T value, int i);
	
	@Override
	public default V invoke(T t1, Integer t2) {
		return select(t1, t2);
	}
	
}
