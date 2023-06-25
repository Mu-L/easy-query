package com.easy.query.api.proxy.select.impl;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.abstraction.AbstractProxyQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.proxy.ProxyQuery;

/**
 * create time 2023/6/23 23:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyProxyQueryable2<T1Proxy extends ProxyQuery<T1Proxy, T1>, T1, T2Proxy extends ProxyQuery<T2Proxy, T2>, T2> extends AbstractProxyQueryable2<T1Proxy, T1, T2Proxy, T2> {
    public EasyProxyQueryable2(T1Proxy t1Proxy, T2Proxy t2Proxy, ClientQueryable2<T1, T2> entityQueryable) {
        super(t1Proxy, t2Proxy, entityQueryable);
    }

    @Override
    public ProxyQueryable<T1Proxy, T1> cloneQueryable() {
        return new EasyProxyQueryable2<>(get1Proxy(),get2Proxy(),getClientQueryable2());
    }
}
