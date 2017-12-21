package base.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import base.core.Core;

public final class FieldInfo {

	private final Field field;
	private final Type type;
	
	FieldInfo(Field field) {
		this.field = field;
		this.type = Types.typeOf(field.getType());
		
		try {
			this.field.setAccessible(true);
		}
		catch (SecurityException e) {
		}
	}
	
	public String getName() {
		return field.getName();
	}

	public Type getType() {
		return type;
	}

	public boolean isPublic() {
		return Modifiers.isPublic(field);
	}

	public void setValue(Object obj, Object value) {
		try {
			field.set(obj, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getValue(Object obj){
		try {
			return (T) field.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}

	public <T extends Annotation> T getAnnotation(Class<T> clazz) {
		return field.getAnnotation(clazz);
	}

	public <T extends Annotation> boolean hasAnnotation(Class<T> clazz) {
		return field.getAnnotation(clazz) != null;
	}

	public Type getDeclaringType() {
		return Types.typeOf(field.getDeclaringClass());
	}

}
