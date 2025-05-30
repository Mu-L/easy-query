package com.easy.query.core.proxy.columns;

import com.easy.query.core.proxy.extension.functions.ColumnStringFunctionAvailable;
import com.easy.query.core.proxy.predicate.DSLContainsStringPredicate;
import com.easy.query.core.proxy.predicate.DSLContainsPropPredicate;
import com.easy.query.core.proxy.predicate.DSLStringAssertPredicate;

/**
 * create time 2023/12/24 00:09
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLStringColumn<TProxy, TProperty> extends SQLObjectColumn<TProxy,TProperty>,
        DSLStringAssertPredicate<TProperty>,
        DSLContainsStringPredicate,
        DSLContainsPropPredicate,
        ColumnStringFunctionAvailable<TProperty> {
}
