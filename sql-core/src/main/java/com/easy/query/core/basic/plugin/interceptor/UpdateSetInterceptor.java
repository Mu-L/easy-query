package com.easy.query.core.basic.plugin.interceptor;

import com.easy.query.core.expression.parser.core.SQLColumnSetter;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;

/**
 * @FileName: EasyUpdateSetInterceptor.java
 * @author xuejiaming
 */
public interface UpdateSetInterceptor extends Interceptor {
    void configure(Class<?> entityClass, EntityUpdateExpressionBuilder entityUpdateExpressionBuilder, SQLColumnSetter<Object> sqlColumnSetter);
}