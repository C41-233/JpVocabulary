package base.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class FieldInfo {

	private final Field field;
	private final Type type;
	
	FieldInfo(Field field) {
		this.field = field;
		this.type = Types.typeOf(field.getType());
		
		this.field.setAccessible(true);
	}
	
	public String getName() {
		return field.getName();
	}

	public Type getType() {
		return type;
	}

	public void setValue(Object obj, Object value) {
		try {
			field.set(obj, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new ReflectException(e);
		}
	}

	public <T extends Annotation> T getAnnotation(Class<T> clazz) {
		return field.getAnnotation(clazz);
	}

	public <T extends Annotation> boolean hasAnnotation(Class<T> clazz) {
		return field.getAnnotation(clazz) != null;
	}

}
