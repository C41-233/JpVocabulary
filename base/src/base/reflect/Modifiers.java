package base.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class Modifiers {

	private Modifiers() {
		throw new StaticClassException();
	}
	
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
	
	public static boolean isProtected(Constructor<?> constructor) {
		return isProtected(constructor.getModifiers());
	}
	
	public static boolean isProtected(Method method) {
		return isProtected(method.getModifiers());
	}
	
	public static boolean isProtected(int modifier) {
		return Modifier.isProtected(modifier);
	}
	
	public static boolean isInternal(Class<?> clazz) {
		return isInternal(clazz.getModifiers());
	}
	
	public static boolean isInternal(Field field) {
		return isInternal(field.getModifiers());
	}

	public static boolean isInternal(Constructor<?> constructor) {
		return isInternal(constructor.getModifiers());
	}
	
	public static boolean isInternal(Method method) {
		return isInternal(method.getModifiers());
	}
	
	public static boolean isInternal(int modifier) {
		return !Modifier.isPublic(modifier) && !Modifier.isProtected(modifier) && !Modifier.isPrivate(modifier);
	}
	
	public static boolean isPrivate(Class<?> clazz) {
		return isPrivate(clazz.getModifiers());
	}
	
	public static boolean isPrivate(Field field) {
		return isPrivate(field.getModifiers());
	}

	public static boolean isPrivate(Constructor<?> constructor) {
		return isPrivate(constructor.getModifiers());
	}
	
	public static boolean isPrivate(Method method) {
		return isPrivate(method.getModifiers());
	}
	
	public static boolean isPrivate(int modifier) {
		return Modifier.isPrivate(modifier);
	}
	
	public static boolean isStatic(Class<?> clazz) {
		return isStatic(clazz.getModifiers());
	}
	
	public static boolean isStatic(Field field) {
		return isStatic(field.getModifiers());
	}
	
	public static boolean isStatic(Method method) {
		return isStatic(method.getModifiers());
	}
	
	public static boolean isStatic(int modifier) {
		return Modifier.isStatic(modifier);
	}
	
	public static boolean isAbstract(Class<?> clazz) {
		return isAbstract(clazz.getModifiers());
	}
	
	public static boolean isAbstract(Method method) {
		return isAbstract(method.getModifiers());
	}
	
	public static boolean isAbstract(int modifier) {
		return Modifier.isAbstract(modifier);
	}
	
}
