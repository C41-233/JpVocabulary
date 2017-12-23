package base.reflect;

import java.lang.annotation.Annotation;

public interface IAnnotatedReflectElement {
	
	public <TAnnotation extends Annotation> TAnnotation getAnnotation(Class<TAnnotation> cl);

	public default <TAnnotation extends Annotation> TAnnotation getAnnotation(Type<TAnnotation> type) {
		return getAnnotation(type.clazz);
	}
	
	public Annotation[] getAnnotations();

	public <TAnnotation extends Annotation> TAnnotation[] getAnnotations(Class<TAnnotation> cl);

	public default <TAnnotation extends Annotation> TAnnotation[] getAnnotations(Type<TAnnotation> type) {
		return getAnnotations(type.clazz);
	}
	
	public Annotation[] getDeclaredAnnotations();
	
	public <TAnnotation extends Annotation> boolean hasAnnotation(Class<TAnnotation> cl);

	public default <TAnnotation extends Annotation> boolean hasAnnotation(Type<TAnnotation> type) {
		return hasAnnotation(type.clazz);
	}
}