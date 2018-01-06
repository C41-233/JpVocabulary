package base.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

import base.core.ImpossibleException;

public final class ParameterInfo implements IModiferReflectElement, IAnnotatedReflectElement{

	private final Parameter parameter;
	
	ParameterInfo(Parameter parameter) {
		this.parameter = parameter;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}

	@Override
	public int hashCode() {
		return parameter.hashCode();
	}
	
	@Override
	public String toString() {
		return parameter.toString();
	}
	
	@Override
	public <TAnnotation extends Annotation> TAnnotation getAnnotation(Class<TAnnotation> cl) {
		return parameter.getAnnotation(cl);
	}

	@Override
	public Annotation[] getAnnotations() {
		return parameter.getAnnotations();
	}

	@Override
	public <TAnnotation extends Annotation> TAnnotation[] getAnnotations(Class<TAnnotation> cl) {
		return parameter.getAnnotationsByType(cl);
	}

	@Override
	public <TAnnotation extends Annotation> boolean hasAnnotation(Class<TAnnotation> cl) {
		return parameter.isAnnotationPresent(cl);
	}
	
	@SuppressWarnings("unchecked")
	public IInvokableReflectElement getDeclaringFunction() {
		Executable executable = parameter.getDeclaringExecutable();
		if(executable instanceof Constructor) {
			return ReflectCache.wrap((Constructor) executable);
		}
		if(executable instanceof Method) {
			return null;//TODO
		}
		throw new ImpossibleException();
	}

	public String getName() {
		return parameter.getName();
	}
	
	public TypeInfo<?> getType(){
		return Types.typeOf(parameter.getType());
	}
	
	@Override
	public int getModifiers() {
		return parameter.getModifiers();
	}
	
	public Type getGenericType() {
		return parameter.getParameterizedType();
	}
	
	public boolean isImplicit() {
		return parameter.isImplicit();
	}
	
	public boolean isSynthetic() {
		return parameter.isSynthetic();
	}
	
	public boolean isVarArgs() {
		return parameter.isVarArgs();
	}
	
	public boolean isFinal() {
		return Modifiers.isFinal(parameter);
	}
	
}
