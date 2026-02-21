package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.proxy.AggregateQueryable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/16 08:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityToAggregatable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends ClientEntityQueryableAvailable<T1> ,EntityQueryableAvailable<T1Proxy,T1>{

    default EntityQueryable<AggregateQueryable<T1Proxy,T1>, T1> toAggregate(){
        return new EasyEntityQueryable<>(AggregateQueryable.of(this.getQueryable().get1Proxy()),this.getClientQueryable());
    }
}
