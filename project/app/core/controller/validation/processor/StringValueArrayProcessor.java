package core.controller.validation.processor;

import core.controller.validation.IValidationProcessor;
import core.controller.validation.ValidationFailException;
import core.controller.validation.annotation.StringValue;

public class StringValueArrayProcessor implements IValidationProcessor<StringValue, String[]>{

	@Override
	public String[] process(StringValue annotation, String[] value, String[] args) throws ValidationFailException {
		if(value == null) {
			return value;
		}
		
		StringValueProxy proxy = new StringValueProxy(annotation);
		for(int i=0; i<value.length; i++) {
			value[i] = proxy.process(annotation, value[i]);
		}
		return value;
	}

}
