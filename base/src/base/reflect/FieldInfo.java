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
	
	public void setStaticValue(Object value) {
		try {
			field.set(null, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}

	public void setValue(Object obj, byte value) {
		try {
			field.set(obj, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}

	public void setStaticValue(byte value) {
		try {
			field.set(null, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}

	public void setValue(Object obj, boolean value) {
		try {
			field.set(obj, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}

	public void setStaticValue(boolean value) {
		try {
			field.set(null, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}

	public void setValue(Object obj, short value) {
		try {
			field.set(obj, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}

	public void setStaticValue(short value) {
		try {
			field.set(null, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}

	public void setValue(Object obj, char value) {
		try {
			field.set(obj, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}

	public void setStaticValue(char value) {
		try {
			field.set(null, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}

	public void setValue(Object obj, int value) {
		try {
			field.set(obj, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}

	public void setStaticValue(int value) {
		try {
			field.set(null, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}

	public void setValue(Object obj, long value) {
		try {
			field.set(obj, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}

	public void setStaticValue(long value) {
		try {
			field.set(null, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}

	public void setValue(Object obj, float value) {
		try {
			field.set(obj, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}

	public void setStaticValue(float value) {
		try {
			field.set(null, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}

	public void setValue(Object obj, double value) {
		try {
			field.set(obj, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}

	public void setStaticValue(double value) {
		try {
			field.set(null, value);
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

	public boolean getBooleanValue(Object obj) {
		try {
			return field.getBoolean(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}
	
	public byte getByteValue(Object obj) {
		try {
			return field.getByte(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}
	
	public char getCharValue(Object obj) {
		try {
			return field.getChar(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}
	
	public short getShortValue(Object obj) {
		try {
			return field.getShort(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}
	
	public int getIntValue(Object obj) {
		try {
			return field.getInt(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}
	
	public long getLongValue(Object obj) {
		try {
			return field.getLong(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}
	
	public float getFloatValue(Object obj) {
		try {
			return field.getFloat(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw Core.throwException(e);
		}
	}
	
	public double getDoubleValue(Object obj) {
		try {
			return field.getDouble(obj);
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

	public Type getDeclaringType() {
		return Types.typeOf(field.getDeclaringClass());
	}
	
	@Override
	public String toString() {
		return field.toString();
	}

}
