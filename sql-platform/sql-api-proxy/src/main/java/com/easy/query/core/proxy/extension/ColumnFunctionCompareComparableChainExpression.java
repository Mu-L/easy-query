package com.easy.query.core.proxy.extension;

import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionCompareComparableChainExpression<T> extends ColumnFuncComparableExpression<T> {
    @Override
    default <TR> ColumnFunctionCompareComparableChainExpression<TR> asAnyType(Class<TR> clazz) {
        ColumnFuncComparableExpression.super.asAnyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }
}
