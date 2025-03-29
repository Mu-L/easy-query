package com.easy.query.core.expression.include.getter;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.sql.include.RelationExtraEntity;
import com.easy.query.core.expression.sql.include.RelationValue;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;
import java.util.Map;

/**
 * create time 2025/3/29 21:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class EqualsManyToOneGetter implements RelationIncludeGetter{
    private final Map<RelationValue, ?> includeMap;
    public EqualsManyToOneGetter(String[] targetPropertyNames, List<RelationExtraEntity> includes){
        //因为是多对一所以获取关联数据key为主键的map
        this.includeMap = EasyCollectionUtil.collectionToMap(includes, x -> {
            RelationValue relationExtraColumns = x.getRelationExtraColumns(targetPropertyNames);
            if (relationExtraColumns.isNull()) {
                return null;
            }
            return relationExtraColumns;
        }, o -> o.getEntity(), (key, old) -> {
            if (old != null) {
                //应该使用ManyToOne而不是OneToOne所以请用户自行确认数据表示的是One-To-One还是Many-To-One
                throw new EasyQueryInvalidOperationException("The relationship value ‘" + key + "’ appears to have duplicates: [" + EasyClassUtil.getInstanceSimpleName(old) + "]. Please confirm whether the data represents a One or Many relationship.");
            }
        });
    }
    @Override
    public boolean include() {
        return !includeMap.isEmpty();
    }

    @Override
    public Object getIncludeValue(RelationValue relationValue) {
        return includeMap.get(relationValue);
    }
}
