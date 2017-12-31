package base.reflect;

import base.utility.collection.list.TypeArrayList;

final class MemberTypeContainer {

	private final Type type;
	
	private Type<?>[] cachedDeclaredMemberTypes;
	private Type<?>[] cachedExportMemberTypes;
	
	public MemberTypeContainer(Type type) {
		this.type = type;
	}
	
	public Type<?>[] getMemberTypes(){
		return getCachedExportMemberTypesInner().clone();
	}
	
	public Type<?>[] getDeclaredMemberTypes(){
		return getCachedDeclaredMemberTypesInner().clone();
	}
	
	private Type<?>[] getCachedDeclaredMemberTypesInner(){
		if(cachedDeclaredMemberTypes == null) {
			Class<?>[] memberClasses = type.clazz.getDeclaredClasses();
			cachedDeclaredMemberTypes = new Type[memberClasses.length];
			for(int i=0; i<memberClasses.length; i++) {
				cachedDeclaredMemberTypes[i] = Types.typeOf(memberClasses[i]);
			}
		}
		return cachedDeclaredMemberTypes;
	}
	
	private Type<?>[] getCachedExportMemberTypesInner(){
		if(cachedExportMemberTypes == null) {
			TypeArrayList<Type> list = new TypeArrayList<>(Type.class);
			list.addAll(getCachedDeclaredMemberTypesInner());
			for (Type<?> base : type.getAssignableSuperTypes()) {
				for (Type<?> type : base.memberTypes.getCachedDeclaredMemberTypesInner()) {
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
