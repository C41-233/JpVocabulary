package core.controller.validation.processor;

import core.controller.validation.IValidationProcessor;
import core.controller.validation.ValidationFailException;
import core.controller.validation.annotation.StringValue;

public class StringValueArrayProcessor extends StringValueProcessorBase implements IValidationProcessor<StringValue, String[]>{

	private final StringValueProcessor processor = new StringValueProcessor();
	
	@Override
	public String[] process(StringValue annotation, String[] value, String[] args) throws ValidationFailException {
		if(value == null) {
			return value;
		}
		for(int i=0; i<value.length; i++) {
			value[i] = process(annotation, value[i]);
		}
		return value;
	}

}
