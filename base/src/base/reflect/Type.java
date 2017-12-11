package base.reflect;

import java.lang.reflect.Constructor;

public class Type<T> {

	public static <T> Type<T> load(Class<T> cl){
		return new Type<>(cl);
	}
	
	private final Class<T> clazz;
	
	private Type(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public T newInstance(){
		try {
			Constructor<T> constructor = clazz.getDeclaredConstructor();
			constructor.setAccessible(true);
			return constructor.newInstance();
		} catch (ReflectiveOperationException e) {
			throw new ReflectException(e);
		}
	}
	
}
