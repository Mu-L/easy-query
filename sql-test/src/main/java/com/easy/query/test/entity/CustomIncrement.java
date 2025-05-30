package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.CustomIncrementProxy;
import com.easy.query.test.increment.MyDatabaseIncrementSQLColumnGenerator;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/8/9 10:53
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table("custom_increment")
@EntityProxy
public class CustomIncrement implements ProxyEntityAvailable<CustomIncrement , CustomIncrementProxy> {
    @Column(primaryKey = true,generatedKey = true, generatedSQLColumnGenerator = MyDatabaseIncrementSQLColumnGenerator.class)
    private String id;
    private String name;
    private String address;
}
