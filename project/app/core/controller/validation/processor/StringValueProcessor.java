package core.controller.validation.processor;

import core.controller.validation.ISimpleValidationProcessor;
import core.controller.validation.ValidationFailException;
import core.controller.validation.annotation.StringValue;

public class StringValueProcessor extends StringValueProcessorBase implements ISimpleValidationProcessor<StringValue, String>{

	@Override
	public String process(StringValue annotation, String value, String arg) throws ValidationFailException {
		if(value == null) {
			return value;
		}
		return process(annotation, value);
	}

}
