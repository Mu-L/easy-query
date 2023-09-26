package com.easy.query.core.basic.api.select.extension.queryable4.override;

import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable4;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.expression.builder.core.ValueFilter;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;
import com.easy.query.core.expression.parser.core.base.NavigateInclude;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

import java.util.Collection;
import java.util.function.Function;

/**
 * create time 2023/8/16 08:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ClientOverrideQueryable4<T1, T2, T3, T4> extends ClientQueryable<T1> {


    ClientQueryable<T1> getClientQueryable();
    @Override
    ClientQueryable4<T1, T2, T3,T4> cloneQueryable();

    @Override
    default ClientQueryable4<T1, T2, T3,T4> whereById(Object id) {
        return whereById(true, id);
    }

    @Override
    ClientQueryable4<T1, T2, T3,T4> whereById(boolean condition, Object id);

    @Override
    <TProperty> ClientQueryable4<T1, T2, T3,T4> whereByIds(boolean condition, Collection<TProperty> ids);

    /**
     * 仅支持主表的动态对象查询
     *
     * @param object 对象查询的对象
     * @return
     */
    @Override
    default ClientQueryable4<T1, T2, T3,T4> whereObject(Object object) {
        return whereObject(true, object);
    }

    /**
     * 仅支持主表的动态对象查询
     *
     * @param condition 是否使用对象查询
     * @param object    对象查询的对象
     * @return
     */
    @Override
    ClientQueryable4<T1, T2, T3,T4> whereObject(boolean condition, Object object);


    @Override
    default ClientQueryable4<T1, T2, T3,T4> where(SQLExpression1<WherePredicate<T1>> whereExpression) {
        return where(true, whereExpression);
    }

    @Override
    ClientQueryable4<T1, T2, T3,T4> where(boolean condition, SQLExpression1<WherePredicate<T1>> whereExpression);

    @Override
    default ClientQueryable4<T1, T2, T3,T4> groupBy(SQLExpression1<ColumnGroupSelector<T1>> selectExpression) {
        return groupBy(true, selectExpression);
    }

    @Override
    ClientQueryable4<T1, T2, T3,T4> groupBy(boolean condition, SQLExpression1<ColumnGroupSelector<T1>> selectExpression);

    @Override
    default ClientQueryable4<T1, T2, T3,T4> having(SQLExpression1<WhereAggregatePredicate<T1>> predicateExpression) {
        return having(true, predicateExpression);
    }

    @Override
    ClientQueryable4<T1, T2, T3,T4> having(boolean condition, SQLExpression1<WhereAggregatePredicate<T1>> predicateExpression);

    @Override
    default ClientQueryable4<T1, T2, T3,T4> orderByAsc(SQLExpression1<ColumnOrderSelector<T1>> selectExpression) {
        return orderByAsc(true, selectExpression);
    }

    @Override
    ClientQueryable4<T1, T2, T3,T4> orderByAsc(boolean condition, SQLExpression1<ColumnOrderSelector<T1>> selectExpression);

    @Override
    default ClientQueryable4<T1, T2, T3,T4> orderByDesc(SQLExpression1<ColumnOrderSelector<T1>> selectExpression) {
        return orderByDesc(true, selectExpression);
    }

    @Override
    ClientQueryable4<T1, T2, T3,T4> orderByDesc(boolean condition, SQLExpression1<ColumnOrderSelector<T1>> selectExpression);

    @Override
    default <TREntity> ClientQueryable4<T1, T2, T3,T4> include(SQLFuncExpression1<NavigateInclude<T1>, ClientQueryable<TREntity>> navigateIncludeSQLExpression) {
        return include(true, navigateIncludeSQLExpression);
    }

    @Override
    <TREntity> ClientQueryable4<T1, T2, T3,T4> include(boolean condition, SQLFuncExpression1<NavigateInclude<T1>, ClientQueryable<TREntity>> navigateIncludeSQLExpression);

    @Override
    default ClientQueryable4<T1, T2, T3,T4> limit(long rows) {
        return limit(true, rows);
    }

    @Override
    default ClientQueryable4<T1, T2, T3,T4> limit(boolean condition, long rows) {
        return limit(condition, 0, rows);
    }

    @Override
    default ClientQueryable4<T1, T2, T3,T4> limit(long offset, long rows) {
        return limit(true, offset, rows);
    }

    @Override
    ClientQueryable4<T1, T2, T3,T4> limit(boolean condition, long offset, long rows);

    default ClientQueryable4<T1, T2, T3,T4> distinct() {
        return distinct(true);
    }

    @Override
    ClientQueryable4<T1, T2, T3,T4> distinct(boolean condition);

    @Override
    ClientQueryable4<T1, T2, T3,T4> disableLogicDelete();

    @Override
    ClientQueryable4<T1, T2, T3,T4> enableLogicDelete();

    @Override
    ClientQueryable4<T1, T2, T3,T4> useLogicDelete(boolean enable);

    @Override
    ClientQueryable4<T1, T2, T3,T4> noInterceptor();

    @Override
    ClientQueryable4<T1, T2, T3,T4> useInterceptor(String name);

    @Override
    ClientQueryable4<T1, T2, T3,T4> noInterceptor(String name);

    @Override
    ClientQueryable4<T1, T2, T3,T4> useInterceptor();

    /**
     * 自动将查询结果集合全部添加到当前上下文追踪中,如果当前查询结果十分庞大,并且更新数据只有个别条数,建议不要使用
     * 追踪查询，可以通过开启追踪后使用普通的查询，然后添加到当前的追踪上下文中{@link EasyQueryClient#addTracking(Object)},开始先数据追踪的差异更新
     *
     * @return
     */
    @Override
    ClientQueryable4<T1, T2, T3,T4> asTracking();

    @Override
    ClientQueryable4<T1, T2, T3,T4> asNoTracking();

    @Override
    ClientQueryable4<T1, T2, T3,T4> queryLargeColumn(boolean queryLarge);

    @Override
    ClientQueryable4<T1, T2, T3,T4> useShardingConfigure(int maxShardingQueryLimit, ConnectionModeEnum connectionMode);

    @Override
    ClientQueryable4<T1, T2, T3,T4> useMaxShardingQueryLimit(int maxShardingQueryLimit);

    @Override
    ClientQueryable4<T1, T2, T3,T4> useConnectionMode(ConnectionModeEnum connectionMode);

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableName}
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableName
     * @return
     */
    @Override
    default ClientQueryable4<T1, T2, T3,T4> asTable(String tableName) {
        return asTable(old -> tableName);
    }

    /**
     * 将当前表达式最近的一张表的表名修改成 {@param tableNameAs}返回的表名
     * 如果当前最近的表是正常的数据库表名,那么直接将表名改写
     * 如果当前最近的表是匿名表比如嵌套queryable的表那么将alias改成对应的表名
     *
     * @param tableNameAs
     * @return
     */
    @Override
    ClientQueryable4<T1, T2, T3,T4> asTable(Function<String, String> tableNameAs);

    @Override
    default ClientQueryable4<T1, T2, T3,T4> asSchema(String schema) {
        return asSchema(old -> schema);
    }

    @Override
    ClientQueryable4<T1, T2, T3,T4> asSchema(Function<String, String> schemaAs);

    @Override
    ClientQueryable4<T1, T2, T3,T4> asAlias(String alias);
    /**
     * @param linkAs 别名 FROM | LEFT JOIN | RIGHT JOIN
     * @return
     */
    @Override
    default  ClientQueryable4<T1, T2, T3,T4> asTableLink(String linkAs) {
        return asTableLink(o -> linkAs);
    }

    @Override
    ClientQueryable4<T1, T2, T3,T4> asTableLink(Function<String, String> linkAs);
    @Override
    ClientQueryable4<T1, T2, T3,T4> filterConfigure(ValueFilter valueFilter);
}
