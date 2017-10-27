package base.core;

public final class Objects {

	@SuppressWarnings("unchecked")
	public static <V, T> V cast(T obj) {
		return (V)obj;
	}
	
	public static <T extends Enum<T>> T asEnum(Class<T> enumClass, String value){
		try {
			return Enum.valueOf(enumClass, value);
		}
		catch (IllegalArgumentException e) {
			return null;
		}
	}
	
	public static <T, V extends T> V as(Class<V> cl, T obj) {
		try {
			return cl.cast(obj);
		} catch (ClassCastException e) {
			return null;
		}
	}
	
}
