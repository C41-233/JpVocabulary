package base.reflect;

import base.utility.collection.list.TypeArrayList;

final class MemberTypeContainer {

	private final TypeInfo type;
	
	private TypeInfo<?>[] cachedDeclaredMemberTypes;
	private TypeInfo<?>[] cachedExportMemberTypes;
	
	public MemberTypeContainer(TypeInfo type) {
		this.type = type;
	}
	
	public TypeInfo<?>[] getMemberTypes(){
		return getCachedExportMemberTypesInner().clone();
	}
	
	public TypeInfo<?>[] getDeclaredMemberTypes(){
		return getCachedDeclaredMemberTypesInner().clone();
	}
	
	private TypeInfo<?>[] getCachedDeclaredMemberTypesInner(){
		if(cachedDeclaredMemberTypes == null) {
			Class<?>[] memberClasses = type.clazz.getDeclaredClasses();
			cachedDeclaredMemberTypes = new TypeInfo[memberClasses.length];
			for(int i=0; i<memberClasses.length; i++) {
				cachedDeclaredMemberTypes[i] = Types.typeOf(memberClasses[i]);
			}
		}
		return cachedDeclaredMemberTypes;
	}
	
	private TypeInfo<?>[] getCachedExportMemberTypesInner(){
		if(cachedExportMemberTypes == null) {
			TypeArrayList<TypeInfo> list = new TypeArrayList<>(TypeInfo.class);
			list.addAll(getCachedDeclaredMemberTypesInner());
			for (TypeInfo<?> base : type.getAssignableSuperTypes()) {
				for (TypeInfo<?> type : base.memberTypes.getCachedDeclaredMemberTypesInner()) {
					if(type.isPublic() || type.isProtected()) {
						list.add(type);
					}
				}
			}
			cachedExportMemberTypes = list.toArray();
		}
		return cachedExportMemberTypes;
	}
	
}
