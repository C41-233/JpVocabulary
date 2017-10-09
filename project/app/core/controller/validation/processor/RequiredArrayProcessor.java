package core.controller.validation.processor;

import core.controller.validation.IValidationProcessor;
import core.controller.validation.ValidationFailException;
import core.controller.validation.annotation.Required;

public class RequiredArrayProcessor implements IValidationProcessor<Required, Object[]>{

	@Override
	public Object[] process(Required annotation, Object[] value, String[] args) throws ValidationFailException {
		if(args == null || args.length == 0) {
			throw new ValidationFailException("参数缺失");
		}
		return value;
	}

}
