package c41.xml;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 只能标记在基本类型上，表示仅读取属性
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface XmlAttribute {

	public String value() default "";
	
}
