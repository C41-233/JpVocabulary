package base.reflect;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;

public final class Type<T> implements IAnnotatedReflectElement, IGenericReflectElement<Class<T>>{

	final Class<T> clazz;
	
	final ConstructorContainer<T> constructors;
	final FieldContainer fields;
	final AssginableTypeContainer<T> assignables;
	final MemberTypeContainer memberTypes;
	
	Type(Class<T> clazz) {
		this.clazz = clazz;

		this.assignables = new AssginableTypeContainer<>(this);
		this.memberTypes = new MemberTypeContainer(this);
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
	
	@Override
	public <TAnnotation extends Annotation> TAnnotation getAnnotation(Class<TAnnotation> cl) {
		return clazz.getAnnotation(cl);
	}

	@Override
	public Annotation[] getAnnotations() {
		return clazz.getAnnotations();
	}
	
	@Override
	public <TAnnotation extends Annotation> TAnnotation[] getAnnotations(Class<TAnnotation> cl) {
		return clazz.getAnnotationsByType(cl);
	}
	
	@Override
	public Annotation[] getDeclaredAnnotations() {
		return clazz.getDeclaredAnnotations();
	}
	
	@Override
	public <TAnnotation extends Annotation> boolean hasAnnotation(Class<TAnnotation> cl) {
		return clazz.isAnnotationPresent(cl);
	}
	
	public ConstructorInfo<T> getConstructor(Class<?>...parameterTypes){
		return constructors.getConstructor(parameterTypes);
	}
	
	public ConstructorInfo<T>[] getConstructors(){
		return constructors.getConstructors();
	}
	
	public FieldInfo getDeclaredField(String name) {
		return getField(name, MemberDomains.AllDeclared);
	}
	
	public FieldInfo getPublicField(String name) {
		return getField(name, MemberDomains.AllPublic);
	}
	
	public FieldInfo getField(String name, int domain) {
		return fields.getField(name, new MemberDomainFlags(domain));
	}
	
	public FieldInfo[] getDeclaredFields() {
		return getFields(MemberDomains.AllDeclared);
	}
	
	public FieldInfo[] getPublicFields() {
		return getFields(MemberDomains.AllPublic);
	}
	
	public FieldInfo[] getFields(int domain) {
		return fields.getFields(new MemberDomainFlags(domain));
	}

	public Type<? super T>[] getDeclaredInterfaces() {
		return assignables.getDeclaredInterfaces();
	}
	
	public Type<? super T>[] getInterfaces(){
		return assignables.getInterfaces();
	}

	public Type<?>[] getMemberTypes(int domain){
		return memberTypes.getMemberTypes(new MemberDomainFlags(domain));
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
	
	public boolean isAnnotation() {
		return clazz.isAnnotation();
	}
	
	public boolean isAnonymous() {
		return clazz.isAnonymousClass();
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

	public String getName() {
		return clazz.getCanonicalName();
	}
	
	private static final HashMap<Class, String> primitiveToSpecific = new HashMap<>();
	static {
		primitiveToSpecific.put(byte.class, "B");
		primitiveToSpecific.put(char.class, "C");
		primitiveToSpecific.put(short.class, "S");
		primitiveToSpecific.put(int.class, "I");
		primitiveToSpecific.put(long.class, "J");
		primitiveToSpecific.put(double.class, "D");
		primitiveToSpecific.put(float.class, "F");
		primitiveToSpecific.put(boolean.class, "Z");
		primitiveToSpecific.put(void.class, "V");
	}
	
	public String getVMSignatureName() {
		String name = primitiveToSpecific.get(clazz);
		if(name != null) {
			return name;
		}
		if(clazz.isArray()) {
			Type compoment = getArrayComponentType();
			return "["+compoment.getVMSignatureName();
		}
		return "L"+getName().replace('.', '/')+";";
	}

	public String getSimpleName() {
		return clazz.getSimpleName();
	}

	public String getSpecificName() {
		return clazz.getName();
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

	public ParameterizedType getSuperGenericType(){
		java.lang.reflect.Type genericType = clazz.getGenericSuperclass();
		if(genericType instanceof ParameterizedType) {
			return (ParameterizedType) genericType;
		}
		return null;
	}
	
	public ParameterizedType getDeclaredGenericInterface(Class<? super T> interfaze) {
		java.lang.reflect.Type[] genericTypes = clazz.getGenericInterfaces();
		for(java.lang.reflect.Type genericType : genericTypes) {
			if(genericType instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) genericType;
				if(parameterizedType.getRawType() == interfaze) {
					return parameterizedType;
				}
			}
		}
		return null;
	}
	
	@Override
	public TypeVariable<Class<T>>[] getGenericTypeParameters(){
		return clazz.getTypeParameters();
	}
	
	public Type<? super T>[] getAssignableTypes(){
		return assignables.getExportTypes();
	}

	public Type<? super T>[] getAssignableSuperTypes() {
		return assignables.getExportSuperTypes();
	}

	public Type<?> getEnclosingType(){
		return Types.typeOf(clazz.getEnclosingClass());
	}
	
	public ConstructorInfo<?> getEnclosingConstructor(){
		return Types.asConstructorInfo(clazz.getEnclosingConstructor());
	}
	
	public T[] getEnumConstants() {
		return clazz.getEnumConstants();
	}
	
	public Package getPackage() {
		return clazz.getPackage();
	}
	
	public int getModifiers() {
		return clazz.getModifiers();
	}
	
	@Override
	public String toString() {
		return clazz.getCanonicalName();
	}
	
	@Override
	public boolean equals(Object other) {
		return this == other;
	}
	
	@Override
	public int hashCode() {
		return this.clazz.hashCode();
	}

	public InputStream readResource(String name) {
		return clazz.getResourceAsStream(name);
	}
	
}
