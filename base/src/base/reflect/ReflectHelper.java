package base.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import base.utility.cache.WeakMemoryCache;

final class ReflectHelper {

	public static int getDistance(Class<?>[] types, Object[] args) {
		if(types.length != args.length) {
			return -1;
		}
		
		int distance = 0;
		int length = types.length;
		for(int i=0; i<length; i++) {
			int dis = getDistance(types[i], args[i].getClass());
			if(dis < 0) {
				return -1;
			}
			distance += dis*dis;
		}
		return distance;
	}

	private static int getDistance(Class<?> parameterType, Class<?> objectType) {
		parameterType = Types.toBoxClass(parameterType); 
		if(parameterType.isAssignableFrom(objectType) == false) {
			return -1;
		}
		List<Class> interfaces = new ArrayList<>();
		interfaces.add(objectType);
		
		int dis = 0;
		while(interfaces.isEmpty() == false) {
			List<Class> other = new ArrayList<>();
			for(Class type : interfaces) {
				if(type == parameterType) {
					return dis;
				}
				for(Class it : type.getInterfaces()) {
					other.add(it);
				}
				if(type.isInterface() == false) {
					Class base = type.getSuperclass();
					if(base != null) {
						other.add(base);
					}
				}
			}
			interfaces = other;
			dis++;
		}
		return -1;
	}

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
