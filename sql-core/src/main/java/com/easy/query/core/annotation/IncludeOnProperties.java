package com.easy.query.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2026/6/18 13:41
 * 文件说明
 *
 * @author xuejiaming
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IncludeOnProperties {

    IncludeOnProperty[] value();

}