package base.utility;

import base.core.StaticClass;

public final class Strings extends StaticClass{

	public static String format(String format, Object... args) {
		if(args.length == 0) {
			return format;
		}
		return String.format(format, args);
	}
	
}
