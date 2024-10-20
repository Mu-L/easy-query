package com.easy.query.api4kt.update.abstraction;

import com.easy.query.api4kt.update.KtEntityUpdatable;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.expression.sql.builder.internal.EasyBehavior;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:06
 */
public abstract class AbstractKtEntityUpdatable<T> implements KtEntityUpdatable<T> {

    protected final ClientEntityUpdatable<T> entityObjectUpdatable;

    public AbstractKtEntityUpdatable(ClientEntityUpdatable<T> entityObjectUpdatable) {
        this.entityObjectUpdatable = entityObjectUpdatable;
    }

    @Override
    public EntityUpdateExpressionBuilder getUpdateExpressionBuilder() {
        return entityObjectUpdatable.getUpdateExpressionBuilder();
    }

    @Override
    public ClientEntityUpdatable<T> getClientUpdate() {
        return entityObjectUpdatable;
    }

    @Override
    public long executeRows() {
        return entityObjectUpdatable.executeRows();
    }

    @Override
    public KtEntityUpdatable<T> asTable(Function<String, String> tableNameAs) {
        entityObjectUpdatable.asTable(tableNameAs);
        return this;
    }

    @Override
    public KtEntityUpdatable<T> asSchema(Function<String, String> schemaAs) {
        entityObjectUpdatable.asSchema(schemaAs);
        return this;
    }

    @Override
    public KtEntityUpdatable<T> asAlias(String alias) {
        entityObjectUpdatable.asAlias(alias);
        return this;
    }

    @Override
    public KtEntityUpdatable<T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        if (condition) {
            entityObjectUpdatable.setSQLStrategy(sqlStrategy);
        }
        return this;
    }

    @Override
    public KtEntityUpdatable<T> noInterceptor() {
        entityObjectUpdatable.noInterceptor();
        return this;
    }

    @Override
    public KtEntityUpdatable<T> useInterceptor(String name) {
        entityObjectUpdatable.useInterceptor(name);
        return this;
    }

    @Override
    public KtEntityUpdatable<T> noInterceptor(String name) {
        entityObjectUpdatable.noInterceptor(name);
        return this;
    }

    @Override
    public KtEntityUpdatable<T> useInterceptor() {
        entityObjectUpdatable.useInterceptor();
        return this;
    }

    @Override
    public KtEntityUpdatable<T> useLogicDelete(boolean enable) {
        entityObjectUpdatable.useLogicDelete(enable);
        return this;
    }

    @Override
    public KtEntityUpdatable<T> ignoreVersion(boolean ignored) {
        entityObjectUpdatable.ignoreVersion(ignored);
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        entityObjectUpdatable.executeRows(expectRows, msg, code);
    }

    @Override
    public KtEntityUpdatable<T> batch(boolean use) {
        entityObjectUpdatable.batch(use);
        return this;
    }

    @Override
    public KtEntityUpdatable<T> asTableLink(Function<String, String> linkAs) {
        entityObjectUpdatable.asTableLink(linkAs);
        return this;
    }

    @Override
    public KtEntityUpdatable<T> asTableSegment(BiFunction<String, String, String> segmentAs) {
        entityObjectUpdatable.asTableSegment(segmentAs);
        return this;
    }

    @Override
    public KtEntityUpdatable<T> configure(SQLExpression1<ContextConfigurer> configurer) {
        entityObjectUpdatable.configure(configurer);
        return this;
    }
}
