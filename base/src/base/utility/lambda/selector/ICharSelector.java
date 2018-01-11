package base.utility.lambda.selector;

@FunctionalInterface
public interface ICharSelector<V> extends ISelector<Character, V>{

	public V select(char ch);
	
	@Override
	public default V select(Character ch) {
		return select((char)ch);
	}
	
}
