package com.easy.query.core.proxy.extension.functions.type;

import com.easy.query.core.proxy.extension.ColumnFuncComparableExpression;
import com.easy.query.core.proxy.extension.functions.ColumnAnyFunctionAvailable;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface AnyTypeExpression<T> extends ColumnFuncComparableExpression<T>,
        ColumnAnyFunctionAvailable<T> {
    @Override
    default <TR> AnyTypeExpression<TR> asAnyType(Class<TR> clazz) {
        ColumnFuncComparableExpression.super.asAnyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }
   void executeSQL();

}
