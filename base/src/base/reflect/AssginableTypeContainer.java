package base.reflect;

import base.utility.collection.list.TypeArrayList;
import base.utility.collection.list.TypeArraySet;

final class AssginableTypeContainer<T> {

	private final Type<T> type;

	private Type<? super T>[] cachedDeclaredInterfaces;
	
	private Type<? super T>[] cachedInterfaces;
	
	private Type<? super T>[] cachedExportTypes;
	
	public AssginableTypeContainer(Type<T> type) {
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	public Type<? super T>[] getDeclaredInterfaces() {
		if(cachedDeclaredInterfaces == null) {
			Class<?>[] interfaces = type.clazz.getInterfaces();
			cachedDeclaredInterfaces = new Type[interfaces.length];
			for(int i=0; i<interfaces.length; i++) {
				cachedDeclaredInterfaces[i] = (Type<? super T>) Types.typeOf(interfaces[i]);
			}
		}
		return cachedDeclaredInterfaces.clone();
	}
	
	@SuppressWarnings("unchecked")
	public Type<? super T>[] getInterfaces(){
		if(cachedInterfaces == null) {
			TypeArraySet<Type> interfaces = new TypeArraySet<>(Type.class);
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
	private static void addInterfacesOfInterface(TypeArraySet<Type> interfaces, Class clazz) {
		Type type = Types.typeOf(clazz);
		interfaces.add(type);
		for(Class interfaze : clazz.getInterfaces()) {
			addInterfacesOfInterface(interfaces, interfaze);
		}
	}

	public Type<? super T>[] getExportTypes(){
		return getExportTypesInner().clone();
	}
	
	@SuppressWarnings("unchecked")
	public Type<? super T>[] getExportSuperTypes(){
		Type<? super T>[] types = getExportTypesInner();
		Type<? super T>[] rst = new Type[types.length - 1];
		System.arraycopy(types, 1, rst, 0, rst.length);
		return rst;
	}

	@SuppressWarnings("unchecked")
	private Type<? super T>[] getExportTypesInner(){
		if(cachedExportTypes == null) {
			TypeArrayList<Type> types = new TypeArrayList<>(Type.class);
			Type<? super T> type = this.type;
			while(type != null) {
				types.add(type);
				type = type.getSuperType();
			}
			for(Type<? super T> interfaze : getInterfaces()) {
				types.add(interfaze);
			}
			cachedExportTypes = types.toArray();
		}
		return cachedExportTypes;
	}
	
}

