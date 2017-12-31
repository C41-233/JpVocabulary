package base.reflect;

import base.utility.collection.list.TypeArrayList;
import base.utility.collection.list.TypeArraySet;

final class AssginableTypeContainer<T> {

	private final ClassType<T> type;

	private ClassType<? super T>[] cachedDeclaredInterfaces;
	
	private ClassType<? super T>[] cachedInterfaces;
	
	private ClassType<? super T>[] cachedExportTypes;
	
	public AssginableTypeContainer(ClassType<T> type) {
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	public ClassType<? super T>[] getDeclaredInterfaces() {
		if(cachedDeclaredInterfaces == null) {
			Class<?>[] interfaces = type.clazz.getInterfaces();
			cachedDeclaredInterfaces = new ClassType[interfaces.length];
			for(int i=0; i<interfaces.length; i++) {
				cachedDeclaredInterfaces[i] = (ClassType<? super T>) Types.typeOf(interfaces[i]);
			}
		}
		return cachedDeclaredInterfaces.clone();
	}
	
	@SuppressWarnings("unchecked")
	public ClassType<? super T>[] getInterfaces(){
		if(cachedInterfaces == null) {
			TypeArraySet<ClassType> interfaces = new TypeArraySet<>(ClassType.class);
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
	private static void addInterfacesOfInterface(TypeArraySet<ClassType> interfaces, Class clazz) {
		ClassType type = Types.typeOf(clazz);
		interfaces.add(type);
		for(Class interfaze : clazz.getInterfaces()) {
			addInterfacesOfInterface(interfaces, interfaze);
		}
	}

	public ClassType<? super T>[] getExportTypes(){
		return getExportTypesInner().clone();
	}
	
	@SuppressWarnings("unchecked")
	public ClassType<? super T>[] getExportSuperTypes(){
		ClassType<? super T>[] types = getExportTypesInner();
		ClassType<? super T>[] rst = new ClassType[types.length - 1];
		System.arraycopy(types, 1, rst, 0, rst.length);
		return rst;
	}

	@SuppressWarnings("unchecked")
	private ClassType<? super T>[] getExportTypesInner(){
		if(cachedExportTypes == null) {
			TypeArrayList<ClassType> types = new TypeArrayList<>(ClassType.class);
			ClassType<? super T> type = this.type;
			while(type != null) {
				types.add(type);
				type = type.getSuperType();
			}
			for(ClassType<? super T> interfaze : getInterfaces()) {
				types.add(interfaze);
			}
			cachedExportTypes = types.toArray();
		}
		return cachedExportTypes;
	}
	
}

