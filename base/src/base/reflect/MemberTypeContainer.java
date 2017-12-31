package base.reflect;

import base.utility.collection.list.TypeArrayList;

final class MemberTypeContainer {

	private final ClassType type;
	
	private ClassType<?>[] cachedDeclaredMemberTypes;
	private ClassType<?>[] cachedExportMemberTypes;
	
	public MemberTypeContainer(ClassType type) {
		this.type = type;
	}
	
	public ClassType<?>[] getMemberTypes(){
		return getCachedExportMemberTypesInner().clone();
	}
	
	public ClassType<?>[] getDeclaredMemberTypes(){
		return getCachedDeclaredMemberTypesInner().clone();
	}
	
	private ClassType<?>[] getCachedDeclaredMemberTypesInner(){
		if(cachedDeclaredMemberTypes == null) {
			Class<?>[] memberClasses = type.clazz.getDeclaredClasses();
			cachedDeclaredMemberTypes = new ClassType[memberClasses.length];
			for(int i=0; i<memberClasses.length; i++) {
				cachedDeclaredMemberTypes[i] = Types.typeOf(memberClasses[i]);
			}
		}
		return cachedDeclaredMemberTypes;
	}
	
	private ClassType<?>[] getCachedExportMemberTypesInner(){
		if(cachedExportMemberTypes == null) {
			TypeArrayList<ClassType> list = new TypeArrayList<>(ClassType.class);
			list.addAll(getCachedDeclaredMemberTypesInner());
			for (ClassType<?> base : type.getAssignableSuperTypes()) {
				for (ClassType<?> type : base.memberTypes.getCachedDeclaredMemberTypesInner()) {
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
