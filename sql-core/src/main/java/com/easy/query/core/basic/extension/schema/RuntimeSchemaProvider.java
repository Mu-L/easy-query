package com.easy.query.core.basic.extension.schema;

import com.easy.query.core.metadata.EntityMetadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * create time 2026/2/22 13:18
 * 运行时schema提供者主要用来生成sql时的schema
 *
 * @author xuejiaming
 */
public interface RuntimeSchemaProvider {
   @Nullable String getSchema(@NotNull EntityMetadata entityMetadata);
}
