package com.easy.query.core.expression.builder;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunction;

import java.util.Collection;

/**
 * create time 2023/6/22 14:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Filter extends SQLNative<Filter> {
    boolean getReverse();

    QueryRuntimeContext getRuntimeContext();

    /**
     * 大于 column > val
     *
     * @param property 字段
     * @param val      值
     * @return children
     */
    Filter gt(TableAvailable table, String property, Object val);

    /**
     * 等于 column >= val
     *
     * @param property 字段
     * @param val      值
     * @return children
     */
    Filter ge(TableAvailable table, String property, Object val);

    /**
     * 等于 column = val
     *
     * @param property 字段
     * @param val      值
     * @return children
     */
    Filter eq(TableAvailable table, String property, Object val);

    /**
     * 不等于 column <> val
     *
     * @param property 字段
     * @param val      值
     * @return children
     */
    Filter ne(TableAvailable table, String property, Object val);

    /**
     * 小于等于 column <= val
     *
     * @param property 字段
     * @param val      值
     * @return children
     */
    Filter le(TableAvailable table, String property, Object val);

    /**
     * 小于 column < val
     *
     * @param property 字段
     * @param val      值
     * @return children
     */
    Filter lt(TableAvailable table, String property, Object val);

    /**
     * column like ?val?
     * 列自定义匹配
     *
     * @param property
     * @param val
     * @param sqlLike
     * @return
     */
    Filter like(TableAvailable table, String property, Object val, SQLLikeEnum sqlLike);

    Filter notLike(TableAvailable table, String property, Object val, SQLLikeEnum sqlLike);

    /**
     * column is null
     *
     * @param property 字段
     * @return children
     */
    Filter isNull(TableAvailable table, String property);

    /**
     * column is not null
     *
     * @param property 字段
     * @return children
     */
    Filter isNotNull(TableAvailable table, String property);

    /**
     * column in collection
     * 集合为空返回False
     */
    Filter in(TableAvailable table, String property, Collection<?> collection);

    <TProperty> Filter in(TableAvailable table, String property, TProperty[] collection);


    <TProperty> Filter in(TableAvailable table, String property, Query<TProperty> subQuery);

    /**
     * column not in collection
     * 集合为空返回True
     */
    Filter notIn(TableAvailable table, String property, Collection<?> collection);

    <TProperty> Filter notIn(TableAvailable table, String property, TProperty[] collection);


    <TProperty> Filter notIn(TableAvailable table, String property, Query<TProperty> subQuery);

    <T2> Filter exists(TableAvailable table, Query<T2> subQuery);


    <T2> Filter notExists(TableAvailable table, Query<T2> subQuery);

    /**
     * 自定义范围
     * 一般用于范围比如时间,小的时间在前大的时间在后
     *
     * @param property
     * @param conditionLeft
     * @param valLeft
     * @param conditionRight
     * @param valRight
     * @param sqlRange
     * @return
     */
    Filter range(TableAvailable table, String property, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight, SQLRangeEnum sqlRange);


    Filter columnFunc(TableAvailable table, ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, Object val);

    default Filter gt(TableAvailable leftTable, String property1, TableAvailable rightTable, String property2) {
        return compareSelf(leftTable, property1, rightTable, property2, SQLPredicateCompareEnum.GT);
    }

    default Filter ge(TableAvailable leftTable, String property1, TableAvailable rightTable, String property2) {
        return compareSelf(leftTable, property1, rightTable, property2, SQLPredicateCompareEnum.GE);
    }

    default Filter eq(TableAvailable leftTable, String property1, TableAvailable rightTable, String property2) {
        return compareSelf(leftTable, property1, rightTable, property2, SQLPredicateCompareEnum.EQ);
    }

    default Filter ne(TableAvailable leftTable, String property1, TableAvailable rightTable, String property2) {
        return compareSelf(leftTable, property1, rightTable, property2, SQLPredicateCompareEnum.NE);
    }

    default Filter le(TableAvailable leftTable, String property1, TableAvailable rightTable, String property2) {
        return compareSelf(leftTable, property1, rightTable, property2, SQLPredicateCompareEnum.LE);
    }

    default Filter lt(TableAvailable leftTable, String property1, TableAvailable rightTable, String property2) {
        return compareSelf(leftTable, property1, rightTable, property2, SQLPredicateCompareEnum.LT);
    }

    /**
     * @param leftTable
     * @param property1
     * @param rightTable
     * @param property2
     * @param sqlPredicateCompare eg.SQLPredicateCompareEnum.EQ
     * @return
     */
    Filter compareSelf(TableAvailable leftTable, String property1, TableAvailable rightTable, String property2, SQLPredicateCompare sqlPredicateCompare);


    /**
     * 大于 column > val
     *
     * @param property 字段
     * @param subQuery 子查询
     * @return children
     */
    default <TProperty> Filter gt(TableAvailable table, String property, Query<TProperty> subQuery) {
        return subQueryFilter(table, property, subQuery, SQLPredicateCompareEnum.GT);
    }

    /**
     * 等于 column >= val
     *
     * @param property 字段
     * @param subQuery 子查询
     * @return children
     */
    default <TProperty> Filter ge(TableAvailable table, String property, Query<TProperty> subQuery) {
        return subQueryFilter(table, property, subQuery, SQLPredicateCompareEnum.GE);
    }

    /**
     * 等于 column = val
     *
     * @param property 字段
     * @param subQuery 子查询
     * @return children
     */
    default <TProperty> Filter eq(TableAvailable table, String property, Query<TProperty> subQuery) {
        return subQueryFilter(table, property, subQuery, SQLPredicateCompareEnum.EQ);
    }

    /**
     * 不等于 column <> val
     *
     * @param property 字段
     * @param subQuery 子查询
     * @return children
     */
    default <TProperty> Filter ne(TableAvailable table, String property, Query<TProperty> subQuery) {
        return subQueryFilter(table, property, subQuery, SQLPredicateCompareEnum.NE);
    }

    /**
     * 小于等于 column <= val
     *
     * @param property 字段
     * @param subQuery 子查询
     * @return children
     */
    default <TProperty> Filter le(TableAvailable table, String property, Query<TProperty> subQuery) {
        return subQueryFilter(table, property, subQuery, SQLPredicateCompareEnum.LE);
    }

    /**
     * 小于 column < val
     *
     * @param property 字段
     * @param subQuery 子查询
     * @return children
     */
    default <TProperty> Filter lt(TableAvailable table, String property, Query<TProperty> subQuery) {
        return subQueryFilter(table, property, subQuery, SQLPredicateCompareEnum.LT);
    }

    <TProperty> Filter subQueryFilter(TableAvailable table, String property, Query<TProperty> subQuery, SQLPredicateCompare sqlPredicateCompare);
