package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.SQLFxAvailable;
import com.easy.query.core.expression.parser.core.base.core.SQLPropertyNative;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * create time 2023/2/6 23:20
 */
public interface ColumnSelector<T1> extends EntitySQLTableOwner<T1>, SQLPropertyNative<ColumnSelector<T1>>, SQLFxAvailable {
    default EntityQueryExpressionBuilder getEntityQueryExpressionBuilder(){
        return getSelector().getEntityQueryExpressionBuilder();
    }
    Selector getSelector();
    default QueryRuntimeContext getRuntimeContext(){
        return getSelector().getRuntimeContext();
    }

    ColumnSelector<T1> column(String property);
    ColumnSelector<T1> columnAs(String property,String alias);
    ColumnSelector<T1> columnFixedAs(String property,String alias);


    ColumnSelector<T1> columnIgnore(String property);

    ColumnSelector<T1> columnAll();

    default <T2> ColumnSelector<T2> then(ColumnSelector<T2> sub) {
        return sub;
    }
}
