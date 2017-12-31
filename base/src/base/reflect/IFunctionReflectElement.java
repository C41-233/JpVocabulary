package base.reflect;

import java.lang.reflect.Type;

public interface IFunctionReflectElement {

	public int getParameterCount();
	public TypeInfo<?>[] getParameterTypes();
	public Type[] getParameterGenericTypes();
	public boolean isParameterTypesOf(Class<?>... parameterTypes);

	public TypeInfo<?>[] getExceptionTypes();
	public int getExceptionCount();
	public Type[] getExceptionGenericTypes();
	
}
