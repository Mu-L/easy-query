package com.easy.query.cache.core;


import com.easy.query.cache.core.common.CacheKey;
import com.easy.query.cache.core.queryable.AllCacheQueryable;
import com.easy.query.cache.core.queryable.KvCacheQueryable;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;

/**
 * create time 2023/5/16 10:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyCacheClient {
    <T> T getService(Class<T> serviceType);

    <T1Proxy extends ProxyEntity<T1Proxy, TEntity>,
            TEntity extends ProxyEntityAvailable<TEntity, T1Proxy> & CacheKvEntity>
    KvCacheQueryable<T1Proxy, TEntity> kvStorage(Class<TEntity> entityClass);

    <T1Proxy extends ProxyEntity<T1Proxy, TEntity>,
            TEntity extends ProxyEntityAvailable<TEntity, T1Proxy> & CacheAllEntity>
    AllCacheQueryable<T1Proxy, TEntity> allStorage(Class<TEntity> entityClass);


    <T1Proxy extends ProxyEntity<T1Proxy, TEntity>,
            TEntity extends CacheKvEntity>
    KvCacheQueryable<T1Proxy, TEntity> kvStorage(T1Proxy t1Proxy);

    <T1Proxy extends ProxyEntity<T1Proxy, TEntity>,
            TEntity extends CacheAllEntity>
    AllCacheQueryable<T1Proxy, TEntity> allStorage(T1Proxy t1Proxy);

    void deleteBy(CacheKey cacheKey);

}
