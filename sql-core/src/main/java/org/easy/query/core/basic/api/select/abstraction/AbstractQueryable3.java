package org.easy.query.core.basic.api.select.abstraction;

import org.easy.query.core.abstraction.metadata.ColumnMetadata;
import org.easy.query.core.abstraction.sql.enums.EasyAggregate;
import org.easy.query.core.abstraction.sql.enums.IEasyFunc;
import org.easy.query.core.basic.api.select.Queryable;
import org.easy.query.core.basic.api.select.Queryable3;
import org.easy.query.core.basic.api.select.provider.*;
import org.easy.query.core.exception.EasyQueryException;
import org.easy.query.core.expression.lambda.SqlExpression2;
import org.easy.query.core.expression.lambda.SqlExpression3;
import org.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.expression.parser.abstraction.SqlColumnResultSelector;
import org.easy.query.core.expression.segment.FuncColumnSegment;
import org.easy.query.core.expression.segment.SqlEntitySegment;
import org.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import org.easy.query.core.query.SqlEntityQueryExpression;
import org.easy.query.core.query.SqlEntityTableExpression;
import org.easy.query.core.util.ArrayUtil;
import org.easy.query.core.util.EasyUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * @FileName: AbstractQueryable2.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:43
 * @Created by xuejiaming
 */
public abstract class AbstractQueryable3<T1, T2, T3> extends AbstractQueryable<T1> implements Queryable3<T1, T2, T3> {

    protected final Class<T2> t2Class;
    protected final Class<T3> t3Class;
    private final EasyQuerySqlBuilderProvider3<T1, T2, T3> sqlPredicateProvider;

    public AbstractQueryable3(Class<T1> t1Class, Class<T2> t2Class, Class<T3> t3Class, SqlEntityQueryExpression sqlEntityExpression) {
        super(t1Class, sqlEntityExpression);
        this.t2Class = t2Class;
        this.t3Class = t3Class;

        this.sqlPredicateProvider = new Select3SqlProvider<>(sqlEntityExpression);
    }


    @Override
    public Queryable3<T1, T2, T3> where(boolean condition, SqlExpression3<SqlPredicate<T1>, SqlPredicate<T2>, SqlPredicate<T3>> whereExpression) {
        if (condition) {
            SqlPredicate<T1> sqlWherePredicate1 = getSqlBuilderProvider3().getSqlWherePredicate1();
            SqlPredicate<T2> sqlWherePredicate2 = getSqlBuilderProvider3().getSqlWherePredicate2();
            SqlPredicate<T3> sqlWherePredicate3 = getSqlBuilderProvider3().getSqlWherePredicate3();
            whereExpression.apply(sqlWherePredicate1, sqlWherePredicate2,sqlWherePredicate3);
        }
         return this;
    }

    @Override
    public <TR> Queryable<TR> select(Class<TR> resultClass, SqlExpression3<SqlColumnAsSelector<T1, TR>, SqlColumnAsSelector<T2, TR>, SqlColumnAsSelector<T3, TR>> selectExpression) {

        SqlColumnAsSelector<T1, TR> sqlColumnAsSelector1 = getSqlBuilderProvider3().getSqlColumnAsSelector1(sqlEntityExpression.getProjects());
        SqlColumnAsSelector<T2, TR> sqlColumnAsSelector2 = getSqlBuilderProvider3().getSqlColumnAsSelector2(sqlEntityExpression.getProjects());
        SqlColumnAsSelector<T3, TR> sqlColumnAsSelector3 = getSqlBuilderProvider3().getSqlColumnAsSelector3(sqlEntityExpression.getProjects());
        selectExpression.apply(sqlColumnAsSelector1,sqlColumnAsSelector2,sqlColumnAsSelector3);
        return sqlEntityExpression.getRuntimeContext().getSqlApiFactory().createQueryable(resultClass, sqlEntityExpression);
    }
    private <TMember> List<TMember> selectAggregateList(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression, IEasyFunc easyFunc) {

        ProjectSqlBuilderSegment projectSqlBuilderSegment = new ProjectSqlBuilderSegment();

        SqlColumnResultSelector<T1, TMember> sqlColumnResultSelector1 = getSqlBuilderProvider3().getSqlColumnResultSelector1(projectSqlBuilderSegment);
        SqlColumnResultSelector<T2, TMember> sqlColumnResultSelector2 = getSqlBuilderProvider3().getSqlColumnResultSelector2(projectSqlBuilderSegment);
        SqlColumnResultSelector<T3, TMember> sqlColumnResultSelector3 = getSqlBuilderProvider3().getSqlColumnResultSelector3(projectSqlBuilderSegment);
        columnSelectorExpression.apply(sqlColumnResultSelector1,sqlColumnResultSelector2,sqlColumnResultSelector3);
        if(projectSqlBuilderSegment.isEmpty()){
            throw new EasyQueryException("aggreagate query not found column");
        }
        SqlEntitySegment sqlSegment = (SqlEntitySegment)projectSqlBuilderSegment.getSqlSegments().get(0);

        SqlEntityTableExpression table = sqlSegment.getTable();
        String propertyName = sqlSegment.getPropertyName();
        ColumnMetadata columnMetadata = EasyUtil.getColumnMetadata(table,propertyName);

        return cloneQueryable().select(easyFunc.getFuncColumn(projectSqlBuilderSegment.toSql())).toList((Class<TMember>)columnMetadata.getProperty().getPropertyType());
    }

