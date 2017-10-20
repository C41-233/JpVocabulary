package core.controller.validation.processor;

import core.controller.validation.IValidationProcessor;
import core.controller.validation.ValidationFailException;
import core.controller.validation.annotation.Array;

public class StringArrayProcessor implements IValidationProcessor<Array, String[]>{

	@Override
	public String[] process(Array annotation, String[] value, String[] args) throws ValidationFailException {
		if(value == null) {
			return new String[0];
		}
		return value;
	}

}
