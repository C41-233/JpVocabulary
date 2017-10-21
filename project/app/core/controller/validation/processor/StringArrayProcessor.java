package core.controller.validation.processor;

import base.utility.linq.Linq;
import core.controller.validation.IValidationProcessor;
import core.controller.validation.ValidationFailException;
import core.controller.validation.annotation.Array;

public class StringArrayProcessor implements IValidationProcessor<Array, String[]>{

	@Override
	public String[] process(Array annotation, String[] value, String[] args) throws ValidationFailException {
		if(value == null) {
			return new String[0];
		}
		
		if(annotation.duplicate() == false && Linq.from(value).hasDuplicate()) {
			throw new ValidationFailException("存在重复的元素："+Linq.from(value).findFirstDuplicate());
		}
		
		return value;
	}

}
