package base.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import base.core.Core;

public class ConstructorInfo<T>{

	private final Constructor<T> constructor;
	
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

	
}
