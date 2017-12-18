package base.reflect;

import java.lang.reflect.Field;
import java.util.HashMap;

import base.core.Core;
import base.utility.cache.WeakMemoryCache;

public final class Types {

	private Types() {
		throw new StaticClassException();
	}

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
			throw Core.throwException(e);
		}
	}

	private static final WeakMemoryCache<Field, FieldInfo> fields = new WeakMemoryCache<>();
	
	public static FieldInfo asFieldInfo(Field field) {
		if(field == null) {
			return null;
		}
		return fields.getOrCreate(field, ()->new FieldInfo(field));
	}
	
	private static final HashMap<Class, Class> primitiveToBox = new HashMap<>();
	private static final HashMap<Class, Class> boxToPrimitive = new HashMap<>();
	
	private static void register_box(Class primitive, Class box) {
		primitiveToBox.put(primitive, box);
		boxToPrimitive.put(box, primitive);
	}
	
	static {
		register_box(boolean.class, Boolean.class);
		register_box(byte.class, Byte.class);
		register_box(short.class, Short.class);
		register_box(int.class, Integer.class);
		register_box(long.class, Long.class);
		register_box(char.class, Character.class);
		register_box(float.class, Float.class);
		register_box(double.class, Double.class);
		register_box(void.class, Void.class);
	}
	
	public static Class<?> toBoxClass(Class<?> type) {
		Class cl = primitiveToBox.get(type);
		return cl != null ? cl : type;
	}
	
	public static Class<?> toPrimitiveClass(Class<?> type) {
		Class cl = boxToPrimitive.get(type);
		return cl != null ? cl : type;
	}
	
}
