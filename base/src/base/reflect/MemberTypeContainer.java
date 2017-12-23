package base.reflect;

import base.utility.collection.list.TypeArrayList;

final class MemberTypeContainer {

	private final Type type;
	private final Class<?>[] memberClasses;
	
	private Type<?>[] cachedMemberTypes;
	
	public MemberTypeContainer(Type type) {
		this.type = type;
		this.memberClasses = type.clazz.getDeclaredClasses();
	}
	
	public Type<?>[] getMemberTypes(MemberDomainFlags flags){
		TypeArrayList<Type> list = new TypeArrayList<>(Type.class);
		
		addMemberTypesInner(list, flags);
		if(flags.isInterited()) {
			for(Type base : type.getExportSuperTypes()) {
				base.memberTypes.addMemberTypesInner(list, flags);
			}
		}
		
		return list.toArray();
	}
	
	private void addMemberTypesInner(TypeArrayList<Type> list, MemberDomainFlags flags) {
		Type[] types = getCachedMemberTypes();
		for (Type type : types) {
			if(type.isPublic() && !flags.isPublic()) {
				continue;
			}
			if(type.isProtected() && !flags.isProtected()) {
				continue;
			}
			if(type.isInternal() && !flags.isInternal()) {
				continue;
			}
			if(type.isPrivate() && !flags.isPrivate()) {
				continue;
			}
			if(type.isStatic() && !flags.isStatic()) {
				continue;
			}
			if(!type.isStatic() && !flags.isInstance()) {
				continue;
			}
			list.add(type);
		}
	}
	
	private Type<?>[] getCachedMemberTypes(){
		if(cachedMemberTypes == null) {
			cachedMemberTypes = new Type[memberClasses.length];
			for(int i=0; i<memberClasses.length; i++) {
				cachedMemberTypes[i] = Types.typeOf(memberClasses[i]);
			}
		}
		return cachedMemberTypes;
	}
}
