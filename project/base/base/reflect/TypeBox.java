package base.reflect;

import java.util.HashMap;

public final class TypeBox {

	private TypeBox() {}
	
	private static final HashMap<Class, Class> primitiveToBox = new HashMap<>();
	private static final HashMap<Class, Class> boxToPrimitive = new HashMap<>();
	
	private static void init(Class primitive, Class box) {
		primitiveToBox.put(primitive, box);
		boxToPrimitive.put(box, primitive);
	}
	
	static {
		init(byte.class, Byte.class);
		init(short.class, Short.class);
		init(int.class, Integer.class);
		init(long.class, Long.class);
		init(char.class, Character.class);
		init(float.class, Float.class);
		init(double.class, Double.class);
		init(void.class, Void.class);
	}
	
	public static Class toBoxType(Class type) {
		Class cl = primitiveToBox.get(type);
		return cl != null ? cl : type;
	}
	
	public static Class toPrimitiveType(Class type) {
		Class cl = boxToPrimitive.get(type);
		return cl != null ? cl : type;
	}
	
}
