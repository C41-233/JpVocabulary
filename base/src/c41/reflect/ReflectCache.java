package c41.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import c41.utility.cache.WeakMemoryCache;

final class ReflectCache {

	private static final WeakMemoryCache<Field, FieldInfo> fields = new WeakMemoryCache<>();
	
	public static FieldInfo wrap(Field field) {
		if(field == null) {
			return null;
		}
		return fields.getOrCreate(field, ()->new FieldInfo(field));
	}
	
	private static final WeakMemoryCache<Constructor, ConstructorInfo> constructors = new WeakMemoryCache<>();
	
	@SuppressWarnings("unchecked")
	public static <T> ConstructorInfo<T> wrap(Constructor<T> constructor){
		if(constructor == null) {
			return null;
		}
		return constructors.getOrCreate(constructor, ()->new ConstructorInfo<>(constructor));
	}

	private static final WeakMemoryCache<Parameter, ParameterInfo> parameters = new WeakMemoryCache<>();
	
	public static ParameterInfo wrap(Parameter parameter) {
		if(parameter == null) {
			return null;
		}
		return parameters.getOrCreate(parameter, ()->new ParameterInfo(parameter));
	}
	
	private static final WeakMemoryCache<Method, MethodInfo> methods = new WeakMemoryCache<>();
	
	public static MethodInfo wrap(Method method) {
		if(method == null) {
			return null;
		}
		return methods.getOrCreate(method, ()->new MethodInfo(method));	
	}
}
