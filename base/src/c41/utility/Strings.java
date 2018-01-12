package c41.utility;

public final class Strings{

	private Strings() {}
	
	public static String format(String format, Object... args) {
		if(args.length == 0) {
			return format;
		}
		return String.format(format, args);
	}

	public static String[] splitByWhitespace(String string) {
		return string.split("(\\s|　)+");
	}
	
	public static String of(Object...objects) {
		StringBuilder sb = new StringBuilder();
		for(Object object : objects) {
			sb.append(object);
		}
		return sb.toString();
	}
	
}
