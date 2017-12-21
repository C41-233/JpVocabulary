package base.reflect;

import java.lang.reflect.Constructor;

import base.core.Core;

final class ConstructorContainer<T> {

	private final ConstructorInfo<T>[] constructors;
	
	@SuppressWarnings("unchecked")
	public ConstructorContainer(Type<T> type) {
		Class<T> clazz =  type.clazz;
		Constructor[] constructors =  clazz.getDeclaredConstructors();
		this.constructors = new ConstructorInfo[constructors.length];
		for(int i=0; i<constructors.length; i++) {
			this.constructors[i] = Types.asConstructorInfo(constructors[i]);
		}
	}

	public T newInstance(){
		for(ConstructorInfo<T> constructor : constructors) {
			if(constructor.getParameterCount() == 0) {
				return constructor.newInstance();
			}
		}
		throw Core.throwException(new NoSuchMethodException());
	}

	public ConstructorInfo<T>[] getConstructors() {
		return constructors.clone();
	}

	public ConstructorInfo<T> getConstructor(Class<?>[] parameterTypes) {
		for(ConstructorInfo<T> constructor : constructors) {
			if(constructor.isParameterTypesOf(parameterTypes)) {
				return constructor;
			}
		}
		return null;
	}

}
