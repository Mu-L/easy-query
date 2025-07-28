package com.easy.query.core.proxy.columns.impl;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.impl.EasyClientQueryable;
import com.easy.query.core.expression.DefaultRelationTableKey;
import com.easy.query.core.expression.ImplicitGroupRelationTableKey;
import com.easy.query.core.expression.ManyConfiguration;
import com.easy.query.core.expression.RelationTableKey;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.columns.SQLManyQueryable;
import com.easy.query.core.proxy.columns.SQLQueryable;
import com.easy.query.core.proxy.columns.SubQueryContext;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.ColumnNumberFunctionAvailable;
import com.easy.query.core.proxy.extension.functions.type.AnyTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.BooleanTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.StringTypeExpression;
import com.easy.query.core.proxy.sql.Include;
import com.easy.query.core.util.EasyObjectUtil;

import java.math.BigDecimal;
import java.util.Map;

/**
 * create time 2025/3/12 11:54
 * 文件说明
 *
 * @author xuejiaming
 */
public class PropertySQLManyQueryable<TProxy, T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> implements SQLManyQueryable<TProxy, T1Proxy, T1> {
    private final SubQueryContext<T1Proxy, T1> subQueryContext;

    public PropertySQLManyQueryable(SubQueryContext<T1Proxy, T1> subQueryContext) {
        this.subQueryContext = subQueryContext;
    }

    @Override
    public SubQueryContext<T1Proxy, T1> getSubQueryContext() {
        return subQueryContext;
    }

    @Override
    public String getNavValue() {
        return subQueryContext.getFullName();
    }

    @Override
    public String getValue() {
        return subQueryContext.getProperty();
    }

    @Override
    public T1Proxy getProxy() {
        return subQueryContext.getPropertyProxy();
    }

    @Override
    public TableAvailable getOriginalTable() {
        return subQueryContext.getLeftTable();
    }

    @Override
    public SQLQueryable<T1Proxy, T1> distinct(boolean useDistinct) {
        subQueryContext.distinct(useDistinct);
        return this;
    }

    @Override
    public SQLManyQueryable<TProxy, T1Proxy, T1> orderBy(boolean condition, SQLActionExpression1<T1Proxy> orderExpression) {
        if (condition) {
            subQueryContext.appendOrderByExpression(orderExpression);
        }
        return this;
    }

    @Override
    public T1Proxy element(int index) {
        return DefaultSubquerySQLQueryableFactory.INSTANCE.create(subQueryContext, index);
//        SQLQueryable<T1Proxy, T1> sqlQueryable = DefaultSubQuerySQLQueryableFactory.INSTANCE.create(subQueryContext);
//        return sqlQueryable.element(index);
    }

    @Override
    public SQLQueryable<T1Proxy, T1> elements(boolean condition, int fromIndex, int toIndex) {
        if (condition) {
            subQueryContext.elements(fromIndex, toIndex);
            SQLQueryable<T1Proxy, T1> sqlQueryable = DefaultSubquerySQLQueryableFactory.INSTANCE.create(subQueryContext);
            return sqlQueryable;
        }
        return this;
    }
    //
//    @Override
//    public SQLQueryable<T1Proxy, T1> elements(int begin, int end) {
//        return null;
//    }


    @Override
    public SQLManyQueryable<TProxy, T1Proxy, T1> useLogicDelete(boolean enable) {
        subQueryContext.appendConfigureExpression(s -> s.useLogicDelete(enable));
        return this;
    }

    @Override
    public SQLManyQueryable<TProxy, T1Proxy, T1> where(SQLActionExpression1<T1Proxy> whereExpression) {
        subQueryContext.appendWhereExpression(whereExpression);
        return this;
    }

    @Override
    public void filter(SQLActionExpression1<T1Proxy> whereExpression) {
        DefaultRelationTableKey defaultRelationTableKey = new DefaultRelationTableKey(subQueryContext.getLeftTable(), subQueryContext.getProperty());
//        new EasyClientQueryable<>(clazz, entityQueryExpressionBuilder)
        subQueryContext.getEntityExpressionBuilder().putManyConfiguration(defaultRelationTableKey, new ManyConfiguration(cq -> {
            ClientQueryable<T1> innerClientQueryable = EasyObjectUtil.typeCastNotNull(cq);
            T1Proxy propertyProxy = subQueryContext.getPropertyProxy();
            EasyEntityQueryable<T1Proxy, T1> entityQueryable = new EasyEntityQueryable<>(propertyProxy, innerClientQueryable);
            entityQueryable.where(whereExpression);
            return entityQueryable.getClientQueryable();
        }));

    }

    @Override
    public void any(SQLActionExpression1<T1Proxy> whereExpression) {
        subQueryContext.appendWhereExpression(whereExpression);
        SQLQueryable<T1Proxy, T1> sqlQueryable = DefaultSubquerySQLQueryableFactory.INSTANCE.create(subQueryContext);
        sqlQueryable.any();
    }

    @Override
    public void any() {
        SQLQueryable<T1Proxy, T1> sqlQueryable = DefaultSubquerySQLQueryableFactory.INSTANCE.create(subQueryContext);
        sqlQueryable.any();
    }

