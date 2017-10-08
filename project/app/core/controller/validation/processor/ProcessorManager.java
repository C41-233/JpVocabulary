package core.controller.validation.processor;

import java.lang.annotation.Annotation;

import base.utility.Strings;
import base.utility.collection.PairHashMap;
import core.controller.validation.IValidationProcessor;
import core.controller.validation.ValidationFailException;
import core.controller.validation.annotation.Id;
import core.controller.validation.annotation.Required;

public final class ProcessorManager {

	private final PairHashMap<Class<? extends Annotation>, Class, IValidationProcessor> cache = new PairHashMap<>();
	
	private <TAnnotation extends Annotation, TValue> 
	void init(Class<TAnnotation> annotationType, Class<TValue> parameterType, IValidationProcessor<TAnnotation, ? super TValue> processor) {
		cache.put(annotationType, parameterType, processor);
	}
	
	public ProcessorManager() {
		init(Id.class, Long.class, new IdProcessor());
		init(Required.class, String.class, new RequiredProcessor());
	}
	
	public Object process(Class annotationType, Class parameterType, Annotation annotation, Object arg, String raw) throws ValidationFailException {
		IValidationProcessor processor = cache.get(annotationType, parameterType);
		if(processor == null) {
			throw new RuntimeException(Strings.format("不存在对应的参数处理"));
		}
		return processor.process(annotation, arg, raw);
	}

	
	
}
