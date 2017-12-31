package base.reflect;

import java.lang.reflect.Constructor;

import base.core.Core;

final class ConstructorContainer<T> {

	private final ConstructorInfo<T>[] constructors;
	
	@SuppressWarnings("unchecked")
	public ConstructorContainer(ClassType<T> type) {
		Class<T> clazz =  type.clazz;
		Constructor[] constructors =  clazz.getDeclaredConstructors();
		this.constructors = new ConstructorInfo[constructors.length];
		for(int i=0; i<constructors.length; i++) {
			this.constructors[i] = Types.asConstructorInfo(constructors[i]);
		}
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

	public T newInstance(Object...args){
		ConstructorInfo<T> best = null;
		int min = Integer.MAX_VALUE;
		
		for(ConstructorInfo<T> constructor : constructors) {
			int distance = ReflectHelper.getDistance(constructor.constructor.getParameterTypes(), args);
			if(distance == 0) {
				best = constructor;
				break;
			}
			if(distance < 0) {
				continue;
			}
			if(distance < min) {
				best = constructor;
				min = distance;
			}
		}
		if(best != null) {
			return best.newInstance(args);
		}
		throw Core.throwException(new NoSuchMethodException());
	}

}
