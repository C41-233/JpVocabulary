package c41.xml.simple.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 只能标记在基本类型上，用以表示仅读取文本
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface XmlText {

}
