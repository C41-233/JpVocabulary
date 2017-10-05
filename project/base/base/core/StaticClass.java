package base.core;

import base.utility.Strings;

public abstract class StaticClass {

	protected StaticClass() {
		throw new RuntimeException(Strings.format("You cannot new intance of class %s.", getClass()));
	}
	
}
