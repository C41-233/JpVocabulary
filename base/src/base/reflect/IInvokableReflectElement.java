package base.reflect;

import java.lang.reflect.Type;

/**
 * 可以被调用的反射元素。（构造方法、成员方法）
 */
public interface IInvokableReflectElement {

	public int getParameterCount();
	public TypeInfo<?>[] getParameterTypes();
	public Type[] getParameterGenericTypes();
	public boolean isParameterTypesOf(Class<?>... parameterTypes);
	public ParameterInfo[] getParameters();

	public TypeInfo<?>[] getExceptionTypes();
	public int getExceptionCount();
	public Type[] getExceptionGenericTypes();
	
	public boolean hasVarArgs();
}
