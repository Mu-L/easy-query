package com.easy.query.test.vo;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.test.entity.BaseEntity;
import com.easy.query.test.vo.proxy.TestUserAAAProxy;
import lombok.Data;

/**
 * create time 2024/6/14 16:17
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@EntityProxy
public class TestUserAAA extends BaseEntity {
    @Column(primaryKey = true)
    private String id;

    private Integer lineFlag;

    /**
     * 中间件同步状态 1未同步 2已同步
     */
    private Integer dStatus;

    /**
     * 使用3区
     */
    private Integer useQ3;
    private void aaa(){
        System.out.println(TestUserAAAProxy.Fields.createTime);
    }
}
