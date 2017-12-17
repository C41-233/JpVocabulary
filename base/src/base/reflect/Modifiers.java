package base.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Modifiers {

	public static boolean isPublic(Field field) {
		return Modifier.isPublic(field.getModifiers());
	}

	public static boolean isProtected(Field field) {
		return Modifier.isProtected(field.getModifiers());
	}
	
	public static boolean isPrivate(Field field) {
		return Modifier.isPrivate(field.getModifiers());
	}
	
}
