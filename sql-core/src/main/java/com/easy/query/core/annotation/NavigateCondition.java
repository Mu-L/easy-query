package com.easy.query.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2024/10/18 15:25
 * 文件说明
 *
 * @author xuejiaming
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NavigateCondition {
    String property();
    String havingValue();

    boolean matchIfMissing() default false;
}
