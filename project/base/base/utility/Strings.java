package base.utility;

public final class Strings{

	private Strings() {}
	
	public static String format(String format, Object... args) {
		if(args.length == 0) {
			return format;
		}
		return String.format(format, args);
	}
	
}
