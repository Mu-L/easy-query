package com.easy.query.core.func;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.core.func.def.impl.NativeSegmentSQLFunction;

/**
 * create time 2023/10/5 22:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFunc extends AggregateSQLFunc, SQLStringFunc, SQLDateTimeFunc, SQLMathFunc, SQLNumberFunc, SQLPartitionByFunc, SQLJsonFunc {
    /**
     * 如果property对应的值为null则返回def值
     * o.nullOrDefault("title","123")
     *
     * @param property 属性列
     * @param def      默认值
     * @return ifNull函数
     */
    default SQLFunction nullOrDefault(String property, Object def) {
        return nullOrDefault(s -> {
            s.column(property)
                    .value(def);
        });
    }

    /**
     * 如果property对应的值为null则返回默认值
     * o.fx().nullOrDefault(x->x.column("title").value("123").column("content"))
     *
     * @param sqlExpression 属性选择函数
     * @return ifNull函数
     */
    SQLFunction nullOrDefault(SQLActionExpression1<ColumnFuncSelector> sqlExpression);

    SQLFunction equalsWith(SQLActionExpression1<ColumnFuncSelector> sqlExpression);

    default SQLFunction orderByNullsMode(TableAvailable table, String property, boolean asc, @NotNull OrderByModeEnum orderByModeEnum) {
        return orderByNullsMode(o -> o.column(table,property), asc, orderByModeEnum);
    }

    default SQLFunction orderByNullsMode(SQLFunction sqlFunction, boolean asc, OrderByModeEnum orderByModeEnum) {
        return orderByNullsMode(o -> o.sqlFunc(sqlFunction), asc, orderByModeEnum);
    }

    SQLFunction orderByNullsMode(SQLActionExpression1<ColumnFuncSelector> sqlExpression, boolean asc, @NotNull OrderByModeEnum orderByModeEnum);


    /**
     * 获取绝对值
     *
     * @param property 属性列
     * @return 绝对值函数
     */
    default SQLFunction abs(String property) {
        return abs(o -> o.column(property));
    }

    /**
     * 获取绝对值
     *
     * @param tableOwner 属性所属表
     * @param property   属性列
     * @return 绝对值函数
     */
    default SQLFunction abs(SQLTableOwner tableOwner, String property) {
        return abs(o -> o.column(tableOwner, property));
    }

    SQLFunction abs(SQLActionExpression1<ColumnFuncSelector> sqlExpression);

    /**
     * 按照指定的小数位数进行四舍五入运算
     *
     * @param property 属性列
     * @param scale    保留小数位数
     * @return 四舍五入函数
     */
    default SQLFunction round(String property, int scale) {
        return round(null, property, scale);
    }

    /**
     * 按照指定的小数位数进行四舍五入运算
     *
     * @param tableOwner 列所属表
     * @param property   属性列
     * @param scale      保留小数位数
     * @return round函数
     */
    SQLFunction round(SQLTableOwner tableOwner, String property, int scale);

    /**
     * 当前时间函数
     *
     * @return 时间函数
     */
    SQLFunction now();

    /**
     * 当前UTC时间函数
     *
     * @return UTC时间函数
     */
    SQLFunction utcNow();
    SQLFunction random();

    default SQLFunction constValue(Object val) {
        return constValue(o -> o.value(val));
    }

    SQLFunction constValue(SQLActionExpression1<ColumnFuncSelector> sqlExpression);

    default SQLFunction subQueryValue(Query<?> query) {
        return subQueryValue(o -> o.subQuery(query));
    }

    SQLFunction subQueryValue(SQLActionExpression1<ColumnFuncSelector> sqlExpression);

    default SQLFunction exists(Query<?> query) {
        return exists(o -> o.subQuery(query));
    }

    SQLFunction exists(SQLActionExpression1<ColumnFuncSelector> sqlExpression);

    /**
     * 取反
     *
     * @param property 属性列
     * @return 取反函数
     */
    default SQLFunction not(String property) {
        return not(s -> {
            s.column(property);
        });
    }

    /**
     * 取反
     *
     * @param sqlFunction sql函数
     * @return 取反函数
     */
    default SQLFunction not(SQLFunction sqlFunction) {
        return not(s -> {
            s.sqlFunc(sqlFunction);
        });
    }

    SQLFunction not(SQLActionExpression1<ColumnFuncSelector> sqlExpression);

    default SQLFunction nativeSql(String sqlSegemnt, SQLActionExpression1<SQLNativeChainExpressionContext> consume) {
        return new NativeSegmentSQLFunction(sqlSegemnt, consume);
    }

    SQLFunction like(SQLActionExpression1<ColumnFuncSelector> sqlExpression, boolean like, SQLLikeEnum sqlLike);

    SQLFunction anySQLFunction(String sqlSegment, SQLActionExpression1<ColumnFuncSelector> sqlExpression);
    SQLFunction booleanSQLFunction(String sqlSegment, SQLActionExpression1<ColumnFuncSelector> sqlExpression);

    SQLFunction booleanConstantSQLFunction(boolean trueOrFalse);
}
