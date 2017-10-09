package core.controller.validation;

import java.lang.annotation.Annotation;

public interface ISimpleValidationProcessor<TAnnotation extends Annotation, TValue> extends IValidationProcessor<TAnnotation, TValue> {

	@Override
	public default TValue process(TAnnotation annotation, TValue value, String[] args) throws ValidationFailException{
		if(args == null || args.length == 0) {
			return process(annotation, value, (String) null);
		}
		if(args.length != 1) {
			throw new ValidationFailException("参数不能有多个");
		}
		return process(annotation, value, args[0]);
	}
	
	public TValue process(TAnnotation annotation, TValue value, String args) throws ValidationFailException;
	
}
