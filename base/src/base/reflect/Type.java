package base.reflect;

import java.lang.annotation.Annotation;

public final class Type<T> {

	final Class<T> clazz;
	
	private final ConstructorContainer<T> constructors;
	private final FieldContainer fields;
	private final ExportTypeContainer<T> export;
	
	Type(Class<T> clazz) {
		this.clazz = clazz;

		this.export = new ExportTypeContainer<>(this);
		this.fields = new FieldContainer(this);
		this.constructors = new ConstructorContainer<>(this);
	}
	
	public T newInstance(){
		return constructors.newInstance();
	}

	public <U> Type<? extends U> asSubTypeOf(Type<U> type){
		try {
			return Types.typeOf(clazz.asSubclass(type.clazz));
		}
		catch (ClassCastException e) {
			return null;
		}
	}
	
	public <U> Type<? extends U> asSubTypeOf(Class<U> cl){
		try {
			return Types.typeOf(clazz.asSubclass(cl));
		}
		catch (ClassCastException e) {
			return null;
		}
	}
	
	public T cast(Object object) {
		try {
			return clazz.cast(object);
		}
		catch (ClassCastException e) {
			return null;
		}
	}
	
	public <A extends Annotation> A getAnnotation(Type<A> type) {
		return clazz.getAnnotation(type.clazz);
	}
	
	public <A extends Annotation> A getAnnotation(Class<A> cl) {
		return clazz.getAnnotation(cl);
	}
	
	public <A extends Annotation> boolean hasAnnotation(Type<A> type) {
		return clazz.isAnnotationPresent(type.clazz);
	}
	
	public <A extends Annotation> boolean hasAnnotation(Class<A> cl) {
		return clazz.isAnnotationPresent(cl);
	}
	
	public ConstructorInfo<T> getConstructor(Class<?>...parameterTypes){
		return constructors.getConstructor(parameterTypes);
	}
	
	public ConstructorInfo<T>[] getConstructors(){
		return constructors.getConstructors();
	}
	
	public FieldInfo[] getFields() {
		return getFields(MemberDomain.Public);
	}
	
	public FieldInfo[] getFields(MemberDomain domain) {
		return fields.getFields(domain);
	}

	public Type<? super T>[] getDeclaredInterfaces() {
		return export.getDeclaredInterfaces();
	}
	
	public Type<? super T>[] getInterfaces(){
		return export.getInterfaces();
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

	public String getCanonicalName() {
		return clazz.getCanonicalName();
	}
	
	public ClassLoader getClassLoader() {
		return clazz.getClassLoader();
	}
	
	public Type<?> getArrayComponentType() {
		return Types.typeOf(clazz.getComponentType());
	}

	public Type<? super T> getSuperType() {
		return Types.typeOf(clazz.getSuperclass());
	}

	public Type<? super T>[] getExportTypes(){
		return export.getExportTypes();
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
