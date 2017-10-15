package base.utility.assertion;

import base.utility.Strings;

public final class Assert{

	private Assert() {}
	
	public static void is(boolean value, String format, Object...args) {
		if(value == false) {
			throw new AssertionFailException(Strings.format(format, args));
		}
	}
	
	public static void notNull(Object obj) {
		is(obj!=null, "is null");
	}
	
	public static void require(String value) {
		notNull(value);
		is(value.trim().isEmpty()==false, "require string");
	}
	
	public static void require(Object obj) {
		notNull(obj);
	}
	
}