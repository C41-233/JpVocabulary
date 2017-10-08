package core.controller.validation.processor;

import core.controller.validation.IValidationProcessor;
import core.controller.validation.ValidationFailException;
import core.controller.validation.annotation.StringValue;

public class StringValueProcessor implements IValidationProcessor<StringValue, String>{

	@Override
	public String process(StringValue annotation, String value, String arg) throws ValidationFailException {
		// TODO Auto-generated method stub
		return null;
	}

}
