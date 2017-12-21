package base.reflect;

import java.lang.reflect.Constructor;

import base.core.Core;

class ConstructorContainer<T> {

	private final ConstructorInfo<T>[] constructors;
	
	@SuppressWarnings("unchecked")
	public ConstructorContainer(Type<T> type) {
		Class<T> clazz =  type.asClass();
		Constructor[] constructors =  clazz.getDeclaredConstructors();
		this.constructors = new ConstructorInfo[constructors.length];
		for(int i=0; i<constructors.length; i++) {
			this.constructors[i] = Types.asConstructorInfo(constructors[i]);
		}
	}

	public ConstructorInfo<T> findConstructorByParameterCount(int parameterCount){
		for(ConstructorInfo<T> constructor : constructors) {
			if(constructor.getParameterCount() == 0) {
				return constructor;
			}
		}
		return null;
	}

	public T newInstance(){
		for(ConstructorInfo<T> constructor : constructors) {
			if(constructor.getParameterCount() == 0) {
				return constructor.newInstance();
			}
		}
		throw Core.throwException(new NoSuchMethodException());
	}

}
