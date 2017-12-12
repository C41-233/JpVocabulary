package base.reflect;

import base.utility.cache.WeakMemoryCache;

public final class Types {

	private Types() {}

	private static final WeakMemoryCache<Class, Type> cache = new WeakMemoryCache<>();
	
	public synchronized static <T> Type<T> typeOf(Class<T> clazz){
		if(clazz == null) {
			return null;
		}
		
		@SuppressWarnings("unchecked")
		Type<T> type = cache.get(clazz);
		if(type != null) {
			return type;
		}
		type = new Type<T>(clazz);
		cache.put(clazz, type);
		return type;
	}
	
	
	public synchronized static Type<?> typeOf(String clazz){
		try {
			return typeOf(Class.forName(clazz));
		} catch (ClassNotFoundException e) {
			throw new ReflectException(e);
		}
	}
}
