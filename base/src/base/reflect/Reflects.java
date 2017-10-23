package base.reflect;

public final class Reflects {

	private Reflects() {}
	
	public static StackTraceElement getCaller() {
		return Thread.currentThread().getStackTrace()[3];
	}
	
}
