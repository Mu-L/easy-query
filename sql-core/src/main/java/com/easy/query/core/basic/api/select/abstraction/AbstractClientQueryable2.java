package com.easy.query.core.basic.api.select.abstraction;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.api.select.extension.queryable2.override.AbstractOverrideClientQueryable2;
import com.easy.query.core.basic.api.select.provider.SQLExpressionProvider;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.SubQueryModeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.DefaultRelationTableKey;
import com.easy.query.core.expression.lambda.SQLActionExpression2;
import com.easy.query.core.expression.lambda.SQLActionExpression3;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.ColumnGroupSelector;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;
import com.easy.query.core.expression.parser.core.base.ColumnResultSelector;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.base.core.FilterContext;
import com.easy.query.core.expression.parser.core.base.many.SubQueryProperty;
import com.easy.query.core.expression.parser.core.base.many.SubQueryPropertySelector;
import com.easy.query.core.expression.parser.core.base.many.SubQueryPropertySelectorImpl;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyRelationalUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xuejiaming
 * @FileName: AbstractQueryable2.java
 * @Description: 文件说明
 * create time 2023/2/6 23:43
 */
public abstract class AbstractClientQueryable2<T1, T2> extends AbstractOverrideClientQueryable2<T1, T2> implements ClientQueryable2<T1, T2> {

    protected SQLExpressionProvider<T2> sqlExpressionProvider2;

    public AbstractClientQueryable2(Class<T1> t1Class, Class<T2> t2Class, EntityQueryExpressionBuilder sqlEntityExpression) {
        super(t1Class, t2Class, sqlEntityExpression);
    }

    @Override
    public Class<T2> queryClass2() {
        return t2Class;
    }

