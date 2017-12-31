package base.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;

import base.core.Core;

public final class Type<T> implements IAnnotatedReflectElement, IGenericReflectElement<Class<T>>, IAccessableReflectElement{

	final Class<T> clazz;
	
	final ConstructorContainer<T> constructors;
	final FieldContainer fields;
	final AssginableTypeContainer<T> assignables;
	final MemberTypeContainer memberTypes;
	final NameContainer names;
	
	Type(Class<T> clazz) {
		this.clazz = clazz;

		this.assignables = new AssginableTypeContainer<>(this);
		this.memberTypes = new MemberTypeContainer(this);
		this.fields = new FieldContainer(this);
		this.constructors = new ConstructorContainer<>(this);
		this.names = new NameContainer(this);
	}

	public Class<T> asClass() {
		return clazz;
	}

	public ClassLoader getClassLoader() {
		return clazz.getClassLoader();
	}
	
	public Type<?> getArrayComponentType() {
		return Types.typeOf(clazz.getComponentType());
	}

	@Override
	public TypeVariable<Class<T>>[] getGenericTypeParameters(){
		return clazz.getTypeParameters();
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
		return getName();
	}
	
	@Override
	public boolean equals(Object other) {
		return this == other;
	}
	
	@Override
	public int hashCode() {
		return this.clazz.hashCode();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Attribute
	////////////////////////////////////////////////////////////////////////////////////////////////////////

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

	public boolean isEnum() {
		return clazz.isEnum();
	}

	public boolean isPrimitive() {
		return clazz.isPrimitive();
	}
	
	public boolean isSynthetic() {
		return clazz.isSynthetic();
	}
	
	public boolean isAnonymousType() {
		return clazz.isAnonymousClass();
	}
	
	public boolean isMemberType() {
		return clazz.isMemberClass();
	}
	
	public boolean isLocalType() {
		return clazz.isLocalClass();
	}
	
	@Override
	public boolean isPublic() {
		return Modifiers.isPublic(clazz);
	}
	
	@Override
	public boolean isProtected() {
		return Modifiers.isProtected(clazz);
	}
	
	@Override
	public boolean isPrivate() {
		return Modifiers.isPrivate(clazz);
	}
	
	@Override
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
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Annotation
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public <TAnnotation extends Annotation> TAnnotation getAnnotation(Class<TAnnotation> cl) {
		return clazz.getAnnotation(cl);
	}
	
	public <TAnnotation extends Annotation> TAnnotation getDeclaredAnnotation(Class<TAnnotation> cl) {
		return clazz.getDeclaredAnnotation(cl);
	}

	public <TAnnotation extends Annotation> TAnnotation getDeclaredAnnotation(Type<TAnnotation> type) {
		return clazz.getDeclaredAnnotation(type.clazz);
	}

	@Override
	public Annotation[] getAnnotations() {
		return clazz.getAnnotations();
	}

	public Annotation[] getDeclaredAnnotations() {
		return clazz.getDeclaredAnnotations();
	}
	
	@Override
	public <TAnnotation extends Annotation> TAnnotation[] getAnnotations(Class<TAnnotation> cl) {
		return clazz.getAnnotationsByType(cl);
	}

	public <TAnnotation extends Annotation> TAnnotation[] getDeclaredAnnotations(Class<TAnnotation> cl) {
		return clazz.getDeclaredAnnotationsByType(cl);
	}

	public <TAnnotation extends Annotation> TAnnotation[] getDeclaredAnnotations(Type<TAnnotation> type) {
		return clazz.getDeclaredAnnotationsByType(type.clazz);
	}
	
	@Override
	public <TAnnotation extends Annotation> boolean hasAnnotation(Class<TAnnotation> cl) {
		return clazz.getAnnotation(cl) != null;
	}

	public <TAnnotation extends Annotation> boolean hasDeclaredAnnotation(Class<TAnnotation> cl) {
		return clazz.getDeclaredAnnotation(cl) != null;
	}

	public <TAnnotation extends Annotation> boolean hasDeclaredAnnotation(Type<TAnnotation> type) {
		return clazz.getDeclaredAnnotation(type.clazz) != null;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Super & Interface
	////////////////////////////////////////////////////////////////////////////////////////////////////////

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
	
	public boolean isAssignableFrom(Class<?> cls) {
		return clazz.isAssignableFrom(cls);
	}
	
	public boolean isAssignableFrom(Type<?> type) {
		return clazz.isAssignableFrom(type.clazz);
	}
	
	public boolean isInstance(Object object) {
		return clazz.isInstance(object);
	}

	public Type<? super T>[] getDeclaredInterfaces() {
		return assignables.getDeclaredInterfaces();
	}
	
	public Type<? super T>[] getInterfaces(){
		return assignables.getInterfaces();
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

	public Type<? super T>[] getAssignableTypes(){
		return assignables.getExportTypes();
	}

	public Type<? super T>[] getAssignableSuperTypes() {
		return assignables.getExportSuperTypes();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Member Type
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Type<?>[] getDeclaredMemberTypes(){
		return memberTypes.getDeclaredMemberTypes();
	}
	
	public Type<?>[] getMemberTypes(){
		return memberTypes.getMemberTypes();
	}

	public Type<?> getEnclosingType(){
		return Types.typeOf(clazz.getEnclosingClass());
	}
	
	public ConstructorInfo<?> getEnclosingConstructor(){
		return Types.asConstructorInfo(clazz.getEnclosingConstructor());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Name
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public String getName() {
		return names.getName();
	}
	
	public String getVMSignatureName() {
		return names.getVMSignatureName();
	}

	public String getSimpleName() {
		return names.getSimpleName();
	}

	public String getGenericName() {
		return names.getGenericName();
	}
	
	public String getGenericSimpleName() {
		return names.getGenericSimpleName();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Field
	////////////////////////////////////////////////////////////////////////////////////////////////////////

	public FieldInfo getDeclaredField(String name) {
		return fields.getDeclaredField(name);
	}
	
	public FieldInfo[] getDeclaredFields() {
		return fields.getDeclaredFields();
	}
	
	public FieldInfo getField(String name) {
		return fields.getField(name);
	}
	
	public FieldInfo[] getFields() {
		return fields.getFields();
	}

	public void setFieldValue(T object, String name, Object value) {
		FieldInfo field = fields.getField(name);
		if(field == null) {
			throw Core.throwException(new NoSuchFieldException());
		}
		field.setValue(object, value);
	}

	
	public void setStaticFieldValue(String name, Object value) {
		FieldInfo field = fields.getField(name);
		if(field == null) {
			throw Core.throwException(new NoSuchFieldException());
		}
		field.setStaticValue(value);
	}

	
	public <V> V getFieldValue(T object, String name) {
		FieldInfo field = fields.getField(name);
		if(field == null) {
			throw Core.throwException(new NoSuchFieldException());
		}
		return field.getValue(object);
	}


	public <V> V getStaticFieldValue(String name) {
		FieldInfo field = fields.getField(name);
		if(field == null) {
			throw Core.throwException(new NoSuchFieldException());
		}
		return field.getStaticValue();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Constructor
	////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ConstructorInfo<T> getConstructor(Class<?>...parameterTypes){
		return constructors.getConstructor(parameterTypes);
	}
	
	public ConstructorInfo<T>[] getConstructors(){
		return constructors.getConstructors();
	}
	
	public T newInstance(Object...args){
		return constructors.newInstance(args);
	}
	
}
