package core.controller.validation;

public @interface Length {

	int min() default 0;

	int max() default Integer.MAX_VALUE;
	
}
