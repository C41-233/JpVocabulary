package base.reflect;

import java.lang.reflect.Method;

public final class MethodInfo {

	private final Method method;
	
	MethodInfo(Method method) {
		this.method = method;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}
	
	@Override
	public int hashCode() {
		return method.hashCode();
	}
	
}
