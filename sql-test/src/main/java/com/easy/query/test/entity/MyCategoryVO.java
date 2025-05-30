package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.MyCategoryProxy;
import lombok.Data;

import java.util.List;

/**
 * create time 2024/11/13 21:58
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
public class MyCategoryVO{
    private String id;
    private String parentId;
    private String name;

    private Long count;
}
