package base.reflect;

import base.utility.linq.Linq;

final class MethodContainer {

	private final TypeInfo type;
	private MethodInfo[] cachedDeclaredMethods;
	
	public MethodContainer(TypeInfo type) {
		this.type = type;
	}
	
	public MethodInfo[] getDeclaredMethods() {
		return getCachedDeclaredMethodsInner().clone();
	}
	
	private MethodInfo[] getCachedDeclaredMethodsInner() {
		if(cachedDeclaredMethods == null) {
			cachedDeclaredMethods = Linq.from(type.clazz.getDeclaredMethods())
					.select(m->ReflectHelper.wrap(m))
					.toArray(MethodInfo.class);
		}
		return cachedDeclaredMethods;
	}
	
}
