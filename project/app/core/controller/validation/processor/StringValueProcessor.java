package core.controller.validation.processor;

import core.controller.validation.ISimpleValidationProcessor;
import core.controller.validation.ValidationFailException;
import core.controller.validation.annotation.StringValue;

public class StringValueProcessor implements ISimpleValidationProcessor<StringValue, String>{

	@Override
	public String process(StringValue annotation, String value, String arg) throws ValidationFailException {
		if(value == null) {
			return value;
		}
		
		StringValueProxy proxy = new StringValueProxy(annotation);
		return proxy.process(annotation, value);
	}

}
