package base.reflect;

import java.lang.reflect.Method;
import java.util.Objects;

import base.utility.Arrays;

public final class MethodInfo 
	implements IAccessableReflectElement{

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

	@Override
	public String toString() {
		return method.toString();
	}
	
	public String getName() {
		return method.getName();
	}

	/**
	 * 当前方法是否覆盖了另一个方法
	 */
	public boolean isOverrideOf(MethodInfo other) {
		//静态方法不能覆盖
		if(this.isStatic() || other.isStatic()) {
			return false;
		}
		
		//覆盖方法名称必须一致
		String thisName = this.method.getName();
		String otherName = other.method.getName();
		if(!Objects.equals(thisName, otherName)) {
			return false;
		}
		
		Class<?> thisClass = this.method.getDeclaringClass();
		Class<?> otherClass = other.method.getDeclaringClass();
		//同一个类型不能覆盖
		if(thisClass == otherClass) {
			return false;
		}
		//被覆盖的方法必须在父类或接口中
		if(!otherClass.isAssignableFrom(thisClass)) {
			return false;
		}
		
		Class<?> thisReturnType = this.method.getReturnType();
		Class<?> otherReturnType = other.method.getReturnType();
		//返回类型必须相同或子类
		if(!otherReturnType.isAssignableFrom(thisReturnType)) {
			return false;
		}
		
		//参数列表必须一致
		if(!Arrays.referenceEquals(this.method.getParameterTypes(), other.method.getParameterTypes())) {
			return false;
		}
		
		return true;
	}
	
	public boolean isStatic() {
		return Modifiers.isStatic(method);
	}

	@Override
	public int getModifiers() {
		return method.getModifiers();
	}

	@Override
	public boolean isPublic() {
		return Modifiers.isPublic(method);
	}

	@Override
	public boolean isProtected() {
		return Modifiers.isProtected(method);
	}

	@Override
	public boolean isInternal() {
		return Modifiers.isInternal(method);
	}

	@Override
	public boolean isPrivate() {
		return Modifiers.isPrivate(method);
	}

	public boolean isSynthetic() {
		return method.isSynthetic();
	}
	
	public TypeInfo<?> getDeclaringType() {
		return Types.typeOf(method.getDeclaringClass());
	}

	public TypeInfo getReturnType() {
		return Types.typeOf(method.getReturnType());
	}
	
}
