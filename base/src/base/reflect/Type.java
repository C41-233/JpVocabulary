package base.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Type<T> {

	private final Class<T> clazz;
	private final ArrayList<FieldInfo> fields;
	
	Type(Class<T> clazz) {
		this.clazz = clazz;
		
		Field[] fields = clazz.getDeclaredFields();
		this.fields = new ArrayList<>(fields.length);
		for(int i=0; i<fields.length; i++) {
			this.fields.add(new FieldInfo(fields[i]));
		}
	}
	
	public T newInstance(){
		try {
			Constructor<T> constructor = clazz.getDeclaredConstructor();
			constructor.setAccessible(true);
			return constructor.newInstance();
		} catch (ReflectiveOperationException e) {
			throw new ReflectException(e);
		}
	}

	public FieldInfo[] getFields() {
		return fields.toArray(new FieldInfo[fields.size()]);
	}

	public boolean isArray() {
		return clazz.isArray();
	}
	
	public Class<T> asClass() {
		return clazz;
	}

	public Type getArrayComponentType() {
		return Types.typeOf(clazz.getComponentType());
	}

	@Override
	public String toString() {
		return clazz.getName();
	}
	
}
