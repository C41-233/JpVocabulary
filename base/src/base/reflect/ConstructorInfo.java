package base.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import base.core.Core;
import base.utility.linq.Linq;

public final class ConstructorInfo<T> 
	implements IAnnotatedReflectElement, IAccessableReflectElement, IInvokableReflectElement{

	final Constructor<T> constructor;
	
	ConstructorInfo(Constructor<T> constructor) {
		this.constructor = constructor;
		
		try {
			constructor.setAccessible(true);
		}
		catch (SecurityException e) {
		}
	}

	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}
	
	@Override
	public String toString() {
		return constructor.toString();
	}
	
	@Override
	public int hashCode() {
		return constructor.hashCode();
	}
	
	public TypeInfo<?> getDeclaringType(){
		return Types.typeOf(constructor.getDeclaringClass());
	}
	
	@Override
	public int getModifiers() {
		return constructor.getModifiers();
	}

	@Override
	public boolean isPublic() {
		return Modifiers.isPublic(constructor);
	}

	@Override
	public boolean isProtected() {
		return Modifiers.isProtected(constructor);
	}

	@Override
	public boolean isInternal() {
		return Modifiers.isInternal(constructor);
	}

	@Override
	public boolean isPrivate() {
		return Modifiers.isPrivate(constructor);
	}
	
	public boolean isSynthetic() {
		return constructor.isSynthetic();
	}
	
	@Override
	public boolean hasVarArgs() {
		return constructor.isVarArgs();
	}
	
	public T newInstance(Object...args) {
		try {
			return constructor.newInstance(args);
		} catch (InvocationTargetException e) {
			throw Core.throwException(e.getTargetException());
		} catch (ReflectiveOperationException e) {
			throw Core.throwException(e);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Parameter
	////////////////////////////////////////////////////////////////////////////////////////////////////////

	private TypeInfo<?>[] cachedParameterTypes;
	private ParameterInfo[] cachedParameters;
	
	@Override
	public int getParameterCount() {
		return constructor.getParameterCount();
	}
	
	@Override
	public TypeInfo<?>[] getParameterTypes(){
		return getCachedParameterTypesInner().clone();
	}

	@Override
	public Type[] getParameterGenericTypes(){
		return constructor.getGenericParameterTypes();
	}
	
	@Override
	public boolean isParameterTypesOf(Class<?>... parameterTypes) {
		TypeInfo<?>[] types = getCachedParameterTypesInner();
		if(types.length != parameterTypes.length) {
			return false;
		}
		for(int i=0; i<types.length; i++) {
			if(types[i].clazz != parameterTypes[i]) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public ParameterInfo[] getParameters() {
		return getCachedParameterInfosInner().clone();
	}
	
	private TypeInfo<?>[] getCachedParameterTypesInner(){
		if(cachedParameterTypes == null) {
			cachedParameterTypes = Linq.from(constructor.getParameterTypes())
					.select(p->Types.typeOf(p))
					.<TypeInfo>cast()
					.toArray(TypeInfo.class);
		}
		return cachedParameterTypes;
	}

	private ParameterInfo[] getCachedParameterInfosInner() {
		if(cachedParameters == null)
		{
			cachedParameters = Linq.from(constructor.getParameters())
					.select(p->ReflectCache.wrap(p))
					.toArray(ParameterInfo.class);
		}
		return cachedParameters;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Annotation
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public <TAnnotation extends Annotation> TAnnotation getAnnotation(Class<TAnnotation> cl) {
		return constructor.getAnnotation(cl);
	}

	@Override
	public Annotation[] getAnnotations() {
		return constructor.getAnnotations();
	}

	@Override
	public <TAnnotation extends Annotation> TAnnotation[] getAnnotations(Class<TAnnotation> cl) {
		return constructor.getAnnotationsByType(cl);
	}

	@Override
	public <TAnnotation extends Annotation> boolean hasAnnotation(Class<TAnnotation> cl) {
		return constructor.isAnnotationPresent(cl);
	}


	////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Exception
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private TypeInfo<?>[] cachedExceptions;

	@Override
	public TypeInfo<?>[] getExceptionTypes(){
		return getCachedExceptionsInner().clone();
	}
	
	@Override
	public int getExceptionCount() {
		return getCachedExceptionsInner().length;
	}
	
	@Override
	public Type[] getExceptionGenericTypes() {
		return constructor.getGenericExceptionTypes();
	}
	
	private TypeInfo<?>[] getCachedExceptionsInner(){
		if(cachedExceptions == null) {
			cachedExceptions = Linq.from(constructor.getExceptionTypes())
					.select(e->Types.typeOf(e))
					.<TypeInfo>cast()
					.toArray(TypeInfo.class);
		}
		return cachedExceptions;
	}

}
