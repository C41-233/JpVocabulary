package c41.xml.simple;

import java.util.HashMap;

class TypeProviers {

	@FunctionalInterface 
	private interface ITypeProvider<T>{
		public T create(String value);
	}
	private static HashMap<Class<?>, ITypeProvider<?>> providers = new HashMap<>();
	
	static {
		providers.put(String.class, value->value);
		providers.put(int.class, value->Integer.parseInt(value));
		providers.put(long.class, value->Long.parseLong(value));
	}
	
	public static boolean contains(Class<?> clazz) {
		return providers.containsKey(clazz);
	}
	
	public static Object create(Class<?> clazz, String value) {
		ITypeProvider<?> provider = providers.get(clazz);
		if(provider == null) {
			return null;
		}
		return provider.create(value);
	}
	
}
