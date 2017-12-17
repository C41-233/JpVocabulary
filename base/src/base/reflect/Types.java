package base.reflect;

import java.lang.reflect.Field;

import base.utility.cache.WeakMemoryCache;

public final class Types {

	private Types() {}

	private static final WeakMemoryCache<Class, Type> classes = new WeakMemoryCache<>();
	
	@SuppressWarnings("unchecked")
	public static <T> Type<T> typeOf(Class<T> clazz){
		if(clazz == null) {
			return null;
		}
		
		return classes.getOrCreate(clazz, ()->new Type<T>(clazz));
	}
	
	public static Type<?> typeOf(String clazz){
		try {
			return typeOf(Class.forName(clazz));
		} catch (ClassNotFoundException e) {
			throw new ReflectException(e);
		}
	}

	private static final WeakMemoryCache<Field, FieldInfo> fields = new WeakMemoryCache<>();
	
	public static FieldInfo asFieldInfo(Field field) {
		if(field == null) {
			return null;
		}
		return fields.getOrCreate(field, ()->new FieldInfo(field));
	}
	
}
