package base.reflect;

import java.lang.reflect.Field;

import base.core.EnumOutOfRangeException;
import base.utility.collection.list.TypeArrayList;

final class FieldContainer {

	private final Type<?> type;
	
	private final FieldInfo[] cachedPublicFields;
	private final FieldInfo[] cachedDeclaredFields;
	
	public FieldContainer(Type<?> type) {
		this.type = type;
		
		Field[] public_fields = type.clazz.getFields();
		this.cachedPublicFields = new FieldInfo[public_fields.length];
		
		for(int i=0; i<public_fields.length; i++) {
			this.cachedPublicFields[i] = Types.asFieldInfo(public_fields[i]);
		}
		
		Field[] declared_fields = type.clazz.getDeclaredFields();
		this.cachedDeclaredFields = new FieldInfo[declared_fields.length];
		for(int i=0; i<declared_fields.length; i++) {
			this.cachedDeclaredFields[i] = Types.asFieldInfo(declared_fields[i]);
		}
	}

	public FieldInfo[] getFields(MemberDomain domain) {
		switch (domain) {
		case Public:
			return cachedPublicFields.clone();
		case Declared:
			return cachedDeclaredFields.clone();
		case PublicOrDeclared:
			{
				TypeArrayList<FieldInfo> fields = new TypeArrayList<>(FieldInfo.class);
				fields.addAll(cachedDeclaredFields);
				for(FieldInfo field : cachedPublicFields) {
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
