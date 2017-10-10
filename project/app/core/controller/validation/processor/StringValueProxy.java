package core.controller.validation.processor;

import core.controller.validation.ValidationFailException;
import core.controller.validation.annotation.StringValue;

public class StringValueProxy{

	private final int length;
	private final int maxLength;
	private final int minLength;
	private final boolean trim;
	
	public StringValueProxy(StringValue annotation) {
		this.length = annotation.length();
		this.maxLength = annotation.maxLength();
		this.minLength = annotation.minLength();
		this.trim = annotation.trim();
	}
	
	public String process(StringValue annotation, String value) throws ValidationFailException {
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