    @Override
    public ClientQueryable<T1> getClientQueryable() {
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> getClientQueryable2() {
        return this;
    }

    @Override
    public <T3> ClientQueryable3<T1, T2, T3> leftJoin(Class<T3> joinClass, SQLActionExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> queryable3 = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable3(t1Class, t2Class, joinClass, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable3, on);
    }

    @Override
    public <T3> ClientQueryable3<T1, T2, T3> leftJoin(ClientQueryable<T3> joinQueryable, SQLActionExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on) {
        ClientQueryable<T3> selectAllT2Queryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllT2Queryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable3<T1, T2, T3> queryable3 = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable3(t1Class, t2Class, selectAllT2Queryable, MultiTableTypeEnum.LEFT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable3, on);
    }

    @Override
    public <T3> ClientQueryable3<T1, T2, T3> rightJoin(Class<T3> joinClass, SQLActionExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> queryable3 = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable3(t1Class, t2Class, joinClass, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable3, on);
    }

    @Override
    public <T3> ClientQueryable3<T1, T2, T3> rightJoin(ClientQueryable<T3> joinQueryable, SQLActionExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on) {
        ClientQueryable<T3> selectAllT2Queryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllT2Queryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable3<T1, T2, T3> queryable3 = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable3(t1Class, t2Class, selectAllT2Queryable, MultiTableTypeEnum.RIGHT_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable3, on);
    }

    @Override
    public <T3> ClientQueryable3<T1, T2, T3> innerJoin(Class<T3> joinClass, SQLActionExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on) {
        ClientQueryable3<T1, T2, T3> queryable3 = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable3(t1Class, t2Class, joinClass, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable3, on);
    }

    @Override
    public <T3> ClientQueryable3<T1, T2, T3> innerJoin(ClientQueryable<T3> joinQueryable, SQLActionExpression3<WherePredicate<T1>, WherePredicate<T2>, WherePredicate<T3>> on) {
        ClientQueryable<T3> selectAllT2Queryable = EasySQLExpressionUtil.cloneAndSelectAllQueryable(joinQueryable);
        entityQueryExpressionBuilder.getExpressionContext().extract(selectAllT2Queryable.getSQLEntityExpressionBuilder().getExpressionContext());
        ClientQueryable3<T1, T2, T3> queryable3 = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable3(t1Class, t2Class, selectAllT2Queryable, MultiTableTypeEnum.INNER_JOIN, entityQueryExpressionBuilder);
        return EasySQLExpressionUtil.executeJoinOn(queryable3, on);
    }

    @Override
    public ClientQueryable2<T1, T2> subQueryToGroupJoin(boolean condition, SQLFuncExpression2<SubQueryPropertySelector, SubQueryPropertySelector, SubQueryProperty> manyPropColumnExpression) {
        if (condition) {
            EntityTableExpressionBuilder table1 = entityQueryExpressionBuilder.getTable(0);
            EntityTableExpressionBuilder table2 = entityQueryExpressionBuilder.getTable(1);
            SubQueryProperty subQueryProperty = manyPropColumnExpression.apply(new SubQueryPropertySelectorImpl(table1.getEntityTable()), new SubQueryPropertySelectorImpl(table2.getEntityTable()));
            EasyRelationalUtil.TableOrRelationTable tableOrRelationalTable = EasyRelationalUtil.getTableOrRelationalTable(entityQueryExpressionBuilder, subQueryProperty.getTable(), subQueryProperty.getNavValue());
            TableAvailable leftTable = tableOrRelationalTable.table;
            String property = tableOrRelationalTable.property;
            entityQueryExpressionBuilder.putSubQueryToGroupJoinJoin(new DefaultRelationTableKey(leftTable, property), SubQueryModeEnum.GROUP_JOIN);
        }
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> where(boolean condition, SQLActionExpression2<WherePredicate<T1>, WherePredicate<T2>> whereExpression) {
        if (condition) {
            FilterContext whereFilterContext = getSQLExpressionProvider1().getWhereFilterContext();
            WherePredicate<T1> sqlWherePredicate1 = getSQLExpressionProvider1().getWherePredicate(whereFilterContext);
            WherePredicate<T2> sqlWherePredicate2 = getSQLExpressionProvider2().getWherePredicate(whereFilterContext);
            whereExpression.apply(sqlWherePredicate1, sqlWherePredicate2);
        }
        return this;
    }


    @Override
    public <TR> ClientQueryable<TR> select(Class<TR> resultClass, SQLActionExpression2<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>> selectExpression) {
        ColumnAsSelector<T1, TR> sqlColumnAsSelector1 = getSQLExpressionProvider1().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        ColumnAsSelector<T2, TR> sqlColumnAsSelector2 = getSQLExpressionProvider2().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
        selectExpression.apply(sqlColumnAsSelector1, sqlColumnAsSelector2);
        return entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().createQueryable(resultClass, entityQueryExpressionBuilder);
    }

    @Override
    public <TR> Query<TR> selectAutoInclude(Class<TR> resultClass, SQLActionExpression2<ColumnAsSelector<T1, TR>, ColumnAsSelector<T2, TR>> selectExpression, boolean replace) {
        selectAutoInclude0(resultClass, replace);
        if (selectExpression != null) {
            ColumnAsSelector<T1, TR> sqlColumnAsSelector1 = getSQLExpressionProvider1().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
            ColumnAsSelector<T2, TR> sqlColumnAsSelector2 = getSQLExpressionProvider2().getColumnAsSelector(entityQueryExpressionBuilder.getProjects(), resultClass);
            selectExpression.apply(sqlColumnAsSelector1, sqlColumnAsSelector2);
        }
        return select(resultClass);
    }

    private <TMember> List<TMember> selectAggregateList(SQLActionExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression, SQLFuncExpression2<TableAvailable,String, SQLFunction> sqlFunctionCreator, Class<TMember> resultClass) {

        ProjectSQLBuilderSegmentImpl projectSQLBuilderSegment = new ProjectSQLBuilderSegmentImpl();

        ColumnResultSelector<T1> sqlColumnResultSelector1 = getSQLExpressionProvider1().getColumnResultSelector(projectSQLBuilderSegment);
        ColumnResultSelector<T2> sqlColumnResultSelector2 = getSQLExpressionProvider2().getColumnResultSelector(projectSQLBuilderSegment);
        columnSelectorExpression.apply(sqlColumnResultSelector1, sqlColumnResultSelector2);
        if (projectSQLBuilderSegment.isEmpty()) {
            throw new EasyQueryException("aggregate query not found column");
        }
        SQLEntitySegment sqlSegment = (SQLEntitySegment) projectSQLBuilderSegment.getSQLSegments().get(0);

        TableAvailable table = sqlSegment.getTable();
        String propertyName = sqlSegment.getPropertyName();
        SQLFunction sqlFunction = sqlFunctionCreator.apply(table, propertyName);

        return selectAggregateList(table, sqlFunction, propertyName, resultClass);
    }

    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLActionExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression, BigDecimal def) {

        List<TMember> result = selectAggregateList(columnSelectorExpression, (table, prop) -> runtimeContext.fx().sum(s -> s.column(table, prop)), null);
        TMember resultMember = EasyCollectionUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(SQLActionExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression, TMember def) {
        List<TMember> result = selectAggregateList(columnSelectorExpression, (table, prop) -> runtimeContext.fx().sum(s -> s.column(table, prop)), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember maxOrDefault(SQLActionExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression, TMember def) {
        List<TMember> result = selectAggregateList(columnSelectorExpression, (table, prop) -> runtimeContext.fx().max(s -> s.column(table, prop)), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember> TMember minOrDefault(SQLActionExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression, TMember def) {

        List<TMember> result = selectAggregateList(columnSelectorExpression, (table, prop) -> runtimeContext.fx().min(s -> s.column(table, prop)), null);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLActionExpression2<ColumnResultSelector<T1>, ColumnResultSelector<T2>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {

        List<TResult> result = selectAggregateList(columnSelectorExpression, (table, prop) -> runtimeContext.fx().avg(s -> s.column(table, prop)), resultClass);
        return EasyCollectionUtil.firstOrDefault(result, def);
    }

    @Override
    public ClientQueryable2<T1, T2> orderByAsc(boolean condition, SQLActionExpression2<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>> selectExpression) {
        if (condition) {
            ColumnOrderSelector<T1> sqlOrderColumnSelector1 = getSQLExpressionProvider1().getOrderColumnSelector(true);
            ColumnOrderSelector<T2> sqlOrderColumnSelector2 = getSQLExpressionProvider2().getOrderColumnSelector(true);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2);
        }
        return this;
    }


    @Override
    public ClientQueryable2<T1, T2> orderByDesc(boolean condition, SQLActionExpression2<ColumnOrderSelector<T1>, ColumnOrderSelector<T2>> selectExpression) {
        if (condition) {
            ColumnOrderSelector<T1> sqlOrderColumnSelector1 = getSQLExpressionProvider1().getOrderColumnSelector(false);
            ColumnOrderSelector<T2> sqlOrderColumnSelector2 = getSQLExpressionProvider2().getOrderColumnSelector(false);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2);
        }
        return this;
    }


    @Override
    public ClientQueryable2<T1, T2> groupBy(boolean condition, SQLActionExpression2<ColumnGroupSelector<T1>, ColumnGroupSelector<T2>> selectExpression) {
        if (condition) {
            ColumnGroupSelector<T1> sqlGroupSelector1 = getSQLExpressionProvider1().getGroupColumnSelector();
            ColumnGroupSelector<T2> sqlGroupSelector2 = getSQLExpressionProvider2().getGroupColumnSelector();
            selectExpression.apply(sqlGroupSelector1, sqlGroupSelector2);
        }
        return this;
    }

    @Override
    public ClientQueryable2<T1, T2> having(boolean condition, SQLActionExpression2<WhereAggregatePredicate<T1>, WhereAggregatePredicate<T2>> predicateExpression) {
        if (condition) {
            WhereAggregatePredicate<T1> sqlGroupSelector1 = getSQLExpressionProvider1().getAggregatePredicate();
            WhereAggregatePredicate<T2> sqlGroupSelector2 = getSQLExpressionProvider2().getAggregatePredicate();
            predicateExpression.apply(sqlGroupSelector1, sqlGroupSelector2);
        }
        return this;
    }


    @Override
    public SQLExpressionProvider<T2> getSQLExpressionProvider2() {
        if (sqlExpressionProvider2 == null) {
            sqlExpressionProvider2 = runtimeContext.getSQLExpressionInvokeFactory().createSQLExpressionProvider(1, this.entityQueryExpressionBuilder);
        }
        return sqlExpressionProvider2;
    }

}
