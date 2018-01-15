package c41.lambda.predicate;

@FunctionalInterface
public interface IIntPredicate extends IPredicate<Integer>{

	public boolean is(int val);
	
	@Override
	public default boolean is(Integer val) {
		return is((int)val);
	}
}