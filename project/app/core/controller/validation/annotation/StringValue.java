package core.controller.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface StringValue {

	int maxLength() default Integer.MAX_VALUE;
	int minLength() default 0;
	int length() default 0;
	
	String match() default "";
	
	boolean trim() default true;
	
}
