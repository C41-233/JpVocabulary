package core.controller.validation.processor;

import core.controller.validation.ISimpleValidationProcessor;
import core.controller.validation.ValidationFailException;
import core.controller.validation.annotation.Id;

public class IdProcessor implements ISimpleValidationProcessor<Id, Long>{

	@Override
	public Long process(Id annotation, Long value, String arg) throws ValidationFailException {
		if(arg == null) {
			throw new ValidationFailException("Id类型不能忽略");
		}
		if(!arg.matches("[1-9][0-9]*")) {
			throw new ValidationFailException("Id格式错误");
		}
		return value;
	}

}
