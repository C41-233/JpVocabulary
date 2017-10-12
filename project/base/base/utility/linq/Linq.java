package base.utility.linq;

public final class Linq {

	public static ICharQuery from(String s) {
		return new StringCharQuery(s);
	}
	
}
