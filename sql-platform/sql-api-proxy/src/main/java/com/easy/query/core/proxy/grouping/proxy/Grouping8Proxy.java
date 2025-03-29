package com.easy.query.core.proxy.grouping.proxy;

import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.grouping.Grouping8;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2023/12/28 21:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class Grouping8Proxy<TKey1Proxy extends PropTypeColumn<TKey1>, TKey1,
        TKey2Proxy extends PropTypeColumn<TKey2>, TKey2,
        TKey3Proxy extends PropTypeColumn<TKey3>, TKey3,
        TKey4Proxy extends PropTypeColumn<TKey4>, TKey4,
        TKey5Proxy extends PropTypeColumn<TKey5>, TKey5,
        TKey6Proxy extends PropTypeColumn<TKey6>, TKey6,
        TKey7Proxy extends PropTypeColumn<TKey7>, TKey7,
        TKey8Proxy extends PropTypeColumn<TKey8>, TKey8,
        TSourceProxy>
        extends AbstractGroupingProxy<Grouping8Proxy<TKey1Proxy, TKey1, TKey2Proxy, TKey2, TKey3Proxy, TKey3, TKey4Proxy, TKey4, TKey5Proxy, TKey5, TKey6Proxy, TKey6, TKey7Proxy, TKey7, TKey8Proxy, TKey8, TSourceProxy>,
        Grouping8<TKey1, TKey2, TKey3, TKey4, TKey5, TKey6, TKey7, TKey8>, TSourceProxy> {

    private static final Class<Grouping8> entityClass = Grouping8.class;
    private final TKey1Proxy k1;
    private final TKey2Proxy k2;
    private final TKey3Proxy k3;
    private final TKey4Proxy k4;
    private final TKey5Proxy k5;
    private final TKey6Proxy k6;
    private final TKey7Proxy k7;
    private final TKey8Proxy k8;

    public Grouping8Proxy(TKey1Proxy k1, TKey2Proxy k2, TKey3Proxy k3, TKey4Proxy k4, TKey5Proxy k5, TKey6Proxy k6, TKey7Proxy k7, TKey8Proxy k8, TSourceProxy tSourceProxy) {
        super(tSourceProxy);
        this.k1 = k1;
        this.k2 = k2;
        this.k3 = k3;
        this.k4 = k4;
        this.k5 = k5;
        this.k6 = k6;
        this.k7 = k7;
        this.k8 = k8;
    }

    @Override
    public Class<Grouping8<TKey1, TKey2, TKey3, TKey4, TKey5, TKey6, TKey7, TKey8>> getEntityClass() {
        return EasyObjectUtil.typeCastNullable(entityClass);
    }

    public TKey1Proxy key1() {
        return k1;
    }

    public TKey2Proxy key2() {
        return k2;
    }

    public TKey3Proxy key3() {
        return k3;
    }

    public TKey4Proxy key4() {
        return k4;
    }

    public TKey5Proxy key5() {
        return k5;
    }

    public TKey6Proxy key6() {
        return k6;
    }

    public TKey7Proxy key7() {
        return k7;
    }

    public TKey8Proxy key8() {
        return k8;
    }

    @Override
    public List<PropTypeColumn<?>> getKeys() {
        return Arrays.asList(k1, k2, k3, k4, k5, k6, k7, k8);
    }

    @Override
    public void accept(GroupSelector s) {
        acceptGroupSelector(k1, s);
        acceptGroupSelector(k2, s);
        acceptGroupSelector(k3, s);
        acceptGroupSelector(k4, s);
        acceptGroupSelector(k5, s);
        acceptGroupSelector(k6, s);
        acceptGroupSelector(k7, s);
        acceptGroupSelector(k8, s);
    }
}