//region func value


    /**
     * 大于 func(column) > val
     *
     * @param table
     * @param sqlFunction
     * @param val
     * @return
     */
    default Filter gt(TableAvailable table, SQLFunction sqlFunction, Object val) {
        return funcValueFilter(table, sqlFunction, val, SQLPredicateCompareEnum.GT);
    }

    /**
     * 等于 func(column) >= val
     *
     * @param table
     * @param sqlFunction
     * @param val
     * @return
     */
    default Filter ge(TableAvailable table, SQLFunction sqlFunction, Object val) {
        return funcValueFilter(table, sqlFunction, val, SQLPredicateCompareEnum.GE);
    }

    /**
     * 等于 func(column) = val
     *
     * @param table
     * @param sqlFunction
     * @param val
     * @return
     */
    default Filter eq(TableAvailable table, SQLFunction sqlFunction, Object val) {
        return funcValueFilter(table, sqlFunction, val, SQLPredicateCompareEnum.EQ);
    }

    /**
     * 不等于 func(column) <> val
     *
     * @param table
     * @param sqlFunction
     * @param val
     * @return
     */
    default Filter ne(TableAvailable table, SQLFunction sqlFunction, Object val) {
        return funcValueFilter(table, sqlFunction, val, SQLPredicateCompareEnum.NE);
    }

    /**
     * 小于等于 func(column) <= val
     *
     * @param table
     * @param sqlFunction
     * @param val
     * @return
     */
    default Filter le(TableAvailable table, SQLFunction sqlFunction, Object val) {
        return funcValueFilter(table, sqlFunction, val, SQLPredicateCompareEnum.LE);
    }

    /**
     * 小于 func(column) < val
     *
     * @param table
     * @param sqlFunction
     * @param val
     * @return
     */
    default Filter lt(TableAvailable table, SQLFunction sqlFunction, Object val) {
        return funcValueFilter(table, sqlFunction, val, SQLPredicateCompareEnum.LT);
    }

    Filter funcValueFilter(TableAvailable table, SQLFunction sqlFunction, Object val, SQLPredicateCompare sqlPredicateCompare);

    //endregion

    //region column func


    /**
     * 大于 func(column) > func(column | val)
     *
     * @param tableLeft
     * @param sqlFunctionLeft
     * @param tableRight
     * @param sqlFunctionRight
     * @return
     */
    default Filter gt(TableAvailable tableLeft, SQLFunction sqlFunctionLeft, TableAvailable tableRight, SQLFunction sqlFunctionRight) {
        return funcColumnFilter(tableLeft, sqlFunctionLeft, tableRight, sqlFunctionRight, SQLPredicateCompareEnum.GT);
    }

    /**
     * 等于 func(column) >= func(column | val)
     *
     * @param tableLeft
     * @param sqlFunctionLeft
     * @param tableRight
     * @param sqlFunctionRight
     * @return
     */
    default Filter ge(TableAvailable tableLeft, SQLFunction sqlFunctionLeft, TableAvailable tableRight, SQLFunction sqlFunctionRight) {
        return funcColumnFilter(tableLeft, sqlFunctionLeft, tableRight, sqlFunctionRight, SQLPredicateCompareEnum.GE);
    }

    /**
     * 等于 func(column) = func(column | val)
     *
     * @param tableLeft
     * @param sqlFunctionLeft
     * @param tableRight
     * @param sqlFunctionRight
     * @return
     */
    default Filter eq(TableAvailable tableLeft, SQLFunction sqlFunctionLeft, TableAvailable tableRight, SQLFunction sqlFunctionRight) {
        return funcColumnFilter(tableLeft, sqlFunctionLeft, tableRight, sqlFunctionRight, SQLPredicateCompareEnum.EQ);
    }

    /**
     * 不等于 func(column) <> func(column | val)
     *
     * @param tableLeft
     * @param sqlFunctionLeft
     * @param tableRight
     * @param sqlFunctionRight
     * @return
     */
    default Filter ne(TableAvailable tableLeft, SQLFunction sqlFunctionLeft, TableAvailable tableRight, SQLFunction sqlFunctionRight) {
        return funcColumnFilter(tableLeft, sqlFunctionLeft, tableRight, sqlFunctionRight, SQLPredicateCompareEnum.NE);
    }

    /**
     * 小于等于 func(column) <= func(column | val)
     *
     * @param tableLeft
     * @param sqlFunctionLeft
     * @param tableRight
     * @param sqlFunctionRight
     * @return
     */
    default Filter le(TableAvailable tableLeft, SQLFunction sqlFunctionLeft, TableAvailable tableRight, SQLFunction sqlFunctionRight) {
        return funcColumnFilter(tableLeft, sqlFunctionLeft, tableRight, sqlFunctionRight, SQLPredicateCompareEnum.LE);
    }

    /**
     * 小于 func(column) < func(column | val)
     *
     * @param tableLeft
     * @param sqlFunctionLeft
     * @param tableRight
     * @param sqlFunctionRight
     * @return
     */
    default Filter lt(TableAvailable tableLeft, SQLFunction sqlFunctionLeft, TableAvailable tableRight, SQLFunction sqlFunctionRight) {
        return funcColumnFilter(tableLeft, sqlFunctionLeft, tableRight, sqlFunctionRight, SQLPredicateCompareEnum.LT);
    }

    Filter funcColumnFilter(TableAvailable tableLeft, SQLFunction sqlFunctionLeft, TableAvailable tableRight, SQLFunction sqlFunctionRight, SQLPredicateCompare sqlPredicateCompare);


    //endregion
    Filter and();


    Filter and(SQLExpression1<Filter> sqlWherePredicateSQLExpression);


    Filter or();


    Filter or(SQLExpression1<Filter> sqlWherePredicateSQLExpression);
}
