package c41.lambda.predicate;

public interface IBytePredicate extends IPredicate<Byte>{

	public boolean is(byte ch);
	
	@Override
	public default boolean is(Byte ch) {
		return is((byte)ch);
	}
	
}
