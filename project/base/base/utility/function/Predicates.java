package base.utility.function;

public final class Predicates {

	public static ICharPredicate and(ICharPredicate p1, ICharPredicate p2) {
		return new CharPredicateAnd2(p1, p2);
	}

	public static ICharPredicate or(ICharPredicate p1, ICharPredicate p2) {
		return new CharPredicateOr2(p1, p2);
	}
	
	private static class CharPredicateAnd2 implements ICharPredicate{
	
		private final ICharPredicate p1;
		private final ICharPredicate p2;
		
		public CharPredicateAnd2(ICharPredicate p1, ICharPredicate p2) {
			this.p1 = p1;
			this.p2 = p2;
		}
	
		@Override
		public boolean is(char ch) {
			return p1.is(ch) && p2.is(ch);
		}
	}

	private static class CharPredicateOr2 implements ICharPredicate{
	
		private final ICharPredicate p1;
		private final ICharPredicate p2;
		
		public CharPredicateOr2(ICharPredicate p1, ICharPredicate p2) {
			this.p1 = p1;
			this.p2 = p2;
		}
	
		@Override
		public boolean is(char ch) {
			return p1.is(ch) || p2.is(ch);
		}
	}
}
