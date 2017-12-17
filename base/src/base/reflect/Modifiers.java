package base.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Modifiers {

	public static boolean isPublic(Class<?> clazz) {
		return isPublic(clazz.getModifiers());
	}
	
	public static boolean isPublic(Field field) {
		return isPublic(field.getModifiers());
	}
	
	public static boolean isPublic(Constructor<?> constructor) {
		return isPublic(constructor.getModifiers());
	}
		
	public static boolean isPublic(Method method) {
		return isPublic(method.getModifiers());
	}
	
	public static boolean isPublic(int modifier) {
		return Modifier.isPublic(modifier);
	}

	public static boolean isProtected(Class<?> clazz) {
		return isProtected(clazz.getModifiers());
	}
	
	public static boolean isProtected(Field field) {
		return isProtected(field.getModifiers());
	}
	
	public static boolean isProtected(int modifier) {
		return Modifier.isProtected(modifier);
	}
	
	public static boolean isInternal(Field field) {
		return isInternal(field.getModifiers());
	}
	
	public static boolean isInternal(int modifier) {
		return !Modifier.isPublic(modifier) && !Modifier.isProtected(modifier) && !Modifier.isPrivate(modifier);
	}
	
	public static boolean isPrivate(Field field) {
		return isPrivate(field.getModifiers());
	}

	public static boolean isPrivate(int modifier) {
		return Modifier.isPrivate(modifier);
	}
	
}
