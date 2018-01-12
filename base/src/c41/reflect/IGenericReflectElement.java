package c41.reflect;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.TypeVariable;

/**
 * 存在泛型参数的反射元素。
 * @param <T> 可泛型化的参数
 */
public interface IGenericReflectElement<T extends GenericDeclaration> {

	public TypeVariable<T>[] getGenericTypeParameters();
	
}
