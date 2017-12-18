package core.controller.validation;

import java.lang.annotation.Annotation;

import base.reflect.Types;
import base.utility.collection.map.PairHashMap;
import core.controller.validation.annotation.Array;
import core.controller.validation.annotation.Id;
import core.controller.validation.annotation.Required;
import core.controller.validation.annotation.StringValue;
import core.controller.validation.processor.IdProcessor;
import core.controller.validation.processor.RequiredArrayProcessor;
import core.controller.validation.processor.RequiredProcessor;
import core.controller.validation.processor.StringArrayProcessor;
import core.controller.validation.processor.StringValueArrayProcessor;
import core.controller.validation.processor.StringValueProcessor;

public final class ProcessorManager {

	private final PairHashMap<Class<? extends Annotation>, Class, IValidationProcessor> cache = new PairHashMap<>();
	
	private <TAnnotation extends Annotation, TValue> 
	void init(Class<TAnnotation> annotationType, Class<TValue> parameterType, IValidationProcessor<TAnnotation, ? super TValue> processor) {
		cache.put(annotationType, Types.toBoxClass(parameterType), processor);
	}
	
	public ProcessorManager() {
		init(Id.class, long.class, new IdProcessor());

		init(Required.class, long.class, new RequiredProcessor());
		init(Required.class, boolean.class, new RequiredProcessor());
		init(Required.class, String.class, new RequiredProcessor());
		init(Required.class, String[].class, new RequiredArrayProcessor());
		
		init(StringValue.class, String.class, new StringValueProcessor());
		init(StringValue.class, String[].class, new StringValueArrayProcessor());
		
		init(Array.class, String[].class, new StringArrayProcessor());
	}
	
	@SuppressWarnings("unchecked")
	public Object process(Class annotationType, Class parameterType, Annotation annotation, Object arg, String[] raw) throws ValidationFailException {
		IValidationProcessor<Annotation, Object> processor = cache.get(annotationType, parameterType);
		if(processor == null) {
			throw new NoSuchValidationException(String.format("%s %s", annotationType.getSimpleName(), parameterType.getSimpleName()));
		}
		return processor.process(annotation, arg, raw);
	}

	
	
}
