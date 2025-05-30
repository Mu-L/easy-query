package com.easy.query.core.proxy.predicate.aggregate;

import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.impl.SQLAggregatePredicateImpl;
import com.easy.query.core.proxy.predicate.DSLAssertPredicate;

/**
 * create time 2023/12/6 21:37
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLAssertAggregatePredicate<TProperty> extends DSLAssertPredicate<TProperty>,  DSLSQLFunctionAvailable {
    @Override
    default void isNull(boolean condition) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.isNull(this.getTable(), func().apply(fx));
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.IS_NULL);
            }));
        }
    }

    @Override
    default void isNotNull(boolean condition) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.isNotNull(this.getTable(), func().apply(fx));
            }, f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.func(this.getTable(), func().apply(fx), SQLPredicateCompareEnum.IS_NOT_NULL);
            }));
        }
    }
}
