package base.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import base.core.Core;
import base.core.EnumOutOfRangeException;
import base.utility.collection.list.TypeArrayList;

public class Type<T> {

	private final Class<T> clazz;
	
	private final FieldInfo[] public_fields;
	private final FieldInfo[] declared_fields;
	
	Type(Class<T> clazz) {
		this.clazz = clazz;
		
		Field[] public_fields = clazz.getFields();
		this.public_fields = new FieldInfo[public_fields.length];
		
		for(int i=0; i<public_fields.length; i++) {
			this.public_fields[i] = Types.asFieldInfo(public_fields[i]);
		}
		
		Field[] declared_fields = clazz.getDeclaredFields();
		this.declared_fields = new FieldInfo[declared_fields.length];
		for(int i=0; i<declared_fields.length; i++) {
			this.declared_fields[i] = Types.asFieldInfo(declared_fields[i]);
		}
	}
	
	public T newInstance(){
		try {
			Constructor<T> constructor = clazz.getDeclaredConstructor();
			constructor.setAccessible(true);
			return constructor.newInstance();
		} catch (ReflectiveOperationException e) {
			throw Core.throwException(e);
		}
	}

	public FieldInfo[] getFields() {
		return getFields(MemberDomain.Public);
	}
	
	public FieldInfo[] getFields(MemberDomain domain) {
		switch (domain) {
		case Public:
			return public_fields.clone();
		case Declared:
			return declared_fields.clone();
		case PublicOrDeclared:
			{
				TypeArrayList<FieldInfo> fields = new TypeArrayList<>(FieldInfo.class);
				fields.addAll(declared_fields);
				for(FieldInfo field : public_fields) {
					if(field.getDeclaringType() != this) {
						fields.add(field);
					}
				}
				return fields.toArray();
			}
		case AllInherited:
			{
				TypeArrayList<FieldInfo> fields = new TypeArrayList<>(FieldInfo.class);
				Type type = this;
				while(type != null) {
					fields.addAll(type.declared_fields);
					type = type.getSuperType();
				}
				return fields.toArray();
			}
		default:
			throw new EnumOutOfRangeException();
		}
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

	private Type getSuperType() {
		return Types.typeOf(clazz.getSuperclass());
	}

	@Override
	public String toString() {
		return clazz.getName();
	}
	
	@Override
	public boolean equals(Object other) {
		return this == other;
	}
	
	@Override
	public int hashCode() {
		return this.clazz.hashCode();
	}
	
}
