package base.utility.linq;

public final class Linq {

	public static ICharSequence of(String s) {
		return new StringCharSequence(s);
	}
	
}
