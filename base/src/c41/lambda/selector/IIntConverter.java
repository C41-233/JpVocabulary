package c41.lambda.selector;

@FunctionalInterface
public interface IIntConverter<T> extends ISelector<T, Integer>{

	public int convert(T value);
	
	@Override
	public default Integer select(T value) {
		return convert(value);
	}
	
}
