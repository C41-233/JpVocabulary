package c41.reflect;

import java.lang.reflect.Field;

import c41.utility.collection.list.TypeArrayList;

final class FieldContainer {

	private final TypeInfo<?> type;
	
	private FieldInfo[] cachedDeclaredFields;
	private FieldInfo[] cachedExportFields;
	
	public FieldContainer(TypeInfo<?> type) {
		this.type = type;
	}

	private FieldInfo[] getCachedDeclaredFieldsInner() {
		if(cachedDeclaredFields == null) {
			Field[] declared_fields = type.clazz.getDeclaredFields();
			this.cachedDeclaredFields = new FieldInfo[declared_fields.length];
			for(int i=0; i<declared_fields.length; i++) {
				this.cachedDeclaredFields[i] = ReflectCache.wrap(declared_fields[i]);
			}
		}
		return cachedDeclaredFields;
	}

	public FieldInfo[] getDeclaredFields() {
		return getCachedDeclaredFieldsInner().clone();
	}
	
	public FieldInfo getDeclaredField(String name) {
		for (FieldInfo field : getCachedDeclaredFieldsInner()) {
			if(field.getName().equals(name)) {
				return field;
			}
		}
		return null;
	}
	
	private FieldInfo[] getCachedExportFieldsInner() {
		if(cachedExportFields == null) {
			TypeArrayList<FieldInfo> list = new TypeArrayList<>(FieldInfo.class);
			list.addAll(getCachedDeclaredFieldsInner());
			for(TypeInfo<?> base : type.getAssignableSuperTypes()) {
				for (FieldInfo field : base.fields.getCachedDeclaredFieldsInner()) {
					if(field.isPublic() || field.isProtected()) {
						list.add(field);
					}
				}
			}
			this.cachedExportFields = list.toArray();
		}
		return cachedExportFields;
	}
	
	public FieldInfo[] getFields() {
		return getCachedExportFieldsInner().clone();
	}
	
	public FieldInfo getField(String name) {
		for (FieldInfo field : getCachedExportFieldsInner()) {
			if(field.getName().equals(name)) {
				return field;
			}
		}
		return null;
	}
	
}
