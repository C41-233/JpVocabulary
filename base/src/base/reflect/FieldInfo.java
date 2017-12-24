package base.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import base.core.Core;

public final class FieldInfo implements IAnnotatedReflectElement{

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

	public boolean isProtected() {
		return Modifiers.isProtected(field);
	}

	public boolean isInternal() {
		return Modifiers.isInternal(field);
	}

	public boolean isStatic() {
		return Modifiers.isStatic(field);
	}

	public boolean isInstance() {
		return Modifiers.isInstance(field);
	}

	public boolean isPrivate() {
		return Modifiers.isPrivate(field);
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

	@Override
	public <T extends Annotation> T getAnnotation(Class<T> clazz) {
		return field.getDeclaredAnnotation(clazz);
	}

	@Override
	public <T extends Annotation> boolean hasAnnotation(Class<T> clazz) {
		return field.getDeclaredAnnotation(clazz) != null;
	}

	@Override
	public Annotation[] getAnnotations() {
		return field.getDeclaredAnnotations();
	}

	@Override
	public <TAnnotation extends Annotation> TAnnotation[] getAnnotations(Class<TAnnotation> cl) {
		return field.getDeclaredAnnotationsByType(cl);
	}

	public Type getDeclaringType() {
		return Types.typeOf(field.getDeclaringClass());
	}
	
	@Override
	public String toString() {
		return field.toString();
	}

}
