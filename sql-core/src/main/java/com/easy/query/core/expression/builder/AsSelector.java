package com.easy.query.core.expression.builder;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.func.DefaultColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

/**
 * create time 2023/6/22 20:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface AsSelector extends SQLNative<AsSelector> {
    QueryRuntimeContext getRuntimeContext();
    ExpressionContext getExpressionContext();
    EntityQueryExpressionBuilder getEntityQueryExpressionBuilder();

    AsSelector column(TableAvailable table, String property);

    /**
     * 哪张表的目标属性
     * @param table
     * @param selfProperty
     * @param aliasProperty
     * @param includeSelectorExpression
     * @return
     */
    AsSelector columnInclude(TableAvailable table, String selfProperty, String aliasProperty, SQLExpression1<AsSelector> includeSelectorExpression);

    AsSelector columnIgnore(TableAvailable table, String property);

    /**
     * 映射到TR的所有列上,按ColumnName进行映射,如果TR上没有对应的列名那么将不会映射查询列
     *
     * @return
     */
    AsSelector columnAll(TableAvailable table);

    AsSelector columnAs(TableAvailable table, String property, String propertyAlias);

    <TSubQuery> AsSelector columnSubQueryAs(SQLFuncExpression<Query<TSubQuery>> subQueryableFunc, String propertyAlias);

    default AsSelector columnCount(TableAvailable table, String property) {
        return columnCountAs(table,property, null);
    }

    default AsSelector columnCountAs(TableAvailable table, String property, String propertyAlias) {
        ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createCountFunction(false);
        ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(property, countFunction);
        return columnFuncAs(table,columnPropertyFunction, propertyAlias);
    }

    default AsSelector columnCountDistinct(TableAvailable table,String property) {
        return columnCountDistinctAs(table,property, null);
    }

    default AsSelector columnCountDistinctAs(TableAvailable table,String property, String propertyAlias) {
        ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createCountFunction(true);
        ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(property, countFunction);
        return columnFuncAs(table,columnPropertyFunction, propertyAlias);
    }

    default AsSelector columnSum(TableAvailable table,String property) {
        return columnSumAs(table,property, null);
    }

    default AsSelector columnSumAs(TableAvailable table,String property, String propertyAlias) {
        ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createSumFunction(false);
        ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(property, countFunction);
        return columnFuncAs(table,columnPropertyFunction, propertyAlias);
    }

    default AsSelector columnSumDistinct(TableAvailable table,String property) {
        return columnSumDistinctAs(table,property, null);
    }

    default AsSelector columnSumDistinctAs(TableAvailable table,String property, String propertyAlias) {
        ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createSumFunction(true);
        ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(property, countFunction);
        return columnFuncAs(table,columnPropertyFunction, propertyAlias);
    }

    default AsSelector columnMax(TableAvailable table,String property) {
        return columnMaxAs(table,property, null);
    }

    default AsSelector columnMaxAs(TableAvailable table,String property, String propertyAlias) {
        ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createMaxFunction();
        ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(property, countFunction);
        return columnFuncAs(table,columnPropertyFunction, propertyAlias);
    }

    default AsSelector columnMin(TableAvailable table,String property) {
        return columnMinAs(table,property, null);
    }

    default AsSelector columnMinAs(TableAvailable table,String property, String propertyAlias) {
        ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createMinFunction();
        ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(property, countFunction);
        return columnFuncAs(table,columnPropertyFunction, propertyAlias);
    }

    default AsSelector columnAvg(TableAvailable table,String property) {
        return columnAvgAs(table,property, null);
    }

    default AsSelector columnAvgAs(TableAvailable table,String property, String propertyAlias) {
        ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createAvgFunction(false);
        ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(property, countFunction);
        return columnFuncAs(table,columnPropertyFunction, propertyAlias);
    }

    default AsSelector columnAvgDistinct(TableAvailable table,String property) {
        return columnAvgDistinctAs(table,property, null);
    }

    default AsSelector columnAvgDistinctAs(TableAvailable table,String property, String propertyAlias) {
        ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createAvgFunction(true);
        ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(property, countFunction);
        return columnFuncAs(table,columnPropertyFunction, propertyAlias);
    }

    default AsSelector columnLen(TableAvailable table,String property) {
        return columnLenAs(table,property, null);
    }

    default AsSelector columnLenAs(TableAvailable table,String property, String propertyAlias) {
        ColumnFunction countFunction = getRuntimeContext().getColumnFunctionFactory().createLenFunction();
        ColumnPropertyFunction columnPropertyFunction = DefaultColumnPropertyFunction.createDefault(property, countFunction);
        return columnFuncAs(table,columnPropertyFunction, propertyAlias);
    }

    AsSelector columnFuncAs(TableAvailable table,ColumnPropertyFunction columnPropertyFunction, String propertyAlias);
    AsSelector sqlSegmentAs(CloneableSQLSegment sqlColumnSegment, String propertyAlias);
}
