package c41.reflect;

public final class Reflects {

	private Reflects() {
		throw new StaticClassException();
	}
	
	public static StackTraceElement getCaller() {
		return Thread.currentThread().getStackTrace()[3];
	}
	
}
