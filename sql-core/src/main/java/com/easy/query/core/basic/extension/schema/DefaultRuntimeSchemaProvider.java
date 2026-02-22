package com.easy.query.core.basic.extension.schema;

import com.easy.query.core.metadata.EntityMetadata;
import org.jetbrains.annotations.NotNull;

/**
 * create time 2026/2/22 13:21
 * 默认实现
 *
 * @author xuejiaming
 */
public class DefaultRuntimeSchemaProvider implements RuntimeSchemaProvider {
    @Override
    public String getSchema(@NotNull EntityMetadata entityMetadata) {
        return entityMetadata.getSchemaOrNull();
    }
}