    @Override
    public <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression, BigDecimal def) {

        List<TMember> result = selectAggregateList(columnSelectorExpression, EasyAggregate.SUM);
        TMember resultMember = ArrayUtil.firstOrNull(result);
        if (resultMember == null) {
            return def;
        }
        return new BigDecimal(resultMember.toString());
    }

    @Override
    public <TMember extends Number> TMember sumOrDefault(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {
        List<TMember> result = selectAggregateList(columnSelectorExpression, EasyAggregate.SUM);
        return ArrayUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember> TMember maxOrDefault(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {
        List<TMember> result = selectAggregateList(columnSelectorExpression, EasyAggregate.MAX);
        return ArrayUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember> TMember minOrDefault(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {
        List<TMember> result = selectAggregateList(columnSelectorExpression, EasyAggregate.MIN);
        return ArrayUtil.firstOrDefault(result,def);
    }

    @Override
    public <TMember> TMember avgOrDefault(SqlExpression3<SqlColumnResultSelector<T1, TMember>, SqlColumnResultSelector<T2, TMember>, SqlColumnResultSelector<T3, TMember>> columnSelectorExpression, TMember def) {
        List<TMember> result = selectAggregateList(columnSelectorExpression, EasyAggregate.AVG);
        return ArrayUtil.firstOrDefault(result,def);
    }

    @Override
    public Integer lenOrDefault(SqlExpression3<SqlColumnResultSelector<T1, ?>, SqlColumnResultSelector<T2, ?>, SqlColumnResultSelector<T3, ?>> columnSelectorExpression, Integer def) {

        ProjectSqlBuilderSegment projectSqlBuilderSegment = new ProjectSqlBuilderSegment();

        SqlColumnResultSelector<T1, ?> sqlColumnResultSelector1 = getSqlBuilderProvider3().getSqlColumnResultSelector1(projectSqlBuilderSegment);
        SqlColumnResultSelector<T2, ?> sqlColumnResultSelector2 = getSqlBuilderProvider3().getSqlColumnResultSelector2(projectSqlBuilderSegment);
        SqlColumnResultSelector<T3, ?> sqlColumnResultSelector3 = getSqlBuilderProvider3().getSqlColumnResultSelector3(projectSqlBuilderSegment);
        columnSelectorExpression.apply(sqlColumnResultSelector1,sqlColumnResultSelector2,sqlColumnResultSelector3);
        if(projectSqlBuilderSegment.isEmpty()){
            throw new EasyQueryException("aggreagate query not found column");
        }

        List<Integer> result =  cloneQueryable().select(EasyAggregate.LEN.getFuncColumn(projectSqlBuilderSegment.toSql())).toList(Integer.class);
        return ArrayUtil.firstOrDefault(result,def);
    }

    @Override
    public Queryable3<T1, T2, T3> groupBy(boolean condition, SqlExpression3<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>> selectExpression) {
        if (condition) {
            SqlColumnSelector<T1> sqlGroupSelector1 = getSqlBuilderProvider3().getSqlGroupColumnSelector1();
            SqlColumnSelector<T2> sqlGroupSelector2 = getSqlBuilderProvider3().getSqlGroupColumnSelector2();
            SqlColumnSelector<T3> sqlGroupSelector3 = getSqlBuilderProvider3().getSqlGroupColumnSelector3();
            selectExpression.apply(sqlGroupSelector1, sqlGroupSelector2,sqlGroupSelector3);
        }
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> orderByAsc(boolean condition, SqlExpression3<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>> selectExpression) {
        if (condition) {
            SqlColumnSelector<T1> sqlOrderColumnSelector1 = getSqlBuilderProvider3().getSqlOrderColumnSelector1(true);
            SqlColumnSelector<T2> sqlOrderColumnSelector2 = getSqlBuilderProvider3().getSqlOrderColumnSelector2(true);
            SqlColumnSelector<T3> sqlOrderColumnSelector3 = getSqlBuilderProvider3().getSqlOrderColumnSelector3(true);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2,sqlOrderColumnSelector3);
        }
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> orderByDesc(boolean condition, SqlExpression3<SqlColumnSelector<T1>, SqlColumnSelector<T2>, SqlColumnSelector<T3>> selectExpression) {
        if (condition) {
            SqlColumnSelector<T1> sqlOrderColumnSelector1 = getSqlBuilderProvider3().getSqlOrderColumnSelector1(false);
            SqlColumnSelector<T2> sqlOrderColumnSelector2 = getSqlBuilderProvider3().getSqlOrderColumnSelector2(false);
            SqlColumnSelector<T3> sqlOrderColumnSelector3 = getSqlBuilderProvider3().getSqlOrderColumnSelector3(false);
            selectExpression.apply(sqlOrderColumnSelector1, sqlOrderColumnSelector2,sqlOrderColumnSelector3);
        }
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> limit(boolean condition, long offset, long rows) {
        super.limit(condition, offset, rows);
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> disableLogicDelete() {
        super.disableLogicDelete();
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> enableLogicDelete() {
        super.enableLogicDelete();
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> noQueryFilter() {
        super.noQueryFilter();
        return this;
    }

    @Override
    public Queryable3<T1, T2, T3> useQueryFilter() {
        super.useQueryFilter();
        return this;
    }

    public EasyQuerySqlBuilderProvider3<T1, T2, T3> getSqlBuilderProvider3() {
        return sqlPredicateProvider;
    }

    @Override
    public EasyQuerySqlBuilderProvider<T1> getSqlBuilderProvider1() {
        return sqlPredicateProvider;
    }
}