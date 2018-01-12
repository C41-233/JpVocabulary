package c41.reflect;

import java.lang.reflect.Constructor;

import c41.core.Core;
import c41.utility.Strings;

final class ConstructorContainer<T> {

	private final ConstructorInfo<T>[] constructors;
	
	@SuppressWarnings("unchecked")
	public ConstructorContainer(TypeInfo<T> type) {
		Class<T> clazz =  type.clazz;
		Constructor[] constructors =  clazz.getDeclaredConstructors();
		this.constructors = new ConstructorInfo[constructors.length];
		for(int i=0; i<constructors.length; i++) {
			this.constructors[i] = ReflectCache.wrap(constructors[i]);
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
		ConstructorInfo<T> anotherBest = null; 
		int min = Integer.MAX_VALUE;
		
		for(ConstructorInfo<T> constructor : constructors) {
			int distance = ReflectDistance.getDistance(constructor.constructor.getParameterTypes(), args);
			if(distance < 0) {
				continue;
			}
			if(distance < min) {
				best = constructor;
				anotherBest = null;
				min = distance;
			}
			else if(distance == min) {
				anotherBest = constructor;
			}
		}
		if(best != null) {
			if(anotherBest != null) {
				throw new AmbigousMethodException(Strings.format("Ambigous call between %s and %s", best, anotherBest));
			}
			return best.newInstance(args);
		}
		throw Core.throwException(new NoSuchMethodException());
	}

}
