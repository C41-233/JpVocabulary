package core.controller.validation.processor;

import core.controller.validation.ValidationFailException;
import core.controller.validation.annotation.StringValue;

public class StringValueProcessorBase{

	public String process(StringValue annotation, String value) throws ValidationFailException {
		int length = annotation.length();
		int maxLength = annotation.maxLength();
		int minLength = annotation.minLength();
		boolean trim = annotation.trim();
		
		if(trim) {
			value = value.trim();
		}
		
		if(value.length() < minLength) {
			throw new ValidationFailException("字符串长度小于最小值"+minLength);
		}

		if(value.length() > maxLength) {
			throw new ValidationFailException("字符串长度大于最大值"+maxLength);
		}
		
		if(length > 0 && value.length() != length) {
			throw new ValidationFailException("字符串长度不等于"+length);
		}
		
		return value;
	}
	
}
