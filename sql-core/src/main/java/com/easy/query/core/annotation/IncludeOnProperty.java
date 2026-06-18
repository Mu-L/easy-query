package com.easy.query.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2024/10/18 15:25
 * 文件说明
 *
 * @author xuejiaming
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(IncludeOnProperties.class)
public @interface IncludeOnProperty {
    String name();
    String value();
    boolean matchNull() default false;
}
