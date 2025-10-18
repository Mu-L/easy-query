package com.easy.query.test.mysql8.entity.bank;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EasyAlias;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.annotation.OrderByProperty;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.enums.PartitionOrderEnum;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.mysql8.entity.bank.proxy.SysBankProxy;
import com.easy.query.test.mysql8.view.FirstSysBankCard;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/4/3 20:06
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("t_bank")
@EntityProxy
@Data
@FieldNameConstants
@EasyAlias("bank")
public class SysBank implements ProxyEntityAvailable<SysBank, SysBankProxy> {
    @Column(primaryKey = true)
    private String id;
    /**
     * 银行名称
     */
    private String name;
    /**
     * 成立时间
     */
    private LocalDateTime createTime;

    /**
     * 拥有的银行卡
     */
    @Navigate(value = RelationTypeEnum.OneToMany,
            selfProperty = {"id"},
            targetProperty = {"bankId"})
    private List<SysBankCard> bankCards;

    @Navigate(value = RelationTypeEnum.OneToOne,
            selfProperty = {"id"},
            targetProperty = {"bankId"})
    private FirstSysBankCard firstBankCard;


    /**
     * 拥有的银行卡
     */
    @Navigate(value = RelationTypeEnum.ManyToOne,
            selfProperty = {"id"},
            targetProperty = {"bankId"})
    private SysBankCard bankCard1;


    @Navigate(value = RelationTypeEnum.ManyToMany,
            selfProperty = {SysBank.Fields.id},
            selfMappingProperty = {SysBankCard.Fields.bankId},
            mappingClass = SysBankCard.class, targetProperty = {SysUser.Fields.id}, targetMappingProperty = {SysBankCard.Fields.uid})
    private List<SysUser> users;
}
