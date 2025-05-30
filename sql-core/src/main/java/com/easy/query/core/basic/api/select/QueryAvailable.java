package com.easy.query.core.basic.api.select;

import com.easy.query.core.metadata.EntityMetadata;
import org.jetbrains.annotations.NotNull;

/**
 * create time 2023/10/7 15:06
 * 文件说明
 *
 * @author xuejiaming
 */
public interface QueryAvailable<T> {
    /**
     * 当前查询对象的字节信息
     *
     * @return 当前查询的对象字节
     */
    @NotNull
    Class<T> queryClass();
    @NotNull
    EntityMetadata queryEntityMetadata();
}
