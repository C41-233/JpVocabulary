package base.reflect;

import java.lang.reflect.Field;

import base.utility.collection.list.TypeArrayList;

final class FieldContainer {

	private final Type<?> type;
	
	private final FieldInfo[] cachedDeclaredFields;
	
	public FieldContainer(Type<?> type) {
		this.type = type;
		
		Field[] declared_fields = type.clazz.getDeclaredFields();
		this.cachedDeclaredFields = new FieldInfo[declared_fields.length];
		for(int i=0; i<declared_fields.length; i++) {
			this.cachedDeclaredFields[i] = Types.asFieldInfo(declared_fields[i]);
		}
	}

	public FieldInfo[] getFields(MemberDomainFlags flags){
		TypeArrayList<FieldInfo> list = new TypeArrayList<>(FieldInfo.class);
		
		addFieldsInner(list, flags);
		if(flags.isInterited()) {
			for(Type base : type.getExportSuperTypes()) {
				base.fields.addFieldsInner(list, flags);
			}
		}
		
		return list.toArray();
	}

	private void addFieldsInner(TypeArrayList<FieldInfo> list, MemberDomainFlags flags) {
		for(FieldInfo field : cachedDeclaredFields) {
			if(isMatchFlag(field, flags)) {
				list.add(field);
			}
		}
	}

	private static boolean isMatchFlag(FieldInfo field, MemberDomainFlags flags) {
		if(field.isPublic() && !flags.isPublic()) {
			return false;
		}
		if(field.isProtected() && !flags.isProtected()) {
			return false;
		}
		if(field.isInternal() && !flags.isInternal()) {
			return false;
		}
		if(field.isPrivate() && !flags.isPrivate()) {
			return false;
		}
		if(field.isStatic() && !flags.isStatic()) {
			return false;
		}
		if(field.isInstance() && !flags.isInstance()) {
			return false;
		}
		return true;
	}
	
	public FieldInfo getField(String name, MemberDomainFlags flags) {
		for (FieldInfo field : cachedDeclaredFields) {
			if(isMatchFlag(field, flags)) {
				return field;
			}
		}
		if(flags.isInterited()) {
			for(Type base : type.getExportSuperTypes()) {
				FieldInfo field = base.fields.getField(name, flags);
				if(field != null) {
					return field;
				}
			}
		}
		
		return null;
	}
}
