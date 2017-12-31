package base.reflect;

import base.utility.collection.list.TypeArrayList;
import base.utility.collection.list.TypeArraySet;

final class AssginableTypeContainer<T> {

	private final TypeInfo<T> type;

	private TypeInfo<? super T>[] cachedDeclaredInterfaces;
	
	private TypeInfo<? super T>[] cachedInterfaces;
	
	private TypeInfo<? super T>[] cachedExportTypes;
	
	public AssginableTypeContainer(TypeInfo<T> type) {
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	public TypeInfo<? super T>[] getDeclaredInterfaces() {
		if(cachedDeclaredInterfaces == null) {
			Class<?>[] interfaces = type.clazz.getInterfaces();
			cachedDeclaredInterfaces = new TypeInfo[interfaces.length];
			for(int i=0; i<interfaces.length; i++) {
				cachedDeclaredInterfaces[i] = (TypeInfo<? super T>) Types.typeOf(interfaces[i]);
			}
		}
		return cachedDeclaredInterfaces.clone();
	}
	
	@SuppressWarnings("unchecked")
	public TypeInfo<? super T>[] getInterfaces(){
		if(cachedInterfaces == null) {
			TypeArraySet<TypeInfo> interfaces = new TypeArraySet<>(TypeInfo.class);
			Class cl = type.clazz;
			while(cl != null) {
				for (Class interfaze : cl.getInterfaces()) {
					addInterfacesOfInterface(interfaces, interfaze);
				}
				cl = cl.getSuperclass();
			}
			cachedInterfaces = interfaces.toArray();
		}
		return cachedInterfaces.clone();
	}
	
	@SuppressWarnings("unchecked")
	private static void addInterfacesOfInterface(TypeArraySet<TypeInfo> interfaces, Class clazz) {
		TypeInfo type = Types.typeOf(clazz);
		interfaces.add(type);
		for(Class interfaze : clazz.getInterfaces()) {
			addInterfacesOfInterface(interfaces, interfaze);
		}
	}

	public TypeInfo<? super T>[] getExportTypes(){
		return getExportTypesInner().clone();
	}
	
	@SuppressWarnings("unchecked")
	public TypeInfo<? super T>[] getExportSuperTypes(){
		TypeInfo<? super T>[] types = getExportTypesInner();
		TypeInfo<? super T>[] rst = new TypeInfo[types.length - 1];
		System.arraycopy(types, 1, rst, 0, rst.length);
		return rst;
	}

	@SuppressWarnings("unchecked")
	private TypeInfo<? super T>[] getExportTypesInner(){
		if(cachedExportTypes == null) {
			TypeArrayList<TypeInfo> types = new TypeArrayList<>(TypeInfo.class);
			TypeInfo<? super T> type = this.type;
			while(type != null) {
				types.add(type);
				type = type.getSuperType();
			}
			for(TypeInfo<? super T> interfaze : getInterfaces()) {
				types.add(interfaze);
			}
			cachedExportTypes = types.toArray();
		}
		return cachedExportTypes;
	}
	
}

