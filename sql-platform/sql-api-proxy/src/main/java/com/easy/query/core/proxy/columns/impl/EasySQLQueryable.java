package com.easy.query.core.proxy.columns.impl;

import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2024/2/11 22:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasySQLQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements SQLQueryable<T1Proxy, T1> {
    private final EntitySQLContext entitySQLContext;
    private final EasyEntityQueryable<T1Proxy, T1> easyEntityQueryable;

    public EasySQLQueryable(EntitySQLContext entitySQLContext,EasyEntityQueryable<T1Proxy, T1> easyEntityQueryable){

        this.entitySQLContext = entitySQLContext;
        this.easyEntityQueryable = easyEntityQueryable;
    }
    @Override
    public EntitySQLContext getEntitySQLContext() {
        return entitySQLContext;
    }

    @Override
    public EasyEntityQueryable<T1Proxy, T1> getQueryable() {
        return easyEntityQueryable;
    }
}