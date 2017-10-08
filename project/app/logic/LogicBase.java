package logic;

import base.utility.Strings;

public abstract class LogicBase{

	protected static void raise(String format, Object...args) {
		throw new LogicException(Strings.format(format, args));
	}
	
}
