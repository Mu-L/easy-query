package com.easy.query.api4j.update.abstraction;

import com.easy.query.api4j.update.EntityUpdatable;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;

import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: AbstractUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:06
 */
public abstract class AbstractEntityUpdatable<T> implements EntityUpdatable<T> {

    protected final ClientEntityUpdatable<T> entityObjectUpdatable;

    public AbstractEntityUpdatable(ClientEntityUpdatable<T> entityObjectUpdatable) {
        this.entityObjectUpdatable = entityObjectUpdatable;
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
    public EntityUpdatable<T> asTable(Function<String, String> tableNameAs) {
        entityObjectUpdatable.asTable(tableNameAs);
        return this;
    }

    @Override
    public EntityUpdatable<T> asSchema(Function<String, String> schemaAs) {
        entityObjectUpdatable.asSchema(schemaAs);
        return this;
    }

    @Override
    public EntityUpdatable<T> asAlias(String alias) {
        entityObjectUpdatable.asAlias(alias);
        return this;
    }

    @Override
    public EntityUpdatable<T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        if (condition) {
            entityObjectUpdatable.setSQLStrategy(sqlStrategy);
        }
        return this;
    }

    @Override
    public EntityUpdatable<T> noInterceptor() {
        entityObjectUpdatable.noInterceptor();
        return this;
    }

    @Override
    public EntityUpdatable<T> useInterceptor(String name) {
        entityObjectUpdatable.useInterceptor(name);
        return this;
    }

    @Override
    public EntityUpdatable<T> noInterceptor(String name) {
        entityObjectUpdatable.noInterceptor(name);
        return this;
    }

    @Override
    public EntityUpdatable<T> useInterceptor() {
        entityObjectUpdatable.useInterceptor();
        return this;
    }

    @Override
    public EntityUpdatable<T> useLogicDelete(boolean enable) {
        entityObjectUpdatable.useLogicDelete(enable);
        return this;
    }

    @Override
    public EntityUpdatable<T> noVersionError() {
        entityObjectUpdatable.noVersionError();
        return this;
    }

    @Override
    public EntityUpdatable<T> noVersionIgnore() {
        entityObjectUpdatable.noVersionIgnore();
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        entityObjectUpdatable.executeRows(expectRows, msg, code);
    }

    @Override
    public EntityUpdatable<T> batch(boolean use) {
        entityObjectUpdatable.batch(use);
        return this;
    }
}
