package org.jc.framework.converter.annotation;

import java.lang.annotation.*;

/**
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Properties {
    /**
     * 实现类
     *
     * @return
     */
    Class<?> impl() default Object.class;

    /**
     * 默认值
     *
     * @return
     */
    String defaultValue() default "${}";

    /**
     * 是否忽略当前属性
     *
     * @return
     */
    boolean ignore() default false;
}
