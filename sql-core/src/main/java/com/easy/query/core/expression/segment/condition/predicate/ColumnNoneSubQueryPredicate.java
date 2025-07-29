package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.visitor.TableVisitor;

/**
 * create time 2023/4/27 09:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnNoneSubQueryPredicate implements SubQueryPredicate {
    private final QueryRuntimeContext runtimeContext;
    private final Query<?> subQuery;

    public ColumnNoneSubQueryPredicate(Query<?> subQuery,  QueryRuntimeContext runtimeContext) {
        this.subQuery = subQuery;
        this.runtimeContext = runtimeContext;
    }

    @Override
    public TableAvailable getTable() {
        return null;
    }

    @Override
    public String getPropertyName() {
        return null;
    }

    @Override
    public Predicate cloneSQLColumnSegment() {
        return new ColumnNoneSubQueryPredicate(subQuery.cloneQueryable(), runtimeContext);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {

        StringBuilder sql = new StringBuilder();
        sql.append("NOT ( ").append(SQLPredicateCompareEnum.EXISTS).append(" (");
        String subQueryableSQL = subQuery.toSQL(toSQLContext);
        sql.append(subQueryableSQL).append("))");
        return sql.toString();
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return SQLPredicateCompareEnum.NOT_EXISTS;
    }

    @Override
    public Query<?> getSubQuery() {
        return subQuery;
    }

    @Override
    public SubQueryPredicate cloneSubQueryPredicate() {
        return new ColumnNoneSubQueryPredicate(subQuery.cloneQueryable(),runtimeContext);
    }

    @Override
    public void accept(TableVisitor visitor) {
        subQuery.getSQLEntityExpressionBuilder().accept(visitor);
    }
}
