package base.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Objects;

import base.core.Core;

public final class FieldInfo implements IAnnotatedReflectElement, IAccessableReflectElement{

	private final Field field;
	private final ClassType type;
	
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

	public ClassType getType() {
		return type;
	}
	
	public Type getGenericType() {
		return field.getGenericType();
	}
	
	@Override
	public boolean isPublic() {
		return Modifiers.isPublic(field);
	}

	@Override
	public boolean isProtected() {
		return Modifiers.isProtected(field);
	}

	@Override
	public boolean isInternal() {
		return Modifiers.isInternal(field);
	}

	public boolean isStatic() {
		return Modifiers.isStatic(field);
	}

	public boolean isInstance() {
		return Modifiers.isInstance(field);
	}

	@Override
	public boolean isPrivate() {
		return Modifiers.isPrivate(field);
	}
	
	public boolean isEnumConstant() {
		return field.isEnumConstant();
	}

	public boolean isSynthetic() {
		return field.isSynthetic();
	}
	
	public void setValue(Object obj, Object value) {
		Objects.requireNonNull(obj);
		try {
			field.set(obj, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}
	
	public void setStaticValue(Object value) {
		try {
			field.set(null, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}


	@SuppressWarnings("unchecked")
	public <T> T getValue(Object obj){
		Objects.requireNonNull(obj);
		try {
			return (T) field.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getStaticValue(){
		try {
			return (T) field.get(null);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}

	@Override
	public <T extends Annotation> T getAnnotation(Class<T> clazz) {
		return field.getAnnotation(clazz);
	}

	@Override
	public <T extends Annotation> boolean hasAnnotation(Class<T> clazz) {
		return field.getAnnotation(clazz) != null;
	}

	@Override
	public Annotation[] getAnnotations() {
		return field.getAnnotations();
	}

	@Override
	public <TAnnotation extends Annotation> TAnnotation[] getAnnotations(Class<TAnnotation> cl) {
		return field.getAnnotationsByType(cl);
	}

	public ClassType getDeclaringType() {
		return Types.typeOf(field.getDeclaringClass());
	}
	
	@Override
	public String toString() {
		return field.toString();
	}

	public int getModifiers() {
		return field.getModifiers();
	}
	
}
