package base.reflect;

import base.utility.collection.list.TypeArraySet;

public class Type<T> {

	private final Class<T> clazz;
	
	private final ConstructorContainer<T> constructors;
	private final FieldContainer fields;
	
	Type(Class<T> clazz) {
		this.clazz = clazz;

		this.fields = new FieldContainer(this);
		this.constructors = new ConstructorContainer<>(this);
	}
	
	public T newInstance(){
		return constructors.newInstance();
	}

	public T cast(Object object) {
		try {
			return clazz.cast(object);
		}
		catch (ClassCastException e) {
			return null;
		}
	}
	
	public FieldInfo[] getFields() {
		return getFields(MemberDomain.Public);
	}
	
	public FieldInfo[] getFields(MemberDomain domain) {
		return fields.getFields(domain);
	}

	@SuppressWarnings("unchecked")
	public Type<? super T>[] getDeclaredInterfaces() {
		Class<?>[] interfaces = clazz.getInterfaces();
		Type<? super T>[] types = new Type[interfaces.length];
		for(int i=0; i<interfaces.length; i++) {
			types[i] = (Type<? super T>) Types.typeOf(interfaces[i]);
		}
		return types;
	}
	
	@SuppressWarnings("unchecked")
	public Type<? super T>[] getInterfaces(){
		TypeArraySet<Type> interfaces = new TypeArraySet<>(Type.class);
		Class cl = clazz;
		while(cl != null) {
			for (Class interfaze : cl.getInterfaces()) {
				addInterfacesOfInterface(interfaces, interfaze);
			}
			cl = cl.getSuperclass();
		}
		return interfaces.toArray();
	}
	
	@SuppressWarnings("unchecked")
	private static void addInterfacesOfInterface(TypeArraySet<Type> interfaces, Class clazz) {
		Type type = Types.typeOf(clazz);
		interfaces.add(type);
		for(Class interfaze : clazz.getInterfaces()) {
			addInterfacesOfInterface(interfaces, interfaze);
		}
	}

	public boolean isArray() {
		return clazz.isArray();
	}
	
	public boolean isClass() {
		return !clazz.isInterface();
	}
	
	public boolean isInterface() {
		return clazz.isInterface();
	}
	
	public boolean isPublic() {
		return Modifiers.isPublic(clazz);
	}
	
	public boolean isProtected() {
		return Modifiers.isProtected(clazz);
	}
	
	public boolean isPrivate() {
		return Modifiers.isPrivate(clazz);
	}
	
	public boolean isInternal() {
		return Modifiers.isInternal(clazz);
	}
	
	public boolean isFinal() {
		return Modifiers.isFinal(clazz);
	}
	
	public boolean isAbstract() {
		return Modifiers.isAbstract(clazz);
	}
	
	public boolean isStrictfp() {
		return Modifiers.isStrictfp(clazz);
	}
	
	public boolean isStatic() {
		return Modifiers.isStatic(clazz);
	}
	
	public Class<T> asClass() {
		return clazz;
	}

	public Type<?> getArrayComponentType() {
		return Types.typeOf(clazz.getComponentType());
	}

	public Type<? super T> getSuperType() {
		return Types.typeOf(clazz.getSuperclass());
	}

	@Override
	public String toString() {
		return clazz.getName();
	}
	
	@Override
	public boolean equals(Object other) {
		return this == other;
	}
	
	@Override
	public int hashCode() {
		return this.clazz.hashCode();
	}

}
