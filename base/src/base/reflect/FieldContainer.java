package base.reflect;

import java.lang.reflect.Field;

import base.core.EnumOutOfRangeException;
import base.utility.collection.list.TypeArrayList;

class FieldContainer {

	private final Type<?> type;
	
	private final FieldInfo[] public_fields;
	private final FieldInfo[] declared_fields;
	
	public FieldContainer(Type<?> type) {
		this.type = type;
		
		Class<?> clazz = type.asClass();
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
					if(field.getDeclaringType() != type) {
						fields.add(field);
					}
				}
				return fields.toArray();
			}
		case AllInherited:
			{
				TypeArrayList<FieldInfo> fields = new TypeArrayList<>(FieldInfo.class);
				Type type = this.type;
				while(type != null) {
					fields.addAll(type.getFields(MemberDomain.Declared));
					for(Type interfaze : type.getDeclaredInterfaces()) {
						fields.addAll(interfaze.getFields(MemberDomain.Declared));
					}
					type = type.getSuperType();
				}
				return fields.toArray();
			}
		default:
			throw new EnumOutOfRangeException();
		}
	}
	
}
