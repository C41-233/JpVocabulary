package base.xml;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 表示仅读取子标签，不读取属性
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface XmlTag {

	public String value() default "";
	
}
