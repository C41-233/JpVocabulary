package logic;

import base.core.StaticClass;
import base.utility.Strings;

public abstract class LogicBase extends StaticClass{

	protected static void raise(String format, Object...args) {
		throw new LogicException(Strings.format(format, args));
	}
	
}
