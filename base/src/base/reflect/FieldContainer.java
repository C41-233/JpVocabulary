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
			if(field.isPublic() && !flags.isPublic()) {
				continue;
			}
			if(field.isProtected() && !flags.isProtected()) {
				continue;
			}
			if(field.isInternal() && !flags.isInternal()) {
				continue;
			}
			if(field.isPrivate() && !flags.isPrivate()) {
				continue;
			}
			if(field.isStatic() && !flags.isStatic()) {
				continue;
			}
			if(field.isInstance() && !flags.isInstance()) {
				continue;
			}
			list.add(field);
		}
	}
}
