package base.reflect;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.TypeVariable;

public interface IGenericReflectElement<T extends GenericDeclaration> {

	public TypeVariable<T>[] getGenericTypeParameters();
	
}
