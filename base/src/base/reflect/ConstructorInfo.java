package base.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import base.core.Core;

public final class ConstructorInfo<T> implements IAnnotatedReflectElement{

	final Constructor<T> constructor;
	
	private ClassType<?>[] cachedParameterTypes;
	
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
	
	public ClassType<?> getDeclaringType(){
		return Types.typeOf(constructor.getDeclaringClass());
	}
	
	public int getModifiers() {
		return constructor.getModifiers();
	}
	
	public int getParameterCount() {
		return constructor.getParameterCount();
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

	public ClassType<?>[] getParameterTypes(){
		return getParameterTypesInner().clone();
	}

	public boolean isParameterTypesOf(Class<?>... parameterTypes) {
		ClassType<?>[] types = getParameterTypesInner();
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

	private ClassType<?>[] getParameterTypesInner(){
		if(cachedParameterTypes == null) {
			Class<?>[] parameters = constructor.getParameterTypes();
			cachedParameterTypes = new ClassType<?>[parameters.length];
			for(int i=0; i<parameters.length; i++) {
				cachedParameterTypes[i] = Types.typeOf(parameters[i]);
			}
		}
		return cachedParameterTypes;
	}

	@Override
	public <TAnnotation extends Annotation> TAnnotation getAnnotation(Class<TAnnotation> cl) {
		return constructor.getDeclaredAnnotation(cl);
	}

	@Override
	public Annotation[] getAnnotations() {
		return constructor.getDeclaredAnnotations();
	}

	@Override
	public <TAnnotation extends Annotation> TAnnotation[] getAnnotations(Class<TAnnotation> cl) {
		return constructor.getDeclaredAnnotationsByType(cl);
	}

	@Override
	public <TAnnotation extends Annotation> boolean hasAnnotation(Class<TAnnotation> cl) {
		return constructor.getDeclaredAnnotation(cl) != null;
	}
	
	private ClassType<?>[] cachedExceptions;

	public ClassType<?>[] getExceptionTypes(){
		return getCachedExceptionsInner().clone();
	}
	
	public int getExceptionCount() {
		return getCachedExceptionsInner().length;
	}
	
	public Type[] getExceptionGenericTypes() {
		return constructor.getGenericExceptionTypes();
	}
	
	private ClassType<?>[] getCachedExceptionsInner(){
		if(cachedExceptions == null) {
			Class<?>[] exceptions = constructor.getExceptionTypes();
			this.cachedExceptions = new ClassType[exceptions.length];
			for(int i = 0; i<exceptions.length; i++) {
				this.cachedExceptions[i] = Types.typeOf(exceptions[i]);
			}
		}
		return cachedExceptions;
	}
	
}
