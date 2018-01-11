package base.utility.lambda.selector;

import base.utility.lambda.function.IFunction1;

@FunctionalInterface
public interface ISelector<T, V> extends IFunction1<V, T>{

	public V select(T obj);
	
	@Override
	public default V invoke(T obj) {
		return select(obj);
	}
	
}
