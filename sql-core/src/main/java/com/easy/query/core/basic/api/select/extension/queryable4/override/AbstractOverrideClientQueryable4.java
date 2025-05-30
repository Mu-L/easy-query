package com.easy.query.core.basic.api.select.extension.queryable4.override;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.basic.api.select.abstraction.AbstractClientQueryable;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * create time 2023/8/16 07:52
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractOverrideClientQueryable4<T1, T2, T3, T4> extends AbstractClientQueryable<T1> {
    protected final Class<T2> t2Class;
    protected final Class<T3> t3Class;
    protected final Class<T4> t4Class;

    public AbstractOverrideClientQueryable4(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, Class<T4> t4Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, sqlEntityExpression);
        this.t2Class = t2Class;
        this.t3Class = t3Class;
        this.t4Class = t4Class;
    }
    public Class<T2> queryClass2() {
        return t2Class;
    }

    public Class<T3> queryClass3() {
        return t3Class;
    }

    public Class<T4> queryClass4() {
        return t4Class;
    }
    protected abstract ClientQueryable4<T1, T2, T3, T4> getClientQueryable4();


    @NotNull
    @Override
    public ClientQueryable4<T1, T2, T3, T4> cloneQueryable() {
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().cloneQueryable(getClientQueryable4());
    }
    @Override
    public ClientQueryable4<T1, T2, T3, T4> whereById(boolean condition, Object id) {
        super.whereById(condition, id);
        return getClientQueryable4();
    }

    @Override
    public <TProperty> ClientQueryable4<T1, T2, T3, T4> whereByIds(boolean condition, Collection<TProperty> ids) {
        super.whereByIds(condition, ids);
        return getClientQueryable4();
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> whereObject(boolean condition, Object object) {
        super.whereObject(condition, object);
        return getClientQueryable4();
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> where(boolean condition, SQLActionExpression1<WherePredicate<T1>> whereExpression) {
        super.where(condition, whereExpression);
        return getClientQueryable4();
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> orderByAsc(boolean condition, SQLActionExpression1<ColumnOrderSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getClientQueryable4();
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> orderByDesc(boolean condition, SQLActionExpression1<ColumnOrderSelector<T1>> selectExpression) {
        super.orderByAsc(condition, selectExpression);
        return getClientQueryable4();
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> groupBy(boolean condition, SQLActionExpression1<ColumnGroupSelector<T1>> selectExpression) {
        super.groupBy(condition, selectExpression);
        return getClientQueryable4();
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> having(boolean condition, SQLActionExpression1<WhereAggregatePredicate<T1>> predicateExpression) {
        super.having(condition, predicateExpression);
        return getClientQueryable4();
    }


    @Override
    public <TProperty> ClientQueryable4<T1, T2, T3, T4> include(boolean condition, SQLFuncExpression1<NavigateInclude, ClientQueryable<TProperty>> navigateIncludeSQLExpression) {
        super.include(condition, navigateIncludeSQLExpression);
        return getClientQueryable4();
    }

    @NotNull
    @Override
    public ClientQueryable4<T1, T2, T3, T4> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return getClientQueryable4();
    }

    @NotNull
    @Override
    public ClientQueryable4<T1, T2, T3, T4> distinct(boolean condition) {
        super.distinct(condition);
        return getClientQueryable4();
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> disableLogicDelete() {
        super.disableLogicDelete();
        return getClientQueryable4();
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> enableLogicDelete() {
        super.enableLogicDelete();
        return getClientQueryable4();
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> useLogicDelete(boolean enable) {
        super.useLogicDelete(enable);
        return getClientQueryable4();
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> noInterceptor() {
        super.noInterceptor();
        return getClientQueryable4();
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> useInterceptor(String name) {
        super.useInterceptor(name);
        return getClientQueryable4();
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> noInterceptor(String name) {
        super.noInterceptor(name);
        return getClientQueryable4();
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> useInterceptor() {
        super.useInterceptor();
        return getClientQueryable4();
    }

    @NotNull
    @Override
    public ClientQueryable4<T1, T2, T3, T4> asTracking() {
        super.asTracking();
        return getClientQueryable4();
    }

    @NotNull
    @Override
    public ClientQueryable4<T1, T2, T3, T4> asNoTracking() {
        super.asNoTracking();
        return getClientQueryable4();
    }

    @NotNull
    @Override
    public ClientQueryable4<T1, T2, T3, T4> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode) {
        super.useShardingConfigure(maxShardingQueryLimit, connectionMode);
        return getClientQueryable4();
    }

    @NotNull
    @Override
    public ClientQueryable4<T1, T2, T3, T4> useMaxShardingQueryLimit(int maxShardingQueryLimit) {
        super.useMaxShardingQueryLimit(maxShardingQueryLimit);
        return getClientQueryable4();
    }

    @NotNull
    @Override
    public ClientQueryable4<T1, T2, T3, T4> useConnectionMode(ConnectionModeEnum connectionMode) {
        super.useConnectionMode(connectionMode);
        return getClientQueryable4();
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> asTable(Function<String, String> tableNameAs) {
        super.asTable(tableNameAs);
        return getClientQueryable4();
    }

    @Override
    public ClientQueryable4<T1, T2, T3, T4> asSchema(Function<String, String> schemaAs) {
        super.asSchema(schemaAs);
        return getClientQueryable4();
    }


    @Override
    public ClientQueryable4<T1, T2, T3, T4> asAlias(String alias) {
        super.asAlias(alias);
        return getClientQueryable4();
    }
    @Override
    public ClientQueryable4<T1, T2, T3, T4> asTableLink(Function<String, String> linkAs) {
        super.asTableLink(linkAs);
        return getClientQueryable4();
    }
    @Override
    public ClientQueryable4<T1, T2, T3, T4> asTableSegment(BiFunction<String, String, String> segmentAs) {
        super.asTableSegment(segmentAs);
        return getClientQueryable4();
    }

    @Override
    public ClientQueryable4<T1, T2, T3,T4> filterConfigure(ValueFilter valueFilter) {
        super.filterConfigure(valueFilter);
        return getClientQueryable4();
    }
    @Override
    public ClientQueryable4<T1, T2, T3,T4> tableLogicDelete(Supplier<Boolean> tableLogicDel) {
        super.tableLogicDelete(tableLogicDel);
        return getClientQueryable4();
    }
    @Override
    public ClientQueryable4<T1, T2, T3,T4> configure(SQLActionExpression1<ContextConfigurer> configurer) {
        super.configure(configurer);
        return getClientQueryable4();
    }
}
