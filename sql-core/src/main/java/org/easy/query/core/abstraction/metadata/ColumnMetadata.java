package org.easy.query.core.abstraction.metadata;

import java.beans.PropertyDescriptor;

/**
 * @FileName: ColumnMetadata.java
 * @Description: 文件说明
 * @Date: 2023/2/11 15:13
 * @Created by xuejiaming
 */
public class ColumnMetadata {

    private final EntityMetadata entityMetadata;
    private final String name;


    private PropertyDescriptor property;

    private  boolean primary=false;
    private  boolean increment=false;


    private  boolean nullable=false;
    private  boolean version=false;
    private  boolean insertIgnore=false;
    private  boolean updateIgnore =false;

    public ColumnMetadata(EntityMetadata entityMetadata, String name) {
        this.entityMetadata = entityMetadata;
        this.name = name;
    }

    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    public String getName() {
        return name;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public boolean isIncrement() {
        return increment;
    }

    public void setIncrement(boolean increment) {
        this.increment = increment;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isVersion() {
        return version;
    }

    public void setVersion(boolean version) {
        this.version = version;
    }

    public boolean isInsertIgnore() {
        return insertIgnore;
    }

    public void setInsertIgnore(boolean insertIgnore) {
        this.insertIgnore = insertIgnore;
    }

    public boolean isUpdateIgnore() {
        return updateIgnore;
    }

    public void setUpdateIgnore(boolean updateIgnore) {
        this.updateIgnore = updateIgnore;
    }
    public PropertyDescriptor getProperty() {
        return property;
    }

    public void setProperty(PropertyDescriptor property) {
        this.property = property;
    }
}