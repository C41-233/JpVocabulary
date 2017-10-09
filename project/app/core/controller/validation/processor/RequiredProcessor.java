package core.controller.validation.processor;

import core.controller.validation.ISimpleValidationProcessor;
import core.controller.validation.ValidationFailException;
import core.controller.validation.annotation.Required;

public class RequiredProcessor implements ISimpleValidationProcessor<Required, Object>{

	@Override
	public Object process(Required annotation, Object value, String arg) throws ValidationFailException {
		if(arg == null) {
			throw new ValidationFailException("参数缺失");
		}
		return value;
	}

}
