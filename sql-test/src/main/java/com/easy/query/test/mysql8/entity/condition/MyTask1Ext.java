package com.easy.query.test.mysql8.entity.condition;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.condition.proxy.MyTask1ExtProxy;
import lombok.Data;

/**
 * create time 2026/6/18 09:54
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("my_task1_ext")
@EntityProxy
public class MyTask1Ext implements ProxyEntityAvailable<MyTask1Ext, MyTask1ExtProxy> {
    @Column(primaryKey = true)
    private String id;
    private String taskId;
    private String name;

}
