package base.utility.lambda;

import base.utility.lambda.predicate.ICharPredicate;
import base.utility.lambda.predicate.IPredicate;

public final class Predicates {

	public static ICharPredicate and(ICharPredicate predicate1, ICharPredicate predicate2) {
		return new CharPredicateAnd2(predicate1, predicate2);
	}

	public static ICharPredicate and(ICharPredicate... predicates) {
		return new CharPredicateAndN(predicates);
	}
	
	public static ICharPredicate or(ICharPredicate predicate1, ICharPredicate predicate2) {
		return new CharPredicateOr2(predicate1, predicate2);
	}

	public static ICharPredicate or(ICharPredicate... predicates) {
		return new CharPredicateOrN(predicates);
	}
	
	private static class CharPredicateAnd2 implements ICharPredicate{
	
		private final ICharPredicate predicate1;
		private final ICharPredicate predicate2;
		
		public CharPredicateAnd2(ICharPredicate predicate1, ICharPredicate predicate2) {
			this.predicate1 = predicate1;
			this.predicate2 = predicate2;
		}
	
		@Override
		public boolean is(char ch) {
			return predicate1.is(ch) && predicate2.is(ch);
		}
	}

	private static class CharPredicateAndN implements ICharPredicate{
		
		private final ICharPredicate[] predicates;
		
		public CharPredicateAndN(ICharPredicate... predicates) {
			this.predicates = predicates;
		}

		@Override
		public boolean is(char ch) {
			for(ICharPredicate predicate : predicates) {
				if(predicate.is(ch) == false) {
					return false;
				}
			}
			return true;
		}
		
	}
	
	private static class CharPredicateOr2 implements ICharPredicate{
	
		private final ICharPredicate predicate1;
		private final ICharPredicate predicate2;
		
		public CharPredicateOr2(ICharPredicate predicate1, ICharPredicate predicate2) {
			this.predicate1 = predicate1;
			this.predicate2 = predicate2;
		}
	
		@Override
		public boolean is(char ch) {
			return predicate1.is(ch) || predicate2.is(ch);
		}
	}
	
	private static class CharPredicateOrN implements ICharPredicate{
		
		private final ICharPredicate[] predicates;
		
		public CharPredicateOrN(ICharPredicate... predicates) {
			this.predicates = predicates;
		}

		@Override
		public boolean is(char ch) {
			for (ICharPredicate predicate : predicates) {
				if(predicate.is(ch)) {
					return true;
				}
			}
			return false;
		}
		
	}
	
	public static <T> IPredicate<T> not(IPredicate<T> predicate){
		return s->predicate.is(s) == false;
	}

}
