package com.easy.query.core.expression.segment.condition.predicate;

import com.easy.query.core.basic.jdbc.parameter.MapSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLUtil;

/**
 * @author xuejiaming
 * @FileName: ColumnValuePredicate.java
 * @Description: colum和具体值的断言
 * create time 2023/2/14 23:34
 */
public class MapColumnValuePredicate implements ValuePredicate, ShardingPredicate {
    private final TableAvailable table;
    private final String columnName;
    private final String mapKey;
    private final SQLPredicateCompare compare;
    private final QueryRuntimeContext runtimeContext;

    public MapColumnValuePredicate(TableAvailable table, String columnName,String mapKey, SQLPredicateCompare compare, QueryRuntimeContext runtimeContext) {
        this.table = table;
        this.columnName = columnName;
        this.mapKey = mapKey;
        this.compare = compare;
        this.runtimeContext = runtimeContext;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        SQLParameter sqlParameter = getParameter();
        String sqlColumnSegment = EasySQLExpressionUtil.getQuoteName(runtimeContext, columnName);
        EasySQLUtil.addParameter(toSQLContext, sqlParameter);
        return sqlColumnSegment + " " + compare.getSQL() + " ?";
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return null;
    }

    @Override
    public Predicate cloneSQLColumnSegment() {
        return new MapColumnValuePredicate(table, columnName,mapKey, compare, runtimeContext);
    }

    @Override
    public SQLPredicateCompare getOperator() {
        return compare;
    }

    @Override
    public SQLParameter getParameter() {
        return new MapSQLParameter(mapKey,true);
    }
}
