package com.easy.query.core.basic.extension.track;

import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.exception.EasyQueryException;

/**
 * @author xuejiaming
 * @FileName: DeffaultTrackManager.java
 * @Description: 文件说明
 * create time 2023/3/19 17:07
 */
public class DefaultTrackManager implements TrackManager {
    private final ThreadLocal<TrackContext> threadTC = ThreadLocal.withInitial(() -> null);
    private final EntityMetadataManager entityMetadataManager;

    public DefaultTrackManager(EntityMetadataManager entityMetadataManager) {

        this.entityMetadataManager = entityMetadataManager;
    }

    @Override
    public void begin() {
        TrackContext trackContext = threadTC.get();
        if (trackContext == null) {
            trackContext = new DefaultTrackContext(entityMetadataManager);
            threadTC.set(trackContext);
        }
        trackContext.begin();

    }

    @Override
    public TrackContext getCurrentTrackContext() {
        return threadTC.get();
    }

    @Override
    public void setCurrentTrackContext(TrackContext trackContext) {
        threadTC.set(trackContext);
    }

    @Override
    public void release() {
        TrackContext trackContext = getCurrentTrackContext();
        if (trackContext == null) {
            throw new EasyQueryException("current thread not begin track");
        }
        if(trackContext.release()){
            threadTC.remove();
        }
    }

}
