package core.controller.validation;

import java.lang.annotation.Annotation;

public interface IValidationProcessor<TAnnotation extends Annotation, TValue> {

	public TValue process(TAnnotation annotation, TValue value, String[] args) throws ValidationFailException;
	
}
