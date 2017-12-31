package base.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import base.core.Core;

public final class ConstructorInfo<T>{

	final Constructor<T> constructor;
	
	private Type<?>[] cachedParameterTypes;
	
	ConstructorInfo(Constructor<T> constructor) {
		this.constructor = constructor;
		
		try {
			constructor.setAccessible(true);
		}
		catch (SecurityException e) {
		}
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

	public Type<?>[] getParameterTypes(){
		return getParameterTypesInner().clone();
	}

	public boolean isParameterTypesOf(Class<?>... parameterTypes) {
		Type<?>[] types = getParameterTypesInner();
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

	private Type<?>[] getParameterTypesInner(){
		if(cachedParameterTypes == null) {
			Class<?>[] parameters = constructor.getParameterTypes();
			cachedParameterTypes = new Type<?>[parameters.length];
			for(int i=0; i<parameters.length; i++) {
				cachedParameterTypes[i] = Types.typeOf(parameters[i]);
			}
		}
		return cachedParameterTypes;
	}

}
