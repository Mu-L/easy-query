package com.easy.query.test.mysql8.entity.condition;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.condition.proxy.MyTask2ExtProxy;
import lombok.Data;

/**
 * create time 2026/6/18 09:54
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("my_task2_ext")
@EntityProxy
public class MyTask2Ext implements ProxyEntityAvailable<MyTask2Ext, MyTask2ExtProxy> {
    @Column(primaryKey = true)
    private String id;
    private String taskId;
    private String name;

}
