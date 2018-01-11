package base.utility.assertion;

import base.utility.Strings;

public final class Assert{

	private Assert() {}
	
	public static void is(boolean value, String format, Object...args) {
		if(value == false) {
			throw new AssertionFailException(Strings.format(format, args));
		}
	}
	
	public static void isNotNull(Object obj) {
		is(obj!=null, "is null");
	}
	
	public static void require(String value) {
		isNotNull(value);
		is(value.trim().isEmpty()==false, "require string");
	}
	
	public static void require(Object obj) {
		isNotNull(obj);
	}
	
	public static void require(String[] values) {
		isNotNull(values);
		for(String value : values) {
			require(value);
		}
	}
	
	public static void require(Iterable<String> values) {
		isNotNull(values);
		for(String value : values) {
			require(value);
		}
	}
	
}
