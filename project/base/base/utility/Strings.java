package base.utility;

public final class Strings{

	private Strings() {}
	
	public static String format(String format, Object... args) {
		if(args.length == 0) {
			return format;
		}
		return String.format(format, args);
	}

	public static String[] splitTokens(String string) {
		return string.split("(\\s|　)+");
	}
	
}
