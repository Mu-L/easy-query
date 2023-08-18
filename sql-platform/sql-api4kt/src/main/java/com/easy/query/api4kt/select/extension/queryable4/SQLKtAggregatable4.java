package com.easy.query.api4kt.select.extension.queryable4;

import com.easy.query.api4kt.sql.SQLKtColumnResultSelector;
import com.easy.query.api4kt.sql.impl.SQLKtColumnResultSelectorImpl;
import com.easy.query.core.common.tuple.Tuple4;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression4;

import java.math.BigDecimal;

/**
 * create time 2023/8/15 21:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtAggregatable4<T1, T2, T3, T4> extends ClientKtQueryable4Available<T1, T2, T3, T4> {

    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNull(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return getClientQueryable4().sumBigDecimalOrNull((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefault(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable4().sumBigDecimalOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNull(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return getClientQueryable4().sumOrNull((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        });
    }

    default <TMember extends Number> TMember sumOrDefault(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable4().sumOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        }, def);
    }

    default <TMember> TMember maxOrNull(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return getClientQueryable4().maxOrNull((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        });
    }

    default <TMember> TMember maxOrDefault(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable4().maxOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        }, def);
    }

    default <TMember> TMember minOrNull(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return getClientQueryable4().minOrNull((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        });
    }

    default <TMember> TMember minOrDefault(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression, TMember def) {
        return getClientQueryable4().minOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        }, def);
    }

    default <TMember extends Number> Double avgOrNull(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return getClientQueryable4().avgOrNull((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNull(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return getClientQueryable4().avgBigDecimalOrNull((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        });
    }

    default <TMember extends Number> Float avgFloatOrNull(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression) {
        return getClientQueryable4().avgFloatOrNull((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        });
    }

    default <TMember extends Number> Double avgOrDefault(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression, Double def) {
        return getClientQueryable4().avgOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefault(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable4().avgBigDecimalOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefault(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression, Float def) {
        return getClientQueryable4().avgFloatOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefault(SQLExpression4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable4().avgOrDefault((selector1, selector2, selector3, selector4) -> {
            columnSelectorExpression.apply(new SQLKtColumnResultSelectorImpl<>(selector1), new SQLKtColumnResultSelectorImpl<>(selector2), new SQLKtColumnResultSelectorImpl<>(selector3), new SQLKtColumnResultSelectorImpl<>(selector4));
        }, def, resultClass);
    }


    /**
     * 防止溢出
     *
     * @param columnSelectorExpression
     * @param <TMember>
     * @return
     */
    default <TMember extends Number> BigDecimal sumBigDecimalOrNullMerge(SQLExpression1<Tuple4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>>> columnSelectorExpression) {
        return getClientQueryable4().sumBigDecimalOrNullMerge((tuple4) -> {
            columnSelectorExpression.apply(new Tuple4<>(new SQLKtColumnResultSelectorImpl<>(tuple4.t1()), new SQLKtColumnResultSelectorImpl<>(tuple4.t2()), new SQLKtColumnResultSelectorImpl<>(tuple4.t3()),new SQLKtColumnResultSelectorImpl<>(tuple4.t4())));
        });
    }


    default <TMember extends Number> BigDecimal sumBigDecimalOrDefaultMerge(SQLExpression1<Tuple4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable4().sumBigDecimalOrDefaultMerge((tuple4) -> {
            columnSelectorExpression.apply(new Tuple4<>(new SQLKtColumnResultSelectorImpl<>(tuple4.t1()), new SQLKtColumnResultSelectorImpl<>(tuple4.t2()), new SQLKtColumnResultSelectorImpl<>(tuple4.t3()),new SQLKtColumnResultSelectorImpl<>(tuple4.t4())));
        }, def);
    }

    default <TMember extends Number> TMember sumOrNullMerge(SQLExpression1<Tuple4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>>> columnSelectorExpression) {
        return getClientQueryable4().sumOrNullMerge((tuple4) -> {
            columnSelectorExpression.apply(new Tuple4<>(new SQLKtColumnResultSelectorImpl<>(tuple4.t1()), new SQLKtColumnResultSelectorImpl<>(tuple4.t2()), new SQLKtColumnResultSelectorImpl<>(tuple4.t3()),new SQLKtColumnResultSelectorImpl<>(tuple4.t4())));
        });
    }

    default <TMember extends Number> TMember sumOrDefaultMerge(SQLExpression1<Tuple4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable4().sumOrDefaultMerge((tuple4) -> {
            columnSelectorExpression.apply(new Tuple4<>(new SQLKtColumnResultSelectorImpl<>(tuple4.t1()), new SQLKtColumnResultSelectorImpl<>(tuple4.t2()), new SQLKtColumnResultSelectorImpl<>(tuple4.t3()),new SQLKtColumnResultSelectorImpl<>(tuple4.t4())));
        }, def);
    }

    default <TMember> TMember maxOrNullMerge(SQLExpression1<Tuple4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>>> columnSelectorExpression) {
        return getClientQueryable4().maxOrNullMerge((tuple4) -> {
            columnSelectorExpression.apply(new Tuple4<>(new SQLKtColumnResultSelectorImpl<>(tuple4.t1()), new SQLKtColumnResultSelectorImpl<>(tuple4.t2()), new SQLKtColumnResultSelectorImpl<>(tuple4.t3()),new SQLKtColumnResultSelectorImpl<>(tuple4.t4())));
        });
    }

    default <TMember> TMember maxOrDefaultMerge(SQLExpression1<Tuple4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable4().maxOrDefaultMerge((tuple4) -> {
            columnSelectorExpression.apply(new Tuple4<>(new SQLKtColumnResultSelectorImpl<>(tuple4.t1()), new SQLKtColumnResultSelectorImpl<>(tuple4.t2()), new SQLKtColumnResultSelectorImpl<>(tuple4.t3()),new SQLKtColumnResultSelectorImpl<>(tuple4.t4())));
        }, def);
    }

    default <TMember> TMember minOrNullMerge(SQLExpression1<Tuple4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>>> columnSelectorExpression) {
        return getClientQueryable4().minOrNullMerge((tuple4) -> {
            columnSelectorExpression.apply(new Tuple4<>(new SQLKtColumnResultSelectorImpl<>(tuple4.t1()), new SQLKtColumnResultSelectorImpl<>(tuple4.t2()), new SQLKtColumnResultSelectorImpl<>(tuple4.t3()),new SQLKtColumnResultSelectorImpl<>(tuple4.t4())));
        });
    }

    default <TMember> TMember minOrDefaultMerge(SQLExpression1<Tuple4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>>> columnSelectorExpression, TMember def) {
        return getClientQueryable4().minOrDefaultMerge((tuple4) -> {
            columnSelectorExpression.apply(new Tuple4<>(new SQLKtColumnResultSelectorImpl<>(tuple4.t1()), new SQLKtColumnResultSelectorImpl<>(tuple4.t2()), new SQLKtColumnResultSelectorImpl<>(tuple4.t3()),new SQLKtColumnResultSelectorImpl<>(tuple4.t4())));
        }, def);
    }

    default <TMember extends Number> Double avgOrNullMerge(SQLExpression1<Tuple4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>>> columnSelectorExpression) {
        return getClientQueryable4().avgOrNullMerge((tuple4) -> {
            columnSelectorExpression.apply(new Tuple4<>(new SQLKtColumnResultSelectorImpl<>(tuple4.t1()), new SQLKtColumnResultSelectorImpl<>(tuple4.t2()), new SQLKtColumnResultSelectorImpl<>(tuple4.t3()),new SQLKtColumnResultSelectorImpl<>(tuple4.t4())));
        });
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrNullMerge(SQLExpression1<Tuple4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>>> columnSelectorExpression) {
        return getClientQueryable4().avgBigDecimalOrNullMerge((tuple4) -> {
            columnSelectorExpression.apply(new Tuple4<>(new SQLKtColumnResultSelectorImpl<>(tuple4.t1()), new SQLKtColumnResultSelectorImpl<>(tuple4.t2()), new SQLKtColumnResultSelectorImpl<>(tuple4.t3()),new SQLKtColumnResultSelectorImpl<>(tuple4.t4())));
        });
    }

    default <TMember extends Number> Float avgFloatOrNullMerge(SQLExpression1<Tuple4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>>> columnSelectorExpression) {
        return getClientQueryable4().avgFloatOrNullMerge((tuple4) -> {
            columnSelectorExpression.apply(new Tuple4<>(new SQLKtColumnResultSelectorImpl<>(tuple4.t1()), new SQLKtColumnResultSelectorImpl<>(tuple4.t2()), new SQLKtColumnResultSelectorImpl<>(tuple4.t3()),new SQLKtColumnResultSelectorImpl<>(tuple4.t4())));
        });
    }

    default <TMember extends Number> Double avgOrDefaultMerge(SQLExpression1<Tuple4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>>> columnSelectorExpression, Double def) {
        return getClientQueryable4().avgOrDefaultMerge((tuple4) -> {
            columnSelectorExpression.apply(new Tuple4<>(new SQLKtColumnResultSelectorImpl<>(tuple4.t1()), new SQLKtColumnResultSelectorImpl<>(tuple4.t2()), new SQLKtColumnResultSelectorImpl<>(tuple4.t3()),new SQLKtColumnResultSelectorImpl<>(tuple4.t4())));
        }, def);
    }

    default <TMember extends Number> BigDecimal avgBigDecimalOrDefaultMerge(SQLExpression1<Tuple4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>>> columnSelectorExpression, BigDecimal def) {
        return getClientQueryable4().avgBigDecimalOrDefaultMerge((tuple4) -> {
            columnSelectorExpression.apply(new Tuple4<>(new SQLKtColumnResultSelectorImpl<>(tuple4.t1()), new SQLKtColumnResultSelectorImpl<>(tuple4.t2()), new SQLKtColumnResultSelectorImpl<>(tuple4.t3()),new SQLKtColumnResultSelectorImpl<>(tuple4.t4())));
        }, def);
    }

    default <TMember extends Number> Float avgFloatOrDefaultMerge(SQLExpression1<Tuple4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>>> columnSelectorExpression, Float def) {
        return getClientQueryable4().avgFloatOrDefaultMerge((tuple4) -> {
            columnSelectorExpression.apply(new Tuple4<>(new SQLKtColumnResultSelectorImpl<>(tuple4.t1()), new SQLKtColumnResultSelectorImpl<>(tuple4.t2()), new SQLKtColumnResultSelectorImpl<>(tuple4.t3()),new SQLKtColumnResultSelectorImpl<>(tuple4.t4())));
        }, def);
    }

    default <TMember extends Number, TResult extends Number> TResult avgOrDefaultMerge(SQLExpression1<Tuple4<SQLKtColumnResultSelector<T1, TMember>, SQLKtColumnResultSelector<T2, TMember>, SQLKtColumnResultSelector<T3, TMember>, SQLKtColumnResultSelector<T4, TMember>>> columnSelectorExpression, TResult def, Class<TResult> resultClass) {
        return getClientQueryable4().avgOrDefaultMerge((tuple4) -> {
            columnSelectorExpression.apply(new Tuple4<>(new SQLKtColumnResultSelectorImpl<>(tuple4.t1()), new SQLKtColumnResultSelectorImpl<>(tuple4.t2()), new SQLKtColumnResultSelectorImpl<>(tuple4.t3()),new SQLKtColumnResultSelectorImpl<>(tuple4.t4())));
        }, def, resultClass);
    }
}
