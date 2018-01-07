package base;

import static org.junit.Assert.fail;

public class AssertEx {

	public static <T extends Throwable> void assertThrow(Class<T> cl, Runnable action) {
		try {
			action.run();
		}
		catch (Throwable e) {
			if(cl.isInstance(e)) {
				return;
			}
		}
		fail();
	}
	
}
