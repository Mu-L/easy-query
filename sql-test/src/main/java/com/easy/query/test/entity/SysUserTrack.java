package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Encryption;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.encryption.DefaultAesEasyEncryptionStrategy;
import com.easy.query.test.entity.proxy.SysUserTrackProxy;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/3/25 10:55
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table(value = "t_sys_user_track")
@EntityProxy
public class SysUserTrack implements ProxyEntityAvailable<SysUserTrack , SysUserTrackProxy> {
    @Column(primaryKey = true)
    private String id;
    private String username;
    @Encryption(strategy = DefaultAesEasyEncryptionStrategy.class, supportQueryLike = true)
    private String phone;
    @Encryption(strategy = DefaultAesEasyEncryptionStrategy.class, supportQueryLike = true)
    private String idCard;
    @Encryption(strategy = DefaultAesEasyEncryptionStrategy.class, supportQueryLike = true)
    private String address;
    /**
     * 创建时间;创建时间
     */
    private LocalDateTime createTime;
}

//    id varchar(32) not null comment '主键ID'primary key,
//        username varchar(50) null comment '姓名',
//        phone varchar(250) null comment '手机号加密',
//        id_card varchar(500) null comment '身份证编号',
//        address text null comment '地址',
//        create_time datetime not null comment '创建时间'