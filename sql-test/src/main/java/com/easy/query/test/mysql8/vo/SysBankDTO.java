package com.easy.query.test.mysql8.vo;


import com.easy.query.core.expression.parser.core.extra.ExtraAutoIncludeConfigure;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.test.mysql8.entity.bank.proxy.SysUserProxy;
import lombok.Data;
import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.Navigate;
import com.easy.query.core.enums.RelationTypeEnum;

import java.math.BigDecimal;
import java.util.List;
import java.time.LocalDateTime;

import lombok.Data;
import com.easy.query.core.annotation.ForeignKey;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

/**
 * this file automatically generated by easy-query struct dto mapping
 * 当前文件是easy-query自动生成的 结构化dto 映射
 * {@link com.easy.query.test.mysql8.entity.bank.SysBank }
 *
 * @author xuejiaming
 * @easy-query-dto schema: normal
 */
@Data
public class SysBankDTO {


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
    @Navigate(value = RelationTypeEnum.OneToMany)
    private List<InternalBankCards> bankCards;


    /**
     * {@link com.easy.query.test.mysql8.entity.bank.SysBankCard }
     */
    @Data
    public static class InternalBankCards {

        private String id;
        private String uid;
        /**
         * 银行卡号
         */
        private String code;
        /**
         * 银行卡类型借记卡 储蓄卡
         */
        private String type;
        /**
         * 所属银行
         */
        private String bankId;
        /**
         * 用户开户时间
         */
        private LocalDateTime openTime;
        /**
         * 所属用户
         */
        @Navigate(value = RelationTypeEnum.ManyToOne)
        private InternalUser user;


    }


    /**
     * {@link com.easy.query.test.mysql8.entity.bank.SysUser }
     */
    @Data
    @FieldNameConstants
    public static class InternalUser {
        private static final ExtraAutoIncludeConfigure EXTRA_AUTO_INCLUDE_CONFIGURE= SysUserProxy.TABLE.EXTRA_AUTO_INCLUDE_CONFIGURE()
                .configure(query->query.subQueryToGroupJoin(u->u.userBooks()))
                .select(u-> Select.of(
                        u.userBooks().count().as(Fields.bookCount),
                        u.userBooks().orderBy(book->book.price().desc()).firstElement().name().as(Fields.bookName),
                        u.userBooks().orderBy(book->book.price().desc()).firstElement().price().as(Fields.bookPrice)
                ));

        private String id;
        private String name;
        private String phone;
        private Integer age;
        private LocalDateTime createTime;

        @SuppressWarnings("EasyQueryFieldMissMatch")
        private Long bookCount;
        @SuppressWarnings("EasyQueryFieldMissMatch")
        private String bookName;
        @SuppressWarnings("EasyQueryFieldMissMatch")
        private BigDecimal bookPrice;

    }

}
