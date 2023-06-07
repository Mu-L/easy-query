package com.easy.query.core.basic.api.update.impl;

import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;

import java.util.function.Function;

/**
 * create time 2023/6/2 12:54
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyEmptyClientEntityUpdatable<T> implements ClientEntityUpdatable<T> {
    @Override
    public ClientEntityUpdatable<T> noInterceptor() {
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> useInterceptor(String name) {
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> noInterceptor(String name) {
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> useInterceptor() {
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> useLogicDelete(boolean enable) {
        return this;
    }

    @Override
    public void executeRows(long expectRows, String msg, String code) {
        long rows = executeRows();
        if (rows != expectRows) {
            throw new EasyQueryConcurrentException(msg, code);
        }

    }

    @Override
    public long executeRows() {
        return 0;
    }

    @Override
    public ClientEntityUpdatable<T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> asTable(Function<String, String> tableNameAs) {
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> asSchema(Function<String, String> schemaAs) {
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> asAlias(String alias) {
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> setColumns(boolean condition, SQLExpression1<ColumnSelector<T>> columnSelectorExpression) {
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> setIgnoreColumns(boolean condition, SQLExpression1<ColumnSelector<T>> columnSelectorExpression) {
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> whereColumns(boolean condition, SQLExpression1<ColumnSelector<T>> columnSelectorExpression) {
        return this;
    }

    @Override
    public String toSQL(Object entity) {
        return null;
    }
}