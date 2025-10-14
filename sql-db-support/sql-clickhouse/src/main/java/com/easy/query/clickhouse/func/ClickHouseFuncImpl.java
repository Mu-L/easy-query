package com.easy.query.clickhouse.func;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.func.SQLFuncImpl;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.func.def.enums.DateTimeUnitEnum;
import com.easy.query.core.func.def.enums.TimeUnitEnum;

import java.util.concurrent.TimeUnit;

/**
 * create time 2023/12/21 10:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class ClickHouseFuncImpl extends SQLFuncImpl {
    @Override
    public SQLFunction stringCompareTo(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new ClickHouseStringCompareToSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction cast(SQLActionExpression1<ColumnFuncSelector> sqlExpression, Class<?> targetClazz) {
        return new ClickHouseCastSQLFunction(getColumnExpressions(sqlExpression), targetClazz);
    }

    @Override
    public SQLFunction like(SQLActionExpression1<ColumnFuncSelector> sqlExpression, boolean like, SQLLikeEnum sqlLike) {
        ClickHouseLikeSQLFunction likeSQLFunction = new ClickHouseLikeSQLFunction(getColumnExpressions(sqlExpression), sqlLike);
        if (!like) {
            return not(x -> x.sqlFunc(likeSQLFunction));
        }
        return likeSQLFunction;
    }

    @Override
    public SQLFunction random() {
        return new ClickHouseRandomSQLFunction();
    }

    @Override
    public SQLFunction plusDateTime(SQLActionExpression1<ColumnFuncSelector> sqlExpression, long duration, TimeUnit timeUnit) {
        return new ClickHouseDateTimePlusSQLFunction(getColumnExpressions(sqlExpression), duration, timeUnit);
    }

    @Override
    public SQLFunction plusDateTime2(SQLActionExpression1<ColumnFuncSelector> sqlExpression, TimeUnitEnum timeUnit) {
        return new ClickHouseDateTime2PlusSQLFunction(getColumnExpressions(sqlExpression), timeUnit);
    }

    @Override
    public SQLFunction plusDateTimeMonths(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new ClickHouseDateTimePlusMonthSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction plusDateTimeYears(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new ClickHouseDateTimePlusYearSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction dateTimeProperty(SQLActionExpression1<ColumnFuncSelector> sqlExpression, DateTimeUnitEnum dateTimeUnitEnum) {
        return new ClickHouseDateTimePropertySQLFunction(getColumnExpressions(sqlExpression), dateTimeUnitEnum);
    }

    @Override
    public SQLFunction dateTimeFormat(SQLActionExpression1<ColumnFuncSelector> sqlExpression, String javaFormat) {
        return new ClickHouseDateTimeFormatSQLFunction(getColumnExpressions(sqlExpression), javaFormat);
    }

    @Override
    public SQLFunction dateTimeSQLFormat(SQLTableOwner tableOwner, String property, String format) {
        return new ClickHouseDateTimeSQLFormatSQLFunction(getTable(tableOwner), property, format);
    }

    @Override
    public SQLFunction joining(SQLActionExpression1<ColumnFuncSelector> sqlExpression, boolean distinct) {
        return new ClickHouseJoiningSQLFunction(getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction utcNow() {
        return ClickHouseUtcNowSQLFunction.INSTANCE;
    }

    @Override
    public SQLFunction booleanSQLFunction(String sqlSegment, SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        SQLFunction sqlFunction = anySQLFunction(sqlSegment, sqlExpression);
        SQLActionExpression1<ColumnFuncSelector> sqlExpressionFunc = columnFuncSelector -> {
            columnFuncSelector.sqlFunc(sqlFunction);
            columnFuncSelector.value(true);
            columnFuncSelector.value(false);
        };
        return new ClickHouseBooleanSQLFunction(getColumnExpressions(sqlExpressionFunc));
    }

    @Override
    public SQLFunction maxColumns(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new ClickHouseMaxMinColumnsSQLFunction(true, getColumnExpressions(sqlExpression));
    }

    @Override
    public SQLFunction minColumns(SQLActionExpression1<ColumnFuncSelector> sqlExpression) {
        return new ClickHouseMaxMinColumnsSQLFunction(false, getColumnExpressions(sqlExpression));
    }
}