    @Override
    public void none(SQLActionExpression1<T1Proxy> whereExpression) {
        subQueryContext.appendWhereExpression(whereExpression);
        SQLQueryable<T1Proxy, T1> sqlQueryable = DefaultSubquerySQLQueryableFactory.INSTANCE.create(subQueryContext);
        sqlQueryable.none();
    }

    @Override
    public void none() {
        SQLQueryable<T1Proxy, T1> sqlQueryable = DefaultSubquerySQLQueryableFactory.INSTANCE.create(subQueryContext);
        sqlQueryable.none();
    }

    @Override
    public BooleanTypeExpression<Boolean> anyValue() {
        SQLQueryable<T1Proxy, T1> sqlQueryable = DefaultSubquerySQLQueryableFactory.INSTANCE.create(subQueryContext);
        return sqlQueryable.anyValue();
    }

    @Override
    public BooleanTypeExpression<Boolean> noneValue() {
        SQLQueryable<T1Proxy, T1> sqlQueryable = DefaultSubquerySQLQueryableFactory.INSTANCE.create(subQueryContext);
        return sqlQueryable.noneValue();
    }

    @Override
    public <TMember> NumberTypeExpression<Long> count(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        SQLQueryable<T1Proxy, T1> sqlQueryable = DefaultSubquerySQLQueryableFactory.INSTANCE.create(subQueryContext);
        return sqlQueryable.count(columnSelector);
    }

    @Override
    public NumberTypeExpression<Long> count() {
        SQLQueryable<T1Proxy, T1> sqlQueryable = DefaultSubquerySQLQueryableFactory.INSTANCE.create(subQueryContext);
        return sqlQueryable.count();
    }

    @Override
    public <TMember> NumberTypeExpression<Integer> intCount(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        SQLQueryable<T1Proxy, T1> sqlQueryable = DefaultSubquerySQLQueryableFactory.INSTANCE.create(subQueryContext);
        return sqlQueryable.intCount(columnSelector);
    }

    @Override
    public NumberTypeExpression<Integer> intCount() {
        SQLQueryable<T1Proxy, T1> sqlQueryable = DefaultSubquerySQLQueryableFactory.INSTANCE.create(subQueryContext);
        return sqlQueryable.intCount();
    }

    @Override
    public <TMember extends Number> NumberTypeExpression<TMember> sum(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        SQLQueryable<T1Proxy, T1> sqlQueryable = DefaultSubquerySQLQueryableFactory.INSTANCE.create(subQueryContext);
        return sqlQueryable.sum(columnSelector);
    }

    @Override
    public <TMember extends Number> NumberTypeExpression<BigDecimal> sumBigDecimal(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        SQLQueryable<T1Proxy, T1> sqlQueryable = DefaultSubquerySQLQueryableFactory.INSTANCE.create(subQueryContext);
        return sqlQueryable.sumBigDecimal(columnSelector);
    }

    @Override
    public <TMember extends Number> NumberTypeExpression<BigDecimal> avg(SQLFuncExpression1<T1Proxy, ColumnNumberFunctionAvailable<TMember>> columnSelector) {
        SQLQueryable<T1Proxy, T1> sqlQueryable = DefaultSubquerySQLQueryableFactory.INSTANCE.create(subQueryContext);
        return sqlQueryable.avg(columnSelector);
    }

    @Override
    public <TMember> AnyTypeExpression<TMember> max(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        SQLQueryable<T1Proxy, T1> sqlQueryable = DefaultSubquerySQLQueryableFactory.INSTANCE.create(subQueryContext);
        return sqlQueryable.max(columnSelector);
    }

    @Override
    public <TMember> AnyTypeExpression<TMember> min(SQLFuncExpression1<T1Proxy, PropTypeColumn<TMember>> columnSelector) {
        SQLQueryable<T1Proxy, T1> sqlQueryable = DefaultSubquerySQLQueryableFactory.INSTANCE.create(subQueryContext);
        return sqlQueryable.min(columnSelector);
    }

    @Override
    public StringTypeExpression<String> joining(SQLFuncExpression1<T1Proxy, PropTypeColumn<String>> columnSelector, String delimiter) {
        SQLQueryable<T1Proxy, T1> sqlQueryable = DefaultSubquerySQLQueryableFactory.INSTANCE.create(subQueryContext);
        return sqlQueryable.joining(columnSelector, delimiter);
    }

    @Override
    public T1Proxy flatElement(SQLFuncExpression1<T1Proxy, SQLSelectAsExpression> flatAdapterExpression) {
//        if(subQueryContext.getLeftTable() instanceof EmptyTableAvailable){
//            T1Proxy tPropertyProxy = getProxy().create(getProxy().getTable(), EmptyFlatEntitySQLContext.DEFAULT);
//            tPropertyProxy.setNavValue(getNavValue());
//            return tPropertyProxy;
//        }
        SQLQueryable<T1Proxy, T1> sqlQueryable = DefaultSubquerySQLQueryableFactory.INSTANCE.create(subQueryContext);
        return sqlQueryable.flatElement(flatAdapterExpression);
    }

    @Override
    public EntitySQLContext getEntitySQLContext() {
        return subQueryContext.getEntitySQLContext();
    }

}
