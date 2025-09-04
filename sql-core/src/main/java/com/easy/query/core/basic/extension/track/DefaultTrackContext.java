package com.easy.query.core.basic.extension.track;

import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.util.EasyBeanUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyMapUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.core.util.EasyTrackUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xuejiaming
 * @FileName: DefaultTrackContext.java
 * @Description: 文件说明
 * create time 2023/3/19 21:13
 */
public class DefaultTrackContext implements TrackContext {
    private final EntityMetadataManager entityMetadataManager;
    private int beginCount = 0;
    private final ConcurrentHashMap<Class<?>, ConcurrentHashMap<String, EntityState>> trackEntityMap = new ConcurrentHashMap<>();

    public DefaultTrackContext(EntityMetadataManager entityMetadataManager) {
        this.entityMetadataManager = entityMetadataManager;
    }

    @Override
    public void begin() {
        beginCount++;
    }

    @Override
    public boolean isTrack(Object entity) {
        return getTrackEntityState(entity) != null;
    }

    @Override
    public EntityState getTrackEntityState(Object entity) {
        if (entity == null) {
            return null;
        }
        ConcurrentHashMap<String, EntityState> entityStateTrackMap = trackEntityMap.get(entity.getClass());
        if (entityStateTrackMap == null || entityStateTrackMap.isEmpty()) {
            return null;
        }
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entity.getClass());
        String trackKey = EasyTrackUtil.getTrackKey(entityMetadata, entity);
        if (trackKey == null) {
            return null;
        }
        return entityStateTrackMap.get(trackKey);
    }

    @Override
    public EntityState getTrackEntityState(Class<?> entityClass, String trackKey) {

        if (trackKey == null) {
            return null;
        }
        ConcurrentHashMap<String, EntityState> entityStateTrackMap = trackEntityMap.get(entityClass);
        if (entityStateTrackMap == null || entityStateTrackMap.isEmpty()) {
            return null;
        }
        return entityStateTrackMap.get(trackKey);
    }

    @Override
    public boolean addTracking(@NotNull Object entity) {
        EntityState entityState = addInternalTracking(entity, false);
        return entityState.getCurrentValue() == entity;
    }

    @NotNull
    @Override
    public EntityState addQueryTracking(@NotNull Object entity) {
        return addInternalTracking(entity, true);
    }

    private EntityState addInternalTracking(Object entity, boolean isQuery) {
        if (entity == null) {
            throw new EasyQueryException("cant track null entity");
        }

        Class<?> entityClass = entity.getClass();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);
        if (EasyStringUtil.isBlank(entityMetadata.getTableName())) {
            throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + ": is not table entity,not allowed track");
        }
        String trackKey = EasyTrackUtil.getTrackKey(entityMetadata, entity);
        if (trackKey == null) {
            throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + ": current entity cant get track key,primary maybe null");
        }
        //其实不涉及高并发这边应该采用HashMap即可因为他是ThreadLocal线程内串行的
        ConcurrentHashMap<String, EntityState> entityStateMap = EasyMapUtil.computeIfAbsent(trackEntityMap, entityClass, o -> new ConcurrentHashMap<>());
        EntityState originalEntityState = entityStateMap.get(trackKey);
        if (originalEntityState != null) {
            //查询的情况下直接使用追踪后的数据
            if (!isQuery && originalEntityState.getCurrentValue() != entity) {//内存地址不一样就说明追踪的不是同一个对象那么将要报错
                throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + ": current entity already tracked key,primary key:" + trackKey);
            }
            return originalEntityState;
        } else {
            Object original = createAndCopyValue(entity, entityMetadata);
            EntityState entityState = new EntityState(entityMetadata, trackKey, original, entity);
            entityStateMap.putIfAbsent(trackKey, entityState);
            return entityState;
        }
    }

    @Override
    public boolean removeTracking(@NotNull Object entity) {

        if (entity == null) {
            throw new EasyQueryException("cant track null entity");
        }

        if (trackEntityMap.isEmpty()) {
            return true;
        }
        Class<?> entityClass = entity.getClass();
        ConcurrentHashMap<String, EntityState> entityStateMap = EasyMapUtil.computeIfAbsent(trackEntityMap, entityClass, o -> new ConcurrentHashMap<>());
        if (entityStateMap.isEmpty()) {
            return true;
        }
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);
        if (EasyStringUtil.isBlank(entityMetadata.getTableName())) {
            throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + ": is not table entity,cant tracking");
        }

        String trackKey = EasyTrackUtil.getTrackKey(entityMetadata, entity);
        if (trackKey == null) {
            throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + ": current entity cant get track key,primary maybe null");
        }
        entityStateMap.remove(trackKey);
        return true;
    }


    @Override
    public boolean hasTracked(Class<?> entityClass) {
        if (entityClass == null) {
            return false;
        }
        ConcurrentHashMap<String, EntityState> entityStateMap = trackEntityMap.get(entityClass);
        if (entityStateMap == null) {
            return false;
        }
        return !entityStateMap.isEmpty();
    }

    /**
     * 实体对象零拷贝
     *
     * @param entity
     * @param entityMetadata
     * @return
     */
    @Override
    public Object createAndCopyValue(Object entity, EntityMetadata entityMetadata) {

//        Class<?> entityClass = entity.getClass();
        Object original = entityMetadata.getBeanConstructorCreator().get();
        for (Map.Entry<String, ColumnMetadata> columnMetadataEntry : entityMetadata.getProperty2ColumnMap().entrySet()) {
            ColumnMetadata columnMetadata = columnMetadataEntry.getValue();
            Class<?> propertyType = columnMetadata.getPropertyType();
//            Property<Object, ?> beanGetter = columnMetadata.getGetterCaller();
//            Object value = beanGetter.apply(entity);
            if (EasyClassUtil.isBasicType(propertyType) || EasyClassUtil.isEnumType(propertyType)) {
                Object value = EasyBeanUtil.getPropertyValue(entity, entityMetadata, columnMetadata);
                EasyBeanUtil.setPropertyValue(original, entityMetadata, columnMetadata, value, false);
//                beanSetter.call(original, value);
            } else {
                Object value = columnMetadata.isValueObject() ? columnMetadata.getBeanConstructorCreatorOrNull().get() : EasyBeanUtil.getPropertyValue(entity, entityMetadata, columnMetadata);
                ValueConverter<?, ?> valueConverter = columnMetadata.getValueConverter();
                Object serializeValue = valueConverter.serialize(EasyObjectUtil.typeCastNullable(value), columnMetadata);
                Object deserialize = valueConverter.deserialize(EasyObjectUtil.typeCastNullable(serializeValue), columnMetadata);

                EasyBeanUtil.setPropertyValue(original, entityMetadata, columnMetadata, deserialize, false);
//                PropertySetterCaller<Object> beanSetter = columnMetadata.getSetterCaller();
//                beanSetter.call(original, deserialize);
            }
        }
        return original;
    }

    @Override
    public boolean release() {
        if (beginCount > 0) {
            beginCount--;
        }
        if (beginCount == 0) {
            trackEntityMap.clear();
            return true;
        }
        return false;

    }
}
