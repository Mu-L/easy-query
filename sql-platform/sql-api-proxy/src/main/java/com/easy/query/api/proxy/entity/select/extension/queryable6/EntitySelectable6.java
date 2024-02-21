package com.easy.query.api.proxy.entity.select.extension.queryable6;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.DraftResult;
import com.easy.query.core.common.tuple.MergeTuple6;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression6;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.draft.DraftFetcher;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Objects;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntitySelectable6<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6> extends ClientEntityQueryable6Available<T1, T2, T3, T4, T5, T6>, EntityQueryable6Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6> {

    /**
     * 请使用 select + Select.DRAFT.of
     *
     * @param selectExpression
     * @param <TRProxy>
     * @param <TR>
     * @return
     */
    @Deprecated
    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy> & DraftResult> EntityQueryable<TRProxy, TR> selectDraft(SQLFuncExpression6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, DraftFetcher<TR, TRProxy>> selectExpression) {
        DraftFetcher<TR, TRProxy> draftFetcher = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy());
        ClientQueryable<TR> select = getClientQueryable6().select(EasyObjectUtil.typeCastNullable(draftFetcher.getDraft().getClass()), columnAsSelector -> {
            draftFetcher.accept(columnAsSelector.getAsSelector());
        });
        TRProxy draftProxy = draftFetcher.getDraftProxy();
        select.getSQLEntityExpressionBuilder().getExpressionContext().setDraftPropTypes(draftFetcher.getDraftPropTypes());
        return new EasyEntityQueryable<>(draftProxy, select);
    }

    /**
     * 请使用 select + Select.DRAFT.of
     *
     * @param selectExpression
     * @param <TRProxy>
     * @param <TR>
     * @return
     */
    @Deprecated
    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy> & DraftResult> EntityQueryable<TRProxy, TR> selectDraftMerge(SQLFuncExpression1<MergeTuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>, DraftFetcher<TR, TRProxy>> selectExpression) {
        return selectDraft((t1, t2, t3, t4, t5, t6) -> selectExpression.apply(new MergeTuple6<>(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy())));
    }

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> select(SQLFuncExpression6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, TRProxy> selectExpression) {

        TRProxy resultProxy = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy());
        Objects.requireNonNull(resultProxy, "select null result class");
        SQLSelectAsExpression selectAsExpression = resultProxy.getEntitySQLContext().getSelectAsExpression();
        if (selectAsExpression == null) {//全属性映射
            ClientQueryable<TR> select = getClientQueryable6().select(resultProxy.getEntityClass(), (t1, t2, t3, t4, t5, t6) -> {
                if (resultProxy == get2Proxy()) {
                    t2.columnAll();
                } else if (resultProxy == get3Proxy()) {
                    t3.columnAll();
                } else if (resultProxy == get4Proxy()) {
                    t4.columnAll();
                } else if (resultProxy == get5Proxy()) {
                    t5.columnAll();
                } else if (resultProxy == get6Proxy()) {
                    t6.columnAll();
                } else {
                    t1.columnAll();
                }
            });
            return new EasyEntityQueryable<>(resultProxy, select);
        } else {
            ClientQueryable<TR> select = getClientQueryable6().select(resultProxy.getEntityClass(), columnAsSelector -> {
                selectAsExpression.accept(columnAsSelector.getAsSelector());
            });
            return new EasyEntityQueryable<>(resultProxy, select);
        }
    }

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> selectMerge(SQLFuncExpression1<MergeTuple6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy>, TRProxy> selectExpression) {
        return select((a, b, c, d, e, f) -> selectExpression.apply(new MergeTuple6<>(a, b, c, d, e, f)));
    }
    /**
     * 快速读取单列用于返回基本类型或者subQuery等查询
     * <blockquote><pre>
     *     {@code
     *          //如果您是枚举需要单独查询请转成integer或者具体数据库对应的值
     *          //直接返回单个列如果是Enum类型的不支持
     *         .selectColumn((t1,t2,t3,t4,t5,t6) -> t2.enumProp().toNumber(Integer.class))
     *          //快速生成子查询
     *          Query<Enum> query = easyEntityQuery.queryable(EntityClass.class).where(o -> o.id().eq("123" )).selectColumn((t1,t2,t3,t4,t5,t6) -> t1.enumProp());
     *         List<EntityClass> list = easyEntityQuery.queryable(EntityClass.class).where(o -> {
     *             o.enumProp().in(query);
     *         }).toList();
     *
     *
     *                 }
     * </pre></blockquote>
     *
     * @param selectExpression
     * @param <TR>
     * @return
     */
    default <TR> Query<TR> selectColumn(SQLFuncExpression6<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, PropTypeColumn<TR>> selectExpression) {
        PropTypeColumn<TR> column = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(),get6Proxy());
        Objects.requireNonNull(column, "select column null result class");
        ClientQueryable<?> select = getClientQueryable6().select(column.getPropertyType(), (t1, t2, t3, t4, t5, t6) -> {
            PropTypeColumn.selectColumn(t1.getAsSelector(),column);
        });
        return EasyObjectUtil.typeCastNullable(select);
    }
}
