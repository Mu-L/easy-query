package com.easy.query.test.navigateflat;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.navigateflat.proxy.MyTable;
import lombok.Data;

/**
 * create time 2025/1/3 12:03
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("my_user")
@Data
@EntityProxy("MyTable")
public class MyUserHome implements ProxyEntityAvailable<MyUserHome , MyTable> {
    private String id;
    private String name;
    private Integer age;
}
