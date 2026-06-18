package com.easy.query.test.mysql8.entity.condition;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.NavigateCondition;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.condition.proxy.MySubTask1Proxy;
import com.easy.query.test.mysql8.entity.condition.proxy.MySubTask2Proxy;
import com.easy.query.test.mysql8.entity.condition.proxy.MyTask1ExtProxy;
import com.easy.query.test.mysql8.entity.condition.proxy.MyTask2ExtProxy;
import com.easy.query.test.mysql8.entity.condition.proxy.MyTaskProxy;
import lombok.Data;

import java.util.List;

/**
 * create time 2026/6/18 09:50
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("my_task")
@EntityProxy
public class MyTask implements ProxyEntityAvailable<MyTask, MyTaskProxy> {
    @Column(primaryKey = true)
    private String id;
    private String name;
    private Long version;


    /**
     * version1的子任务
     **/
    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {MyTaskProxy.Fields.id}, targetProperty = {MySubTask1Proxy.Fields.taskId}
            , conditions = {
            @NavigateCondition(property = "version", havingValue = "1")
    })
    private List<MySubTask1> mySubTask1List;
    /**
     * version2的子任务
     **/
    @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {MyTaskProxy.Fields.id}, targetProperty = {MySubTask2Proxy.Fields.taskId}, conditions = {
            @NavigateCondition(property = "version", havingValue = "2")
    })
    private List<MySubTask2> mySubTask2List;


    /**
     * version1的扩展
     **/
    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = {MyTaskProxy.Fields.id}, targetProperty = {MyTask1ExtProxy.Fields.taskId}, conditions = {
            @NavigateCondition(property = "version", havingValue = "1")
    })
    private MyTask1Ext myTask1Ext;

    /**
     * version2的扩展
     **/
    @Navigate(value = RelationTypeEnum.OneToOne, selfProperty = {MyTaskProxy.Fields.id}, targetProperty = {MyTask2ExtProxy.Fields.taskId}, conditions = {
            @NavigateCondition(property = "version", havingValue = "2")
    })
    private MyTask2Ext myTask2Ext;
}
