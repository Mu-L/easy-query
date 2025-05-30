package com.easy.query.core.basic.api.delete.impl;

import com.easy.query.core.basic.api.delete.abstraction.AbstractClientEntityDeletable;
import com.easy.query.core.expression.sql.builder.EntityDeleteExpressionBuilder;

import java.util.Collection;

/**
 * @author xuejiaming
 * @FileName: EasyEntityDeletable.java
 * @Description: 文件说明
 * create time 2023/3/6 13:20
 */
public class EasyClientEntityDeletable<T> extends AbstractClientEntityDeletable<T> {
    public EasyClientEntityDeletable(Class<T> clazz,Collection<T> entities, EntityDeleteExpressionBuilder entityDeleteExpression) {
        super(clazz,entities, entityDeleteExpression);
    }
}
