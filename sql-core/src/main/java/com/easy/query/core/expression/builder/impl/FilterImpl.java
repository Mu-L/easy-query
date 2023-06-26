package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.OrPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnCollectionPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnExistsSubQueryPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnInSubQueryPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnWithColumnPredicate;
import com.easy.query.core.expression.segment.condition.predicate.FuncColumnValuePredicate;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.util.EasySQLUtil;

import java.util.Arrays;
import java.util.Collection;

/**
 * create time 2023/6/22 14:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class FilterImpl implements Filter {
    private final QueryRuntimeContext runtimeContext;
    private final ExpressionContext expressionContext;
    private final PredicateSegment rootPredicateSegment;
    private final boolean reverse;
    private PredicateSegment nextPredicateSegment;

    public FilterImpl(QueryRuntimeContext runtimeContext,ExpressionContext expressionContext, PredicateSegment predicateSegment, boolean reverse) {

        this.runtimeContext = runtimeContext;
        this.expressionContext = expressionContext;
        this.rootPredicateSegment = predicateSegment;
        this.reverse = reverse;
        this.nextPredicateSegment = new AndPredicateSegment();
    }

    protected void nextAnd() {
        this.rootPredicateSegment.addPredicateSegment(nextPredicateSegment);
        this.nextPredicateSegment = new AndPredicateSegment();
    }

    protected void nextOr() {
        this.rootPredicateSegment.addPredicateSegment(nextPredicateSegment);
        this.nextPredicateSegment = new OrPredicateSegment();
    }

    protected void next() {
        if (reverse) {
            nextOr();
        } else {
            nextAnd();
        }
    }

    protected SQLPredicateCompare getReallyPredicateCompare(SQLPredicateCompare sqlPredicateCompare) {
        return reverse ? sqlPredicateCompare.toReverse() : sqlPredicateCompare;
    }

    protected void appendThisPredicate(TableAvailable table, String property, Object val, SQLPredicateCompare condition) {

        nextPredicateSegment.setPredicate(new ColumnValuePredicate(table, property, val, getReallyPredicateCompare(condition), runtimeContext));
    }

    protected void appendThisFuncPredicate(TableAvailable table, String propertyName, ColumnFunction func, SQLPredicateCompare compare, Object val) {
        nextPredicateSegment.setPredicate(new FuncColumnValuePredicate(table, func, propertyName, val, compare, runtimeContext));
    }

    @Override
    public boolean getReverse() {
        return reverse;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public Filter gt(TableAvailable table, String property, Object val) {
        appendThisPredicate(table, property, val, SQLPredicateCompareEnum.GT);
        next();
        return this;
    }

    @Override
    public Filter ge(TableAvailable table, String property, Object val) {
        appendThisPredicate(table, property, val, SQLPredicateCompareEnum.GE);
        next();
        return this;
    }

    @Override
    public Filter eq(TableAvailable table, String property, Object val) {
        appendThisPredicate(table, property, val, SQLPredicateCompareEnum.EQ);
        next();
        return this;
    }

    @Override
    public Filter ne(TableAvailable table, String property, Object val) {
        appendThisPredicate(table, property, val, SQLPredicateCompareEnum.NE);
        next();
        return this;
    }

    @Override
    public Filter le(TableAvailable table, String property, Object val) {
        appendThisPredicate(table, property, val, SQLPredicateCompareEnum.LE);
        next();
        return this;
    }

    @Override
    public Filter lt(TableAvailable table, String property, Object val) {
        appendThisPredicate(table, property, val, SQLPredicateCompareEnum.LT);
        next();
        return this;
    }

    @Override
    public Filter like(TableAvailable table, String property, Object val, SQLLikeEnum sqlLike) {
        appendThisPredicate(table, property, EasySQLUtil.getLikeParameter(val, sqlLike), SQLPredicateCompareEnum.LIKE);
        next();
        return this;
    }

    @Override
    public Filter notLike(TableAvailable table, String property, Object val, SQLLikeEnum sqlLike) {
        appendThisPredicate(table, property, EasySQLUtil.getLikeParameter(val, sqlLike), SQLPredicateCompareEnum.NOT_LIKE);
        next();
        return this;
    }

    @Override
    public Filter isNull(TableAvailable table, String property) {
        nextPredicateSegment.setPredicate(new ColumnPredicate(table, property, getReallyPredicateCompare(SQLPredicateCompareEnum.IS_NULL), runtimeContext));
        next();
        return this;
    }

    @Override
    public Filter isNotNull(TableAvailable table, String property) {
        nextPredicateSegment.setPredicate(new ColumnPredicate(table, property, getReallyPredicateCompare(SQLPredicateCompareEnum.IS_NOT_NULL), runtimeContext));
        next();
        return this;
    }

    @Override
    public Filter in(TableAvailable table, String property, Collection<?> collection) {
        nextPredicateSegment.setPredicate(new ColumnCollectionPredicate(table, property, collection, getReallyPredicateCompare(SQLPredicateCompareEnum.IN), runtimeContext));
        next();
        return this;
    }

    @Override
    public <TProperty> Filter in(TableAvailable table, String property, TProperty[] collection) {
        nextPredicateSegment.setPredicate(new ColumnCollectionPredicate(table, property, Arrays.asList(collection), getReallyPredicateCompare(SQLPredicateCompareEnum.IN), runtimeContext));
        next();
        return this;
    }

    private <T2> void extract(Query<T2> subQuery) {
        EntityQueryExpressionBuilder subQueryableSQLEntityExpressionBuilder = subQuery.getSQLEntityExpressionBuilder();
        expressionContext.extract(subQueryableSQLEntityExpressionBuilder.getExpressionContext());
    }

    private <TProperty> void subQueryIn(TableAvailable table, String property, Query<TProperty> subQueryable, SQLPredicateCompareEnum sqlPredicateCompare) {
        extract(subQueryable);
        nextPredicateSegment.setPredicate(new ColumnInSubQueryPredicate(table, property, subQueryable, getReallyPredicateCompare(sqlPredicateCompare), runtimeContext));
        next();
    }

    private <T2> void subQueryExists(TableAvailable table,Query<T2> subQuery, SQLPredicateCompareEnum sqlPredicateCompare) {

        extract(subQuery);
        Query<T2> existsQuery = subQuery.cloneQueryable().select("1");

        nextPredicateSegment.setPredicate(new ColumnExistsSubQueryPredicate(table, existsQuery, getReallyPredicateCompare(sqlPredicateCompare), runtimeContext));
        next();
    }

    @Override
    public <TProperty> Filter in(TableAvailable table, String property, Query<TProperty> subQuery) {
        subQueryIn(table, property, subQuery, SQLPredicateCompareEnum.IN);
        return this;
    }

    @Override
    public Filter notIn(TableAvailable table, String property, Collection<?> collection) {
        nextPredicateSegment.setPredicate(new ColumnCollectionPredicate(table, property, collection, getReallyPredicateCompare(SQLPredicateCompareEnum.NOT_IN), runtimeContext));
        next();
        return this;
    }

    @Override
    public <TProperty> Filter notIn(TableAvailable table, String property, TProperty[] collection) {
        nextPredicateSegment.setPredicate(new ColumnCollectionPredicate(table, property, Arrays.asList(collection), getReallyPredicateCompare(SQLPredicateCompareEnum.NOT_IN), runtimeContext));
        next();
        return this;
    }

    @Override
    public <TProperty> Filter notIn(TableAvailable table, String property, Query<TProperty> subQuery) {
        subQueryIn(table,property, subQuery, SQLPredicateCompareEnum.NOT_IN);
        return this;
    }

    @Override
    public <T2> Filter exists(TableAvailable table,Query<T2> subQuery) {
        subQueryExists(table,subQuery, SQLPredicateCompareEnum.EXISTS);
        return this;
    }

    @Override
    public <T2> Filter notExists(TableAvailable table,Query<T2> subQuery) {
        subQueryExists(table,subQuery, SQLPredicateCompareEnum.NOT_EXISTS);
        return this;
    }

    @Override
    public Filter range(TableAvailable table, String property, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight, SQLRangeEnum sqlRange) {

        if (conditionLeft) {
            boolean openFirst = SQLRangeEnum.openFirst(sqlRange);
            appendThisPredicate(table,property, valLeft, getReallyPredicateCompare(openFirst ? SQLPredicateCompareEnum.GT : SQLPredicateCompareEnum.GE));
            next();
        }
        if (conditionRight) {
            boolean openEnd = SQLRangeEnum.openEnd(sqlRange);
            appendThisPredicate(table,property, valRight, getReallyPredicateCompare(openEnd ? SQLPredicateCompareEnum.LT : SQLPredicateCompareEnum.LE));
            next();
        }
        return this;
    }

    @Override
    public Filter columnFunc(TableAvailable table, ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, Object val) {

        appendThisFuncPredicate(table,columnPropertyFunction.getPropertyName(), columnPropertyFunction.getColumnFunction(), getReallyPredicateCompare(sqlPredicateCompare), val);
        next();
        return this;
    }

    @Override
    public <T2> Filter eq(TableAvailable leftTable, String property1, TableAvailable rightTable, String property2) {
        nextPredicateSegment.setPredicate(new ColumnWithColumnPredicate(leftTable, property1, rightTable, property2, getReallyPredicateCompare(SQLPredicateCompareEnum.EQ), runtimeContext));
        next();
        return this;
    }

    protected void and0() {
        if (reverse) {
            this.nextPredicateSegment = new OrPredicateSegment();
        } else {
            this.nextPredicateSegment = new AndPredicateSegment();
        }
    }
    @Override
    public Filter and() {
        and0();
        return this;
    }

    @Override
    public Filter and(SQLExpression1<Filter> sqlWherePredicateSQLExpression) {
        and0();
        FilterImpl filter = new FilterImpl(runtimeContext, expressionContext, this.nextPredicateSegment, reverse);
        sqlWherePredicateSQLExpression.apply(filter);
        next();
        return this;
    }

    protected void or0() {
        if (reverse) {
            this.nextPredicateSegment = new AndPredicateSegment();
        } else {
            this.nextPredicateSegment = new OrPredicateSegment();
        }
    }
    @Override
    public Filter or() {
        or0();
        return this;
    }

    @Override
    public Filter or(SQLExpression1<Filter> sqlWherePredicateSQLExpression) {
        or0();
        FilterImpl filter = new FilterImpl(runtimeContext, expressionContext, this.nextPredicateSegment, reverse);
        sqlWherePredicateSQLExpression.apply(filter);
        next();
        return this;
    }
}