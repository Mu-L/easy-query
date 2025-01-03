package com.easy.query.core.basic.extension.track;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.exception.EasyQueryException;

import java.util.Objects;

/**
 * @FileName: TrackContext.java
 * @Description: 文件说明
 * @Date: 2023/3/18 21:21
 * @author xuejiaming
 */
public interface TrackContext {
    void begin();
    boolean isTrack(Object entity);

    /**
     * 没有或者不符合返回null
     * @param entity
     * @return
     */
   @Nullable
   EntityState getTrackEntityState(Object entity);
    default @NotNull EntityState getTrackEntityStateNotNull(Object entity){
        EntityState trackEntityState = getTrackEntityState(entity);
        Objects.requireNonNull(trackEntityState,"cant get track entity state");
        return trackEntityState;
    }

    /**
     * 查询的情况下添加追踪如果当前添加的对象已经被追踪,且追踪对象和当前对象不是同一个对象那么就会抛错
     * 那么当前添加的对象将不会被添加到上下文中,接口一般用于查询时候返回对象
     *
     * @param entity
     * @return true:添加成功,false:已经存在相同对象
     * @throws EasyQueryException 追踪对象为null,追踪对象无法获取key,追踪对象已经被追踪且不是同一个对象,追踪对象无法获取表名
     */
    boolean addTracking(Object entity);

    EntityState addQueryTracking(Object entity);

    /**
     * 只要不在当前上下文那么就是返回true
     * @param entity
     * @return
     */
    boolean removeTracking(Object entity);

    boolean release();

    boolean hasTracked(Class<?> entityClass);
}
