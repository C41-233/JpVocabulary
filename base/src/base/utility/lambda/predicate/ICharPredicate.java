package base.utility.lambda.predicate;

@FunctionalInterface
public interface ICharPredicate extends IPredicate<Character>{

	public boolean is(char ch);
	
	@Override
	public default boolean is(Character ch) {
		return is((char)ch);
	}
	
